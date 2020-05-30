package towerDef.announce;

import org.newdawn.slick.Graphics;

public class Announces {
    private int xBase, yBase;
    private int movingY;
    private AnnounceText[] announceText;
    private int nbAnnounces = 0;
    int numAnnounce = 0;

    public Announces(int x, int y) {
        xBase = x;
        movingY = yBase = y;
        announceText = new AnnounceText[10];
    }

    public void show(Graphics g, int xAbsolu, int yAbsolu) {
        for (int i = 0; i < nbAnnounces; i++) {
            announceText[i].show(g, xAbsolu, yAbsolu);
        }
    }

    public void update(int delta) {
        for (int i = 0; i < nbAnnounces; i++) {

            if (announceText[i].timer >= announceText[i].duree) {
                if (announceText[i].transparency >= 5) {
                    announceText[i].transparency -= 5;
                } else {
                    announceText[i].resetTimer();
                    announceText[i].show = false;
                    if (!announceText[nbAnnounces - 1].show) {
                        nbAnnounces = 0;
                        movingY = yBase;

                    }
                }
            } else {
                announceText[i].upTimer(delta);
            }
        }
    }

    public void add(String text) {

        announceText[nbAnnounces] = new AnnounceText(text, 3500, nbAnnounces, xBase, movingY);
        movingY -= 15;
        nbAnnounces++;
    }
}
