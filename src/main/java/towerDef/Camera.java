package towerDef;

import org.newdawn.slick.Graphics;

public class Camera {
    private int resolutionX;
    private int resolutionY;
    private int xBase = 0;
    private int yBase = 0;
    private int xActuel;
    private int yActuel;

    Camera(int resolutionX, int resolutionY) {
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        xActuel = xBase;
        yActuel = yBase;
    }

    void event(int oldx, int oldy, int newx, int newy) {
        if (xActuel - (oldx - newx) <= xBase && xActuel - (oldx - newx) >= -(800 - 800)) {
            xActuel = xActuel - (oldx - newx);
        }
        if (yActuel - (oldy - newy) <= yBase && yActuel - (oldy - newy) >= -(500 - 500)) {
            yActuel = yActuel - (oldy - newy);
        }
    }

    void rendu(Graphics g) {
        g.drawString("xAct : " + xActuel + "yAct : " + yActuel + "", 100, 100);
        g.translate(xActuel, yActuel);
    }

    public int getxActuel() {
        return xActuel;
    }

    public void setxActuel(int xActuel) {
        this.xActuel = xActuel;
    }

    public int getyActuel() {
        return yActuel;
    }

    public void setyActuel(int yActuel) {
        this.yActuel = yActuel;
    }

}
