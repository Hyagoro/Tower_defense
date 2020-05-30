package towerDef;

import org.newdawn.slick.SlickException;
import towerDef.announce.Announces;

public class SpawnManager {
    private int baseLife = 200;
    private int baseLifeBoss = 700;
    private int timerBetweenMobs = 0;
    private int timerBetweenWave = 0;
    private Boolean waitBetweenWaves = true;
    private int nbMobsWave;
    public int nbMobsCurrent;

    SpawnManager() {
        nbMobsCurrent = 0;
        nbMobsWave = 5;
    }

    public void update(int delta, MobContainer mc, InfoGame ip, Announces a, Map c) throws SlickException {
        if (waitBetweenWaves)//vague suivante en attente
        {
            if (timerBetweenWave == 0)//Annonce des mobs !
            {
                c.solve();
                a.add("Nombre de mobs � la vague suivante : " + nbMobsWave + ".");
                if (ip.numWave % 4 == 0) {
                    a.add("Elite : 1");
                }
                //a.ajout("Ceci est une annonce !");
            }
            timerBetweenWave += delta;
            if (timerBetweenWave >= 3000) {
                timerBetweenWave = 0;
                waitBetweenWaves = false;
            }
        } else {
            timerBetweenMobs += delta;
            if (nbMobsCurrent < nbMobsWave)//vague en cours
            {
                if (timerBetweenMobs >= 500) {
                    if (nbMobsCurrent == nbMobsWave - 1)//si c'est le dernier (boss)
                    {
                        if (ip.numWave % 4 == 0) {
                            mc.add(1600, 0.035f, 15);
                        } else {
                            mc.add(baseLife, 0.05f, 2);
                        }
                    } else {
                        if (nbMobsCurrent >= (nbMobsWave * 3 / 4) + 2)//1 quart -2 des mobs sont plus chaud
                        {
                            mc.add(baseLifeBoss, 0.07f, 10);
                        } else {
                            mc.add(baseLife, 0.05f, 2);
                        }
                    }
                    nbMobsCurrent++;
                    timerBetweenMobs = 0;
                }
            }
            if (nbMobsCurrent == mc.nbMobsDead && nbMobsCurrent == nbMobsWave)//vague termin�e
            {
                nbMobsWave++;
                nbMobsCurrent = 0;
                ip.numWave++;
                mc.nbMobsDead = 0;
                waitBetweenWaves = true;
                baseLife = baseLife + (baseLife * 5 / 100);
                baseLifeBoss = baseLifeBoss + (baseLifeBoss * 5 / 100);
            }
        }
    }
}
