package towerDef.actor;

import org.newdawn.slick.*;

public class Projectile {
    private float xBase, yBase;
    private int taille;
    private float x, y;
    private float xCible, yCible;
    private float mult1, mult2;
    private float dommage;
    private Boolean afficher;
    private Boolean collision;
    private float vitesse = 0.25f;

    SpriteSheet skin;
    Animation anim;
    Image aura;

    Projectile(float coordX, float coordY, float dommage, int tempsRecharge, int taille) throws SlickException {
        collision = false;
        this.taille = taille;
        afficher = false;
        this.dommage = dommage;
        xBase = x = coordX;
        yBase = y = coordY;

        aura = new Image("textures/aura.png");
        skin = new SpriteSheet("textures/testProj.png", taille, taille);
        anim = new Animation(skin, 100);
    }

    void ciblage(float x, float y) {
        xCible = x;
        yCible = y;
        afficher = true;
    }

    public void changementDeCible() {
        finDeplacement();
    }

    public void afficher(Graphics g) {
        if (afficher) {
            anim.draw(x, y);
            aura.draw(x - (aura.getWidth() / 2.f), y - (aura.getHeight() / 2.f));
        }
    }

    public void finDeplacement() {
        afficher = false;
        x = xBase;
        y = yBase;

    }

    public void deplacement(int delta) {
        deplacement2(delta);
    }

    private void deplacement2(int delta) {
        if (xCible - x > 0) {
            if (yCible - y > 0) { //on a les deux parties du test positives
                if (xCible - x > yCible - y) {
                    mult1 = 1;
                    mult2 = (yCible - y) / (xCible - x);
                } else if (xCible - x < yCible - y) {
                    mult1 = (xCible - x) / (yCible - y);
                    mult2 = 1;
                }
            } else if (yCible - y < 0) {    //on a les x positifs et les y n�gatifs
                if (xCible - x > -(yCible - y)) {
                    mult1 = 1;//positif
                    mult2 = (yCible - y) / (xCible - x);//negatif
                } else if (xCible - x < -(yCible - y)) {
                    mult1 = (xCible - x) / -(yCible - y);//positif
                    mult2 = -1;//negatif
                }
            }

        } else if (xCible - x < 0) {
            if (yCible - y > 0) { //negatif .. positif
                if (-(xCible - x) > yCible - y) {
                    mult1 = -1;//negatif
                    mult2 = (yCible - y) / -(xCible - x);//positif
                } else if (-(xCible - x) < yCible - y) {
                    mult1 = (xCible - x) / (yCible - y);//negatif
                    mult2 = 1;//positif
                }
            } else if (yCible - y < 0) {    //les deux negatifs
                if (-(xCible - x) > -(yCible - y)) {
                    mult1 = -1;
                    mult2 = -((yCible - y) / (xCible - x));
                } else if (-(xCible - x) < -(yCible - y)) {
                    mult1 = -((xCible - x) / (yCible - y));
                    mult2 = -1;
                }
            }
        }
        if (x - 5 <= xCible && xCible <= x + 5 && y - 5 <= yCible && yCible <= y + 5) {
            collision = true;
            this.finDeplacement();
        }
        if (xCible != x && yCible != y && mult1 != 0 && mult2 != 0) {
            x = x + (mult1 * vitesse * delta);
            y = y + (mult2 * vitesse * delta);
        }
    }

    Boolean getCollision() {
        return collision;
    }

    void setCollision(Boolean b) {
        collision = b;
    }

    public float getDommage() {
        return dommage;
    }

    public Boolean getAfficher() {
        return afficher;
    }
}
