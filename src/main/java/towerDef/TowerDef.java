package towerDef;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class TowerDef extends BasicGameState {
    private Camera camera;
    private InfoGame ip;
    private TowerContainer tc;
    private MobContainer mc;
    private SpawnManager gs;
    private Map map;
    private Boolean close = false;

    TowerDef(int state) {

    }


    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        camera.rendu(g);
        map.show();
        tc.afficherProjectiles(g, map);
        mc.show(g);
        ip.show(g, gs, camera.getxActuel(), camera.getyActuel());
    }


    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        map = new Map(800, 500, 50);
        ip = new InfoGame(gc.getWidth(), gc.getHeight());
        tc = new TowerContainer();
        mc = new MobContainer();
        gs = new SpawnManager();
        camera = new Camera(800, 500);

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        ip.update(delta);
        mc.update(delta, ip, map);
        tc.update(delta, mc);
        gs.update(delta, mc, ip, ip.a, map);
        if (close)
            gc.exit();

    }

    public void mouseClicked(int button, int x, int y, int clickCount) {
        tc.event(button, x - camera.getxActuel(), y - camera.getyActuel(), clickCount, map, ip);
        //carte.resoudre();
    }

    public void keyPressed(int key, char c) {
        if (key == Input.KEY_F1) {
            try {
                map.solve();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        if (key == Input.KEY_ESCAPE) {
            close = true;
        }
    }

    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        camera.event(oldx, oldy, newx, newy);
    }

    public int getID() {
        return 1;
    }
}