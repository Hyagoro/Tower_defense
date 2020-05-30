package towerDef;

import org.newdawn.slick.SlickException;
import towerDef.actor.Tower;
import towerDef.pathFinding.MazeSolver;

import java.util.ArrayList;

public class Map {
    private Tile[][] map;
    private int resolutionX;
    private int resolutionY;
    int size;
    private ArrayList<Integer> path;
    private MazeSolver mazeSolver;

    Map(int resolutionX, int resolutionY, int size) throws SlickException {
        this.resolutionY = resolutionY;
        this.resolutionX = resolutionX;
        this.size = size;
        map = new Tile[resolutionX / size][resolutionY / size];
        this.init();
        mazeSolver = new MazeSolver(this);
    }

    public Tile getTile(int x, int y) {
        return map[x][y];
    }

    private void init() throws SlickException {
        for (int i = 0; i < resolutionX / size; i++) {
            for (int j = 0; j < resolutionY / size; j++) {
                map[i][j] = new Tile();
            }
        }
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    Boolean isFree(int x, int y) throws SlickException {
        if (map[x / size][y / size].getIsOccupied()) {
            return false;
        } else {
            mazeSolver.addVertex(x / size, y / size, resolutionX / size, 34000);
            if (solve())  //si on peux resoudre avec cette case supplémentaire
            {
                mazeSolver.addVertex(x / size, y / size, resolutionX / size, 1);
                //resoudre();//Foutre un boolean pour ne pas retourner de chemin
                return true;
            } else {
                mazeSolver.addVertex(x / size, y / size, resolutionX / size, 1);
                solve();
                return false;
            }
        }
    }

    public Boolean isPath(int x, int y) {
		return map[x / size][y / size].getIsPath();
    }

    public void add(int x, int y, Tower tower) {
        map[x / size][y / size].addTower(tower.getSkin());
        mazeSolver.addVertex(x / size,
                             y / size,
                       resolutionX / size,
                         34000);
    }

    public void remove(int x, int y) throws SlickException {
        map[x / size][y / size].removeTower();
        mazeSolver.addVertex(x / size,
                             y / size,
                       resolutionX / size,
                         1);
    }

    public void show() {
        for (int i = 0; i < resolutionX / size; i++) {
            for (int j = 0; j < resolutionY / size; j++) {
                map[i][j].show(i * size, j * size);
            }
        }
    }


    public boolean solve() throws SlickException {
        boolean isExistingPath = mazeSolver.resolutionDijkstra(0, 99);
        if (isExistingPath) {
            path = mazeSolver.getPath();
        } else {
            return false;
        }


        int a, b;
        for (int i = 0; i < path.size(); i++) {
            int nbEdgeX = (resolutionX / size);
            a = path.get(i) / nbEdgeX ;
            b = path.get(i) - (a * nbEdgeX);
            //System.out.println("Case["+b+"]["+a+"]");
            if (i == 0 || i == path.size() - 1) // if the is the end of the beginning
                map[b][a] = new Tile(true);
            else
                map[b][a] = new Tile();
        }
        return true;
    }

    @SuppressWarnings("unused")
    private boolean isSolvable() {
        return mazeSolver.resolutionDijkstra(0, 99);

    }

    @SuppressWarnings("unused")
    private void supprimerChemin() throws SlickException {
        int a, b;
        for (int i = 0; i < path.size(); i++) {
            a = path.get(i) / (resolutionX / size);
            b = path.get(i) - (a * (resolutionY / size));
            //System.out.println("Case["+b+"]["+a+"]");
            map[b][a] = new Tile();
        }
    }

    public int getResolutionX() {
        return resolutionX;
    }

    public void setResolutionX(int resolutionX) {
        this.resolutionX = resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }

    public void setResolutionY(int resolutionY) {
        this.resolutionY = resolutionY;
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Tile[][] getMap() {
        return map;
    }

    public void setMap(Tile[][] map) {
        this.map = map;
    }
}
