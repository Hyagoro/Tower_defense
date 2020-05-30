package towerDef.actor;

import org.newdawn.slick.*;
import towerDef.Map;


public class Mob {
    private int id;
    private int valeur;
    private int taille;
    private float vitesse;
    private float x;
    private float y;
    private int xCible, yCible;
    private float mult1, mult2;
    private float baseVie;
    private float vie;
    private int numNoeud = 0;
    private int noeudChemin = 0;
    private Boolean isPathEnded = false;
    private SpriteSheet skin;
    private Animation anim;

    public Mob(int coordX, int coordY, float vie, int taille, int id, float vitesse, int valeur) throws SlickException {
        this.valeur = valeur;
        this.vitesse = vitesse;
        this.id = id;
        this.taille = taille;
        baseVie = vie;
        this.vie = vie;
        x = coordX;
        y = coordY;
        xCible = 0;
        yCible = 0;
        mult1 = 0;
        mult2 = 0;
        //System.out.println("ID : "+id);
        if (vie <= 250)
            skin = new SpriteSheet("textures/testMob.png", taille, taille);
        else if (vitesse > 0.05f)
            skin = new SpriteSheet("textures/testMob2.png", taille, taille);
        else if (vie <= 10000 && vitesse < 0.05f)
            skin = new SpriteSheet("textures/testMob3.png", taille, taille);
        else
            skin = new SpriteSheet("textures/testMob.png", taille, taille);
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
                        vie = 0;
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
                x = x + (mult1 * vitesse * delta);
                y = y + (mult2 * vitesse * delta);
            }
        }
    }

    public int getId() {
        return id;
    }

    public Boolean enVie() {
        return !(vie <= 0);
    }

    public void retirerVie(float dommage) {
        vie = vie - dommage;
    }

    public void afficher(Graphics g) {
        anim.draw(x, y);
        //g.drawRect(x, y, 5, 5);
        g.setColor(Color.red);
        g.drawRect(x - 5, y - 6, 20, 0);
        g.setColor(Color.green);
        g.drawRect(x - 5, y - 6, (20 * (vie)) / baseVie, 0);
        g.setColor(Color.white);
    }

    public Boolean getIsPathEnded() {
        return isPathEnded;
    }

    public int getValeur() {
        return valeur;
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
