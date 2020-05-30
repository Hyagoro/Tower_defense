package towerDef;

import org.newdawn.slick.Graphics;
import towerDef.announce.Announces;

public class InfoGame {
    private int resolutionX;
    private int resolutionY;
    public Announces a;
    private int money;
    private int nbLife;
    public int numWave = 1;

    InfoGame(int resolutionX, int resolutionY) {
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        a = new Announces(15, this.resolutionY - 20);
        nbLife = 20;
        money = 1000;
    }

    void show(Graphics g, SpawnManager gs, int xAbsolu, int yAbsolu) {
        g.drawString("Vague : " + numWave, (resolutionX / 2.f) - 40 - xAbsolu, 10 - yAbsolu);
        g.drawString(money + "$", resolutionX - 100 - xAbsolu, 10 - yAbsolu);
        g.drawString("Vies : " + nbLife, resolutionX - 100 - xAbsolu, 30 - yAbsolu);

        a.show(g, xAbsolu, yAbsolu);
    }

    void update(int delta) {
        a.update(delta);
    }

    public int getMoney() {
        return money;
    }

    /*public void setArgent(int argent)
    {
        this.argent = argent;
    }*/
    public Boolean withdrawMoney(int ammount) {
        if (money - ammount >= 0) {
            this.money = this.money - ammount;
            return true;
        } else {
            return false;
        }
    }

    public void addMoney(int prix) {
        this.money = this.money + prix;
    }

    public int getNbLife() {
        return nbLife;
    }

    public void setNbLife(int nbLife) {
        this.nbLife = nbLife;
    }
}
