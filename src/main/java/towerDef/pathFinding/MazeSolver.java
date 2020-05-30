package towerDef.pathFinding;

import towerDef.Map;

import java.util.ArrayList;

public class MazeSolver {
    private int[][] matrix;
    private int [][][] mergeMatrixArray;
    private int[][] arrayResolution;
    private int nbVertices;
    private ArrayList<Integer> path;
    private final int ID = 0;
    private final int WEIGHT = 1;
    private final int COVERED = 2;
    private final int UNKNOWN = 3;


    public MazeSolver(Map map) {
        int nbSomX = map.getResolutionX() / map.getSize();
        int nbSomY = map.getResolutionY() / map.getSize();
        nbVertices = (nbSomX) * (nbSomY);
        matrix = new int[nbVertices][nbVertices];
        arrayResolution = new int[4][nbVertices];
        mergeMatrixArray = new int[4][nbVertices][nbVertices];
        path = new ArrayList<Integer>();
        for (int i = 0; i < nbVertices; i++) {
            for (int j = 0; j < nbVertices; j++) {
                matrix[i][j] = -1;
            }
        }

        for (int i = 0; i < nbSomY; i++)    //y
        {
            for (int j = 0; j < nbSomX; j++)    //x
            {
                // if a tile is occupied w=1 else w=Integer.MAX_VALUE
                if (!map.getTile(j, i).getIsOccupied()) {
                    addVertex(j, i, nbSomX, 1);
                } else {
                    addVertex(j, i, nbSomX, 34000);
                }
            }
        }
    }

    public void addVertex(int j, int i, int nbVerticesX, int weight) {
        int d = (i * nbVerticesX) + j;

        if (d - 1 >= 0 && d - 1 != (i * nbVerticesX) - 1)           // to avoid sphere effect
        {
            matrix[d - 1][d] = weight;
        }

        if (d + 1 < nbVertices && d != ((i + 1) * nbVerticesX) - 1) // to avoid sphere effect
        {
            matrix[d + 1][d] = weight;
        }
        if (d + nbVerticesX < nbVertices) {
            matrix[d + nbVerticesX][d] = weight;
        }
        if (d - nbVerticesX >= 0) {
            matrix[d - nbVerticesX][d] = weight;
        }
    }


    private int minimumNotCovered() {
        int j, i;
        int minimumValue = Integer.MAX_VALUE;//35000;
        int minimumValueVertex = -1;

        for (i = 0; i < nbVertices; i++)
            for (j = 0; j < nbVertices; j++) {
                if (matrix[i][j] != -1)     // if there is an edge
                {
                    if (arrayResolution[COVERED][j] == 0) // si il n'a pas encore été parcouru j étant l'arrivée
                    {
                        if (arrayResolution[WEIGHT][j] < minimumValue) // si c'est le plus petit
                        {
                            minimumValue = arrayResolution[WEIGHT][j];
                            minimumValueVertex = j;
                        }
                    }
                }
            }
        return minimumValueVertex;
    }

    private boolean isEachVertexCovered() {
        int i;
        for (i = 0; i < nbVertices; i++) {
            if (arrayResolution[COVERED][i] == 0) // Still one node is not covered
                return false;
        }
        return true;
    }

    public boolean resolutionDijkstra(int beginVertex, int endVertex) {
        int n;
        ArrayList<Integer> previousPath = path;
        int n1 = beginVertex, n2;

        initialization();
        path.clear();

        arrayResolution[WEIGHT][beginVertex] = 0;
        while (!isEachVertexCovered()) {
            arrayResolution[COVERED][n1] = 1;
            for (n2 = 0; n2 < nbVertices; n2++) {
                if (matrix[n1][n2] != -1)
                {
                    if (arrayResolution[WEIGHT][n2] > (arrayResolution[WEIGHT][n1] + matrix[n1][n2])) {
                        arrayResolution[WEIGHT][n2] = arrayResolution[WEIGHT][n1] + matrix[n1][n2];
                        arrayResolution[UNKNOWN][n2] = n1;
                    }
                }
            }

            n1 = minimumNotCovered();
            if (n1 == -1)
                break;
        }
        n = endVertex;

        //System.out.println("tabResolution[1][n] : "+tabResolution[1][n]);
        if (arrayResolution[1][n] >= 34000) {
            return false;
        }

        while (n != beginVertex) {
            path.add(0, n);
            n = arrayResolution[UNKNOWN][n];

        }
        path.add(0, beginVertex);
	    
	   /* for(int i = 0; i < chemin.size(); i++)
	    	System.out.println("indice : "+i+" Chemin : "+chemin.get(i));*/

        if (path.size() == 2) {
            path = previousPath;
            return false;
        } else {
            return true;
        }
    }

    private void initialization() {
        int j;
        for (j = 0; j < nbVertices; j++) {
            arrayResolution[ID][j] = -1;                        // 0 Vertex ID
            arrayResolution[WEIGHT][j] = 34000;                 // 1 infinite weight representation
            arrayResolution[COVERED][j] = 0;                    // 2 Is already covered (false by default)
        }
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

}