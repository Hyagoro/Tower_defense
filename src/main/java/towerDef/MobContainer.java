package towerDef;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import towerDef.actor.Mob;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MobContainer {
    int nbMobsDead = 0;
    private int nombreId = 0;
    private List<Mob> mobList = new LinkedList<Mob>();

    MobContainer() {
    }

    void show(Graphics g) {
        for (Mob mob : mobList)
            if (mob.enVie()) {
                mob.afficher(g);
            }
    }

    void update(int delta, InfoGame infoGame, Map c) {
        ArrayList<Mob> toRemove = new ArrayList<Mob>();
        // Update the state of each mob
        for (Mob mob : mobList) {
            if (!mob.enVie()) {
                if (mob.getIsPathEnded()) {
                    infoGame.setNbLife(infoGame.getNbLife() - 1);
                } else {
                    infoGame.addMoney(mob.getValeur());
                }
                toRemove.add(mob);
                nbMobsDead++;
            }
        }
        // Remove dead mobs
        for (Mob mob : toRemove) {
            mobList.remove(mob);
        }
        // Move each mob
        for (Mob mob : mobList) {
            mob.deplacement(delta, c);
        }
    }

    void add(int vie, float vitesse, int valeur) throws SlickException {
        ((LinkedList<Mob>) mobList).addLast(new Mob(0, 0, vie, 10, nombreId++, vitesse, valeur));
    }

    public Mob get(int i) {
        return mobList.get(i);
    }

    public Mob getById(int id) {
        for (Mob mob : mobList) {
            if (mob.getId() == id) {
                return mob;
            }
        }
        return null;
    }

    public Boolean exists(int id) {
        for (Mob mob : mobList) {
            if (mob.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return mobList.size();
    }
}
