package towerDef.pathFinding;

import towerDef.Map;

import java.util.ArrayList;

public class MazeSolver {
    private int[][] edgeMatrix;
    private int[][] arrayResolution;
    private int nbVertices;
    private ArrayList<Integer> path;
    private final int ID = 0;
    private final int WEIGHT = 1;
    private final int COVERED = 2;
    private final int NEXT = 3;


    public MazeSolver(Map map) {
        int nbSomX = map.getResolutionX() / map.getSize();
        int nbSomY = map.getResolutionY() / map.getSize();
        nbVertices = (nbSomX) * (nbSomY);
        edgeMatrix = new int[nbVertices][nbVertices];
        arrayResolution = new int[4][nbVertices];
        path = new ArrayList<Integer>();
        for (int i = 0; i < nbVertices; i++) {
            for (int j = 0; j < nbVertices; j++) {
                edgeMatrix[i][j] = -1;
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

    private void initialization() {
        int j;
        for (j = 0; j < nbVertices; j++) {
            arrayResolution[ID][j] = -1;                        // 0 Vertex ID
            arrayResolution[WEIGHT][j] = 34000;                 // 1 infinite weight representation
            arrayResolution[COVERED][j] = 0;                    // 2 Is already covered (false by default)
        }
    }

    public void addVertex(int j, int i, int nbVerticesX, int weight) {
        int d = (i * nbVerticesX) + j;

        // if the left bloc if on the map and is adjacent (to avoid sphere effect)
        if (d - 1 >= 0 && d - 1 != (i * nbVerticesX) - 1)
        {
            edgeMatrix[d - 1][d] = weight;
        }
        // if the right bloc if on the map and is adjacent (to avoid sphere effect)
        if (d + 1 < nbVertices && d != ((i + 1) * nbVerticesX) - 1)  // to avoid sphere effect
        {
            edgeMatrix[d + 1][d] = weight;
        }
        // if the top bloc if on the map and is adjacent (to avoid sphere effect)
        if (d + nbVerticesX < nbVertices) {
            edgeMatrix[d + nbVerticesX][d] = weight;
        }
        // if the bottom bloc if on the map and is adjacent (to avoid sphere effect)
        if (d - nbVerticesX >= 0) {
            edgeMatrix[d - nbVerticesX][d] = weight;
        }
    }


    private int minimumNotCovered() {
        int j, i;
        int minimumValue = Integer.MAX_VALUE;
        int minimumValueVertex = -1;

        for (i = 0; i < nbVertices; i++)
            for (j = 0; j < nbVertices; j++) {
                if (edgeMatrix[i][j] != -1)               // if there is an edge
                {
                    if (arrayResolution[COVERED][j] == 0) // if this edge haven't been covered // j étant l'arrivée
                    {
                        if (arrayResolution[WEIGHT][j] < minimumValue) // if the weight is smaller than the reference value
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

    // TODO implement and use A* instead of Dijkstra (keep both)
    public boolean resolutionDijkstra(int startVertex, int endVertex) {
        ArrayList<Integer> previousPath = path;
        int node1 = startVertex;

        initialization();
        path.clear();

        arrayResolution[WEIGHT][startVertex] = 0;
        while (!isEachVertexCovered()) {
            arrayResolution[COVERED][node1] = 1;
            for (int node2 = 0; node2 < nbVertices; node2++) {
                if (edgeMatrix[node1][node2] != -1)
                {
                    if (arrayResolution[WEIGHT][node2] > (arrayResolution[WEIGHT][node1] + edgeMatrix[node1][node2])) {
                        arrayResolution[WEIGHT][node2] = arrayResolution[WEIGHT][node1] + edgeMatrix[node1][node2];
                        arrayResolution[NEXT][node2] = node1;
                    }
                }
            }

            node1 = minimumNotCovered();
            if (node1 == -1)
                break;
        }
        int n = endVertex;

        // if the weight of end vertex if a wall return false (no path)
        if (arrayResolution[WEIGHT][n] >= 34000) {
            return false;
        }

        // we go from the end to the beginning to recreate the path from NEXT tags
        // while end vertex is different than begin vertex
        while (n != startVertex) {
            path.add(0, n);
            n = arrayResolution[NEXT][n];
        }
        // add the start vertex at the beginning of the path
        path.add(0, startVertex);
	    
	   /* for(int i = 0; i < chemin.size(); i++)
	    	System.out.println("indice : "+i+" Chemin : "+chemin.get(i));*/

	    // if the two vertices are start and end
        if (path.size() == 2) {
            path = previousPath;
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

}