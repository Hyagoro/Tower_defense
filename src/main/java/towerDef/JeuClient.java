package towerDef;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;


public class JeuClient extends BasicGameState {
    private ListeMobs listeMobs;
    private Camera camera;
    private InfoGame ip;
    private TowerContainer tc;
    private MobContainer mc;
    private SpawnManager gs;
    private Map map;

    private Client client;
    private Kryo kryo;
    private Nouveau nouveau;

    public JeuClient(int state) {

    }


    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        camera.rendu(g);
        map.show();
        tc.afficherProjectiles(g, map);
        mc.show(g);
        ip.show(g, gs, camera.getxActuel(), camera.getyActuel());
    }


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        client = new Client();// cr�e l'objet

        client.start();

        kryo = client.getKryo();
        kryo.register(Nouveau.class);
        kryo.register(Bienvenue.class);
        kryo.register(ListeMobs.class);


        nouveau = new Nouveau();
        nouveau.pseudo = "Hyago";


        map = new Map(2000, 2000, 50);
        ip = new InfoGame(gc.getWidth(), gc.getHeight());
        tc = new TowerContainer();
        mc = new MobContainer();
        //gs = new GestionnaireSpawn();
        camera = new Camera(gc.getWidth(), gc.getHeight());

        client.addListener(new Listener() {
            public void connected(Connection connection) {
                System.out.println("CLIENT - le joueur est connect�");
            }

            public void received(Connection connection, Object object) {
                if (object instanceof Bienvenue) {
                    Bienvenue response = (Bienvenue) object;
                    System.out.println(response.message);
                }
                if (object instanceof ListeMobs) {
                    try {
                        mc.add(150, 0.05f, 10);
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void disconnected(Connection connection) {
                System.out.println("CLIENT - le joueur est d�connect�");
            }
        });

        try {
            client.connect(5000, "localhost", 54555);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listeMobs = new ListeMobs();
        listeMobs.nombreMobs = 5;
        client.sendTCP(nouveau);
        //client.sendTCP(listeMobs);
        //envoie l'objet au serveur


    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        ip.update(delta);
        mc.update(delta, ip, map);
        tc.update(delta, mc);
        //gs.update(delta,mc,ip,ip.a);
    }

    public void mouseClicked(int button, int x, int y, int clickCount) {
        tc.event(button, x - camera.getxActuel(), y - camera.getyActuel(), clickCount, map, ip);
        //carte.resoudre();
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_F1) {
            client.sendTCP(listeMobs);
            System.out.println("envoi serveur");
        }
    }

    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        camera.event(oldx, oldy, newx, newy);
    }

    public int getID() {
        return 1;
    }
}