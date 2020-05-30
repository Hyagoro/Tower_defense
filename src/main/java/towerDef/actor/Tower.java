package towerDef.actor;

import org.newdawn.slick.*;
import towerDef.MobContainer;

public class Tower {
    private int x, y;
    private int prix;
    SpriteSheet skin;
    private Animation anim;
    Projectile projectile;
    private int portee;
    private int idCible;
    private int taille;
    private int i;
    private int j;
    private int tempsTir;
    private int tempsRecharge;
    private int timerRecharge;
    private Boolean afficherPortee;
    private Boolean collision;
    private Boolean ciblerTirer;//0cibler, 1tirer
    private Boolean recharge;

    public Tower(int coordX, int coordY, int tailleTour) throws SlickException {
        prix = 30;
        tempsTir = 0;
        tempsRecharge = 500;
        timerRecharge = 0;
        recharge = false;
        afficherPortee = false;
        collision = false;
        ciblerTirer = false;
        this.taille = tailleTour;
        i = coordX / taille;
        j = coordY / taille;
        x = i * taille;
        y = j * taille;
        projectile = new Projectile(x + (taille / 2.f) - 2,
                              y + (taille / 2.f) - 2, 40, 800, 5);
        portee = 100;
        skin = new SpriteSheet("textures/skinTour.png", tailleTour, tailleTour);
        anim = new Animation(skin, 100);
    }

    void recharge(int delta) {
        if (timerRecharge < tempsRecharge - tempsTir) {
            timerRecharge = timerRecharge + delta;
            recharge = true;
        } else {
            tempsTir = 0;
            timerRecharge = 0;
            recharge = false;
        }
    }

    int idMobLePlusProche(MobContainer mc) {
        int idMin = mc.get(0).getId();
        float min = distance(mc.get(0));
        for (int j = 0; j < mc.size(); j++) {
            if (distance(mc.get(j)) < min) {
                idMin = mc.get(j).getId();
                min = distance(mc.get(j));
            }
        }
        return idMin;
    }

    public void update(MobContainer mc, int delta) {
        if (!recharge) {
            ciblerTirer(mc, delta);
        } else {
            recharge(delta);
        }
    }

    public void ciblerTirer(MobContainer mc, int delta) {
        if (!ciblerTirer)//doit cibler
        {
            idCible = idMobLePlusProche(mc);
            ciblerTirer = true;//doit tirer = vrai
        } else if (ciblerTirer)//doit tirer
        {
            if (mc.exists(idCible) && mc.getById(idCible).enVie())//cible existante et en vie
            {
                if (distance(mc.getById(idCible)) < portee)//cible à portée
                {
                    projectile.ciblage(mc.getById(idCible).getX(), mc.getById(idCible).getY());
                    projectile.deplacement(delta);
                    tempsTir += delta;
                    if (projectile.getCollision()) {
                        mc.getById(idCible).retirerVie(projectile.getDommage());
                        projectile.setCollision(false);
                        recharge = true;
                    }
                } else if (distance(mc.getById(idCible)) >= portee && projectile.getAfficher())//cible pas a portée et projectile en deplacement
                {
                    projectile.ciblage(mc.getById(idCible).getX(), mc.getById(idCible).getY());
                    projectile.deplacement(delta);
                    tempsTir += delta;
                    if (projectile.getCollision()) {
                        mc.getById(idCible).retirerVie(projectile.getDommage());
                        projectile.setCollision(false);
                        ciblerTirer = false;
                        recharge = true;
                    }
                } else {
                    projectile.finDeplacement();
                    ciblerTirer = false;
                }
            } else {
                projectile.finDeplacement();
                ciblerTirer = false;
            }
        }

    }

    public void afficherPortee(Graphics g) {
        g.setColor(Color.green);
        g.drawOval((x + taille / 2.f) - (portee), (y + taille / 2.f) - (portee), portee * 2, portee * 2);
        g.setColor(new Color(0, 255, 0, 100));
        g.fillOval((x + taille / 2.f) - (portee), (y + taille / 2.f) - (portee), portee * 2, portee * 2);
        g.setColor(Color.white);
    }

    public void show() {
        anim.draw(i * taille, j * taille);
    }

    private float distance(Mob mob) {
        return (float) Math.sqrt(((mob.getX() - (x + taille / 2.f)) *
                     (mob.getX() - (x + taille / 2.f))) + ((mob.getY() - (y + taille / 2.f)) *
                     (mob.getY() - (y + taille / 2.f))));
    }

    public SpriteSheet getSkin() {
        return skin;
    }

    public void setSkin(SpriteSheet skin) {
        this.skin = skin;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public int getPortee() {
        return portee;
    }

    public Boolean getAfficherPortee() {
        return afficherPortee;
    }

    public void setAfficherPortee(Boolean afficherPortee) {
        this.afficherPortee = afficherPortee;
    }

    public Boolean getCollision() {
        return collision;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getPrix() {
        return prix;
    }
}
