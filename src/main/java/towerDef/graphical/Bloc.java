package towerDef.graphical;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

public class Bloc {
    protected int x, y;
    protected SpriteSheet skin;
    protected Animation animation;
    protected int size = 50;
    protected Boolean collision = false;

    public Bloc(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Boolean getCollision() {
        return collision;
    }

    public void setCollision(Boolean collision) {
        this.collision = collision;
    }
}
