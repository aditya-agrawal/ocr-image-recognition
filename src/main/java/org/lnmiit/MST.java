package org.lnmiit;

import java.util.ArrayList;

/**
 * Created by aditya on 30/4/16.
 */

class MST
{
    private int V=7;

    MST(int V){
        this.V = V;
    }

    int maxKey(int key[], Boolean mstSet[])
    {
        // Initialize min value
        int max = Integer.MIN_VALUE, max_index=-1;

        for (int v = 0; v < V; v++)
            if (mstSet[v] == false && key[v] > max)
            {
                max = key[v];
                max_index = v;
            }

        return max_index;
    }

    ArrayList<JunctionGraphEdge> printMST(int parent[], int n, int graph[][])
    {
        ArrayList<JunctionGraphEdge> edges = new ArrayList<>();

        for (int i = 1; i < V; i++){
         /**   System.out.println(parent[i]+" - "+ i+"    "+
                    graph[i][parent[i]]);*/

            JunctionGraphEdge junctionGraphEdge= new JunctionGraphEdge(parent[i],i,graph[i][parent[i]]);
            edges.add(junctionGraphEdge);
        }
        return edges;
    }

    ArrayList<JunctionGraphEdge> primMST(int graph[][])
    {
        int parent[] = new int[V];


        int key[] = new int [V];

        Boolean mstSet[] = new Boolean[V];

        for (int i = 0; i < V; i++)
        {
            key[i] = Integer.MIN_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;

        parent[0] = -1;


        for (int count = 0; count < V-1; count++)
        {

            int u = maxKey(key, mstSet);

            mstSet[u] = true;

            for (int v = 0; v < V; v++)

                if (graph[u][v]!=0 && mstSet[v] == false &&
                        graph[u][v] >  key[v])
                {
                    parent[v]  = u;
                    key[v] = graph[u][v];
                }
        }

        // print the constructed MST
        return printMST(parent, V, graph);
    }
}