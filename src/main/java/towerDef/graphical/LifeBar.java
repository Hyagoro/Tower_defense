package towerDef.graphical;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class LifeBar {
    private float x;
    private float y;
    private float totalLife;
    private float currentLife;

    public LifeBar(float x, float y, float totalLife) {
        this.currentLife = totalLife;
        this.totalLife = totalLife;
        this.x = x;
        this.y = y;
    }

    public void update(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void show(Graphics g) {
        //g.drawRect(x, y, 5, 5);
        g.setColor(Color.red);
        g.drawRect(x - 5, y - 6, 20, 0);
        g.setColor(Color.green);
        g.drawRect(x - 5, y - 6, (20 * (currentLife)) / totalLife, 0);
        g.setColor(Color.white);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getTotalLife() {
        return totalLife;
    }


    public float getCurrentLife() {
        return currentLife;
    }

    public void setCurrentLife(float currentLife) {
        this.currentLife = currentLife;
    }
}
