package towerDef.announce;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

class AnnounceText {
    private String text;
    int duree;
    int transparency = 255;
    private int ID;
    int timer = 0;
    Boolean show = true;
    private int x;
    private int y;

    AnnounceText(String text, int duree, int ID, int x, int y) {
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.text = text;
        this.duree = duree;
    }

    void show(Graphics g, int xAbsolu, int yAbsolu) {
        if (show) {
            g.setColor(new Color(255, 255, 0, transparency));
            g.drawString(text, x - xAbsolu, y - yAbsolu);
            g.setColor(Color.white);
        }
    }

    void resetTimer() {
        timer = 0;
    }

    void upTimer(int delta) {
        timer += delta;
    }
}
