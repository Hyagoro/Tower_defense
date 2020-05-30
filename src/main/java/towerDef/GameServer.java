package towerDef;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;


public class GameServer {
    Server server;
    int nbreConnexions = 0;
    static public final int port = 54555; //le port utilis� pour TCP

    public GameServer() throws IOException {
        server = new Server();

        Kryo kryo = server.getKryo();
        kryo.register(Nouveau.class);
        kryo.register(Bienvenue.class);
        kryo.register(ListeMobs.class);

        server.addListener(new Listener() {
            public void connected(Connection connection) {
                nbreConnexions++;
            }

            public void received(Connection c, Object object) {
                if (object instanceof Nouveau) {
                    Log.info("Objet Nouveau re�us.");
                    Nouveau log = (Nouveau) object;
                    String nom = log.pseudo;
                    System.out.println("SERVEUR - le joueur envoi son pseudo LOGIN: " + nom);
                    System.out.println("il y a " + nbreConnexions + " connection(s) sur le serveur :");
                    /* on pr�pare le message de retour � tous les joueurs*/
                    Bienvenue annonce = new Bienvenue();
                    annonce.message = "le joueur :" + nom + " vient de se connecter !";
                    server.sendToAllTCP(annonce);//on envoie l'objet � tous les joueurs

                    if (nbreConnexions > 1) {
                        annonce.message = "Test 1";
                        server.sendToAllTCP(annonce);
                    }

                    //c.close();
                }
                if (object instanceof ListeMobs) {
                    ListeMobs lm = (ListeMobs) object;
                    System.out.println("Objet Re�us, traitement...");
                    int ID = c.getID();
                    Bienvenue annonce = new Bienvenue();
                    annonce.message = "Envoi de : " + ID + ". Nombre de mob : " + lm.nombreMobs;
                    server.sendToAllExceptTCP(ID, annonce);
                    server.sendToAllExceptTCP(ID, lm);
                }
            }

            public void disconnected(Connection connection) {
                System.out.println("SERVEUR - d�connect� de " + connection.getID());
                nbreConnexions--;
            }

        });
        server.bind(port);
        server.start();
    }

    public static void main(String[] args) throws IOException {
        new GameServer();
        Log.set(Log.LEVEL_DEBUG);
        System.out.println("Serveur lanc�");
    }
}
