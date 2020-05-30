package towerDef.graphical;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

public abstract class Movable {
    protected float x;
    protected float y;
    protected SpriteSheet skin;
    protected Animation anim;

    public void show(Graphics g) {
        anim.draw(x, y);
    }
}
