package org.lnmiit;

/**
 * Created by aditya on 22/4/16.
 */
public class GraphMatrix {
    public static int[][] getGraphmatrix(Graph graph){
        int[][] graphMatrix = new int[10][10];
        for(int i =0 ; i<10 ; i++){
            for(int j=0; j<10 ; j++){
                if(graph.containsEdge(new Edge(new Vertex(i + ""),new Vertex(j + ""))))
                    graphMatrix[i][j] = 1;
                else
                    graphMatrix[i][j] = 0;
            }
        }
        return graphMatrix;
    }
}
