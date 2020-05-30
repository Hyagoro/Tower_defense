package towerDef.actor;

import org.newdawn.slick.*;
import towerDef.MobContainer;
import towerDef.graphical.Bloc;

public class Tower extends Bloc {
    private int prix;
    Projectile projectile;
    private int portee;
    private int idCible;
    private int i;
    private int j;
    private int tempsTir;
    private int tempsRecharge;
    private int timerRecharge;
    private Boolean afficherPortee;
    private Boolean ciblerTirer;//0cibler, 1tirer
    private Boolean recharge;

    public Tower(int x, int y, int towerSize) throws SlickException {
        super((x / towerSize) * towerSize, (y / towerSize) * towerSize);

        this.prix = 30;
        this.tempsTir = 0;
        this.tempsRecharge = 500;
        this.timerRecharge = 0;
        this.recharge = false;
        this.afficherPortee = false;
        this.collision = false;
        this.ciblerTirer = false;

        this.size = towerSize;
        this.i = this.x / size;
        this.j = this.y / size;

        this.projectile = new Projectile(this.x + (size / 2.f) - 2,
                              this.y + (size / 2.f) - 2, 40, 800, 5);
        this.portee = 100;
        this.skin = new SpriteSheet("textures/skinTour.png", towerSize, towerSize);
        this.animation = new Animation(skin, 100);
    }

    private void recharge(int delta) {
        if (timerRecharge < tempsRecharge - tempsTir) {
            timerRecharge = timerRecharge + delta;
            recharge = true;
        } else {
            tempsTir = 0;
            timerRecharge = 0;
            recharge = false;
        }
    }

    private int idMobLePlusProche(MobContainer mc) {
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

    public void showRange(Graphics g) {
        g.setColor(Color.green);
        g.drawOval((x + size / 2.f) - (portee), (y + size / 2.f) - (portee), portee * 2, portee * 2);
        g.setColor(new Color(0, 255, 0, 100));
        g.fillOval((x + size / 2.f) - (portee), (y + size / 2.f) - (portee), portee * 2, portee * 2);
        g.setColor(Color.white);
    }

    public void show() {
        animation.draw(i * size, j * size);
    }

    private float distance(Mob mob) {
        return (float) Math.sqrt(((mob.getX() - (x + size / 2.f)) *
                     (mob.getX() - (x + size / 2.f))) + ((mob.getY() - (y + size / 2.f)) *
                     (mob.getY() - (y + size / 2.f))));
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
