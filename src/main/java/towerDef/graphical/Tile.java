package towerDef.graphical;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Tile {
    private Boolean isOccupied;
    private Boolean isPath;
    private SpriteSheet texture;
    private SpriteSheet textureBase;
    private Animation animation;
    private Image test;

    public Tile() throws SlickException {
        test = new Image("textures/case2.png");
        isPath = false;
        textureBase = texture = new SpriteSheet("textures/case.png", 50, 50);
        animation = new Animation(texture, 1000);
        isOccupied = false;
    }

    public Tile(Boolean isOccupied) throws SlickException {
        isPath = true;
        textureBase = texture = new SpriteSheet("textures/case.png", 50, 50);
        test = new SpriteSheet("textures/case2.png", 50, 50);
        animation = new Animation(texture, 1000);
        this.isOccupied = isOccupied;
    }

    public void show(int x, int y) {
        textureBase.draw(x, y);
        animation.draw(x, y);
        if (isPath)
            test.draw(x, y);

    }

    public void addTower(SpriteSheet ss) {
        animation = new Animation(ss, 100);
        texture = ss;
        isOccupied = true;
    }

    public void removeTower() throws SlickException {
        texture = new SpriteSheet("textures/case.png", 50, 50);
        animation = new Animation(texture, 1000);
        isOccupied = false;
    }
    public Boolean getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(Boolean occupied) {
        isOccupied = occupied;
    }

    public Boolean getIsPath() {
        return isPath;
    }

    public void setIsPath(Boolean isPath) {
        this.isPath = isPath;
    }
}
