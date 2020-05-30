package towerDef;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import towerDef.actor.Tower;

import java.util.ArrayList;
import java.util.List;

public class TowerContainer {
    private List<Tower> towerList = new ArrayList<Tower>(5);

    TowerContainer() {
        towerList.clear();
    }

    void afficherProjectiles(Graphics g, Map map) {
        for (Tower tower : towerList) {
            tower.getProjectile().afficher(g);
            if (tower.getAfficherPortee()) {
                tower.showRange(g);
            }
        }
    }

    void update(int delta, MobContainer mobContainer) {
        if (mobContainer != null && mobContainer.size() > 0) {
            for (Tower tower : towerList) {
                tower.update(mobContainer, delta);
            }
        } else {
            for (Tower tower : towerList) {
                tower.getProjectile().finDeplacement();
            }
        }
    }

    private void add(int x, int y, Map map) throws SlickException {
        if (map.isFree(x, y)) {
            Tower tower = new Tower(x, y, map.size);
            towerList.add(tower);
            map.add(x, y, tower);
        }
    }

    private void remove(int x, int y, Map map) throws SlickException {
        ArrayList<Integer> indicesToRemove = new ArrayList<Integer>();
        if (!map.isFree(x, y)) {
            for (int i = 0; i < towerList.size(); i++) {
                if (towerList.get(i).getI() == x / map.size && towerList.get(i).getJ() == y / map.size) {
                    indicesToRemove.add(i);
                    map.remove(x, y);
                }
            }
            for (int i : indicesToRemove) {
                towerList.remove(i);
            }
        }
    }

    private Tower getTour(int x, int y, Map map) {
        for (Tower tower : towerList) {
            if (tower.getI() == x / map.size && tower.getJ() == y / map.size) {
                return tower;
            }
        }
        return null;

    }

    private boolean isTowerHere(int x, int y, Map map) {
        for (Tower tower : towerList) {
            if (tower.getI() == x / map.size && tower.getJ() == y / map.size) {
                return true;
            }
        }
        return false;
    }

    void event(int button, int x, int y, int clickCount, Map map, InfoGame ip) {
        try {
            if (button == Input.MOUSE_LEFT_BUTTON) {
                if (map.isFree(x, y)) {
                    if (ip.getMoney() >= 30) {
                        add(x, y, map);
                        ip.withdrawMoney(getTour(x, y, map).getPrix());
						/*if(!carte.resoudre())
						{
							System.out.println("�a vire !");
							ip.ajouterArgent(getTour(x, y, carte).prix);
							retirer(x, y, carte);
						}*/

                    }
                } else if (!map.isPath(x, y) && isTowerHere(x, y, map)) {
                    boolean toSet = !getTour(x, y, map).getAfficherPortee();
                    getTour(x, y, map).setAfficherPortee(toSet);
                }
            }
            if (button == Input.MOUSE_RIGHT_BUTTON) {
                if (!map.isFree(x, y) && !map.isPath(x, y)) {
                    remove(x, y, map);
                    ip.addMoney(30 * 80 / 100);
                    map.solve();
                }
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        return this.size();
    }

    public Tower get(int i) {
        return this.get(i);
    }

}
