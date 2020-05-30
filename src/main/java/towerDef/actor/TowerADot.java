package towerDef.actor;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TowerADot extends Tower {
    private float dmgParSec;

    public TowerADot(int coordX, int coordY, int tailleTour) throws SlickException {
        super(coordX, coordY, tailleTour);
        skin = new SpriteSheet("textures/skinTourDot.png", tailleTour, tailleTour);
        dmgParSec = projectile.getDommage() / 5;
    }

}
