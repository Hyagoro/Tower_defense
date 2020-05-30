package towerDef.actor;

import org.newdawn.slick.*;
import towerDef.Map;
import towerDef.graphical.Movable;
import towerDef.graphical.LifeBar;


public class Mob extends Movable {
    private int id;
    private int value;
    private int size;
    private float speed;
    private int xCible, yCible;
    private float mult1, mult2;
    private int numNoeud = 0;
    private int noeudChemin = 0;
    private Boolean isPathEnded = false;
    private LifeBar lifeBar;

    public Mob(int coordX, int coordY, float currentLife, int size, int id, float speed, int value) throws SlickException {
        this.value = value;
        this.speed = speed;
        this.id = id;
        this.size = size;
        this.lifeBar = new LifeBar(x, y, currentLife);
        this.x = coordX;
        this.y = coordY;
        this.xCible = 0;
        this.yCible = 0;
        this.mult1 = 0;
        this.mult2 = 0;
        //System.out.println("ID : "+id);
        if (currentLife <= 250)
            skin = new SpriteSheet("textures/testMob.png", size, size);
        else if (speed > 0.05f)
            skin = new SpriteSheet("textures/testMob2.png", size, size);
        else if (currentLife <= 10000 && speed < 0.05f)
            skin = new SpriteSheet("textures/testMob3.png", size, size);
        else
            skin = new SpriteSheet("textures/testMob.png", size, size);
        anim = new Animation(skin, 300);
    }

    public void deplacement(int delta, Map map) {
        if (enVie()) {
            if (xCible - x > 0) {
                if (yCible - y > 0) { //on a les deux parties du test positives
                    if (xCible - x >= yCible - y) {
                        mult1 = 1;
                        mult2 = (yCible - y) / (xCible - x);
                    } else if (xCible - x < yCible - y) {
                        mult1 = (xCible - x) / (yCible - y);
                        mult2 = 1;
                    }
                } else if (yCible - y < 0) {    //on a les x positifs et les y n�gatifs
                    if (xCible - x >= -(yCible - y)) {
                        mult1 = 1;//positif
                        mult2 = (yCible - y) / (xCible - x);//negatif
                    } else if (xCible - x < -(yCible - y)) {
                        mult1 = (xCible - x) / -(yCible - y);//positif
                        mult2 = -1;//negatif
                    }
                }

            } else if (xCible - x < 0) {
                if (yCible - y > 0) { //negatif .. positif
                    if (-(xCible - x) >= yCible - y) {
                        mult1 = -1;//negatif
                        mult2 = (yCible - y) / -(xCible - x);//positif
                    } else if (-(xCible - x) < yCible - y) {
                        mult1 = (xCible - x) / (yCible - y);//negatif
                        mult2 = 1;//positif
                    }
                } else if (yCible - y < 0) {    //les deux negatifs
                    if (-(xCible - x) >= -(yCible - y)) {
                        mult1 = -1;
                        mult2 = -((yCible - y) / (xCible - x));
                    } else if (-(xCible - x) < -(yCible - y)) {
                        mult1 = -((xCible - x) / (yCible - y));
                        mult2 = -1;
                    }
                }
            }

            if (x - 5 <= xCible && xCible <= x + 5 && y - 5 <= yCible && yCible <= y + 5) {
                if (numNoeud > 0)
                    if (map.getPath().get(numNoeud - 1) == 99) {
                        lifeBar.setCurrentLife(0);
                        isPathEnded = true;
                    }


                if (map.getPath().size() > numNoeud)
                    noeudChemin = map.getPath().get(numNoeud);
				
				/*if(this.id == 0)
				{
					System.out.println("indice max chemin "+c.getChemin().size());
					System.out.println("numNoeud : "+numNoeud+"noeudChemin :"+noeudChemin);
				}*/

                numNoeud++;


                yCible = (noeudChemin / (map.getResolutionX() / map.getSize()));
                xCible = (noeudChemin - (yCible * (map.getResolutionX() / map.getSize())));

                yCible = (yCible * map.getSize()) + map.getSize() / 2;
                xCible = (xCible * map.getSize()) + map.getSize() / 2;
				
				/*if(this.id == 0)
				{
					System.out.println("xCible : "+xCible+" yCible :"+yCible);
				}	*/
            }
            if (xCible != x && yCible != y && mult1 != 0 && mult2 != 0) {
                //System.out.println("x = "+x+" y = "+y);
                //System.out.println("mult1 = "+mult1+" mult2 = "+mult2);
                x = x + (mult1 * speed * delta);
                y = y + (mult2 * speed * delta);
            }
        }
        lifeBar.update(this.x, this.y);
    }

    public int getId() {
        return id;
    }

    public Boolean enVie() {
        return !(lifeBar.getCurrentLife() <= 0);
    }

    public void retirerVie(float dommage) {
        this.lifeBar.setCurrentLife(this.lifeBar.getCurrentLife() - dommage);
    }

    public void show(Graphics g) {
        super.show(g);
        //g.drawRect(x, y, 5, 5);
        this.lifeBar.show(g);
    }

    public Boolean getIsPathEnded() {
        return isPathEnded;
    }

    public int getValue() {
        return value;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getxCible() {
        return xCible;
    }

    public int getyCible() {
        return yCible;
    }
}
