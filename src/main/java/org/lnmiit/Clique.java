package org.lnmiit;

import java.util.*;

/**
 * Created by aditya on 27/4/16.
 */

public class Clique {

    private int [][] graph; // an adjacency edge matrix for a graph
    private static int numEdges;  // total number of edges in a graph
    private static int sizeClique;   // k = floor((1/2)*(log n)/log 2), n = |V|
    private static int numClique;  // number of k-cliques in a graph
    private Vector firstClique;  // a k-clique first found
    private static int totalnodes;

    /**
     * Constructor: create a data structure of a graph.
     * @param numV total number of vertices in a graph.
     */
    public Clique(String numV,int size) {

        //   System.out.println(numV+" :graph size:"+size);
        int n = new Integer(numV).intValue();
        graph = new int[n][n];
        firstClique = new Vector();
        sizeClique = size;

        //sizeClique = 50;
    }

    /**
     * Add an edge to the adjacent edge matrix while reading pairs from the input.
     * For an undirected graph, only upper-right triangle in the matrix will be
     * filled with '1' if there is an edge.
     * @param v one vertex incident to the edge
     * @param x the other vertex incident to the edge
     */
    public void addEdge(String v, String x) {
        int idxV1 = new Integer(v).intValue();
        int idxV2 = new Integer(x).intValue();
        graph[idxV1][idxV2] = 1;
        graph[idxV2][idxV1] = 1;

        numEdges++;
    }

    /**
     * Check if there is an edge between vertex i and vertex j.
     */
    public boolean isConnected(int i, int j) {
        return graph[i][j] == 1;
    }



    /**
     * Using recursive DFS, find k-cliques in a graph. Store only the first found
     * k-clique whereas it counts the number of all k-cliques in a graph.
     * @param A a vector to be tested if A is j-clique
     */

    public boolean anyConnect( int a, Vector A) {
        int k;


        for(k=A.size()-1;k>=0;k--)
            if(isConnected(a, (Integer)A.get(k)) || isConnected( (Integer)A.get(k), a))
                return true;
        return false;
    }


    public void printVector( Vector A) {
        int j;
        for(j=0;j<A.size();j++)
        { System.out.print(A.get(j)+" "); }
    }

    public void doCliqueBT(Vector A, int j,Set<Set<Integer>> cliques) {
        Set<Integer> result = new HashSet<>();
        // If j is equal to size of clique, k, then A is k-clique in the graph
        if (j == sizeClique) {
            if (firstClique.isEmpty())  {  firstClique = A;  }
            numClique++;
            ///////////////////////////////////////////////////////////////////
            // The following is to display all k-cliques in a graph
            // - comment out this portion if the input graph is expected
            //   to have a lot of such cliques.
            //
            // System.out.print("Vertices in a clique: ");
            //  System.out.print(" for clique :")  ;
            //   System.out.print("checkssss:");
            for (int i=0; i<A.size(); i++) {
                Integer v = (Integer)A.get(i);
                //System.out.print(v.intValue()+", ");
                result.add(v.intValue());
            }

            //System.out.println();
            //CalculateCliques(A);
            cliques.add(result);
            ///////////////////////////////////////////////////////////////////
            return;
        }
        else {
            j = j + 1;
            // Sj is the set of all candidate vectors for j-clique
            ArrayList Sj = new ArrayList();
            if (j <= sizeClique) {
                Sj = getCandidates(A);
            }
            if (!Sj.isEmpty()) {
                // For each candidate vector in Sj,
                // recursively do backtracking for k-clique
                for (int i=0; i<Sj.size(); i++) {
                    Vector a = (Vector)Sj.get(i);
                    doCliqueBT(a, j ,cliques);
                }
            }
        }
    }

    /**
     * Return a set of candidates, Sq, for q-clique. Each candidate is a vector
     * in which a newly added vertex must be greater than the last vertex in the
     * given vector, A, and must be connected to all vertices in A. Note that the
     * returing candidate vectors are extended from the given vector A by adding
     * only one additional vertex each time this method is called.
     * @param A a vector, (a1, a2, ..., aq)
     * @return a set of candiates for q-clique
     */
    public ArrayList getCandidates(Vector A) {
        // The set of all candidate vectors for q-clique
        ArrayList candidates = new ArrayList();

        // If A is empty, let sj be a vector with each singleton node in a graph.
        if (A.isEmpty()) {
            for (int i=0; i<graph.length; i++) {
                Vector sj = new Vector(1);
                sj.add(new Integer(i));
                candidates.add(sj);
            }
        }
        else {
            Integer last = (Integer)A.lastElement();
            int q = last.intValue()+1;  // greater than the last in A

            // Permutate all candidate vectors, satisfying the property of sj
            for (int j=q; j<graph.length; j++) {
                boolean allConnected = true;
                Iterator iter = A.iterator();

                // Check if vertex 'j' is adjacent to all vertices in A
                while (iter.hasNext()) {
                    Integer v = (Integer)iter.next();
                    int i = v.intValue();
                    if (!isConnected(i,j)) {
                        // Cutoff occured in pruning - fails to meet the property of A
                        allConnected = false;
                        break;
                    }
                }
                if (allConnected) {
                    Vector sj = new Vector(A);
                    sj.add(new Integer(j));
                    candidates.add(sj);
                }
            }
        }
        return candidates;
    }


    /**
     * Show all the results of backtracking.
     */
    public void showResult() {
        //System.out.println("       Total number of vertices : "+graph.length);
        //  System.out.println("          Total number of edges : "+numEdges);
        // System.out.println("    Value of k (size of clique) : "+sizeClique);
        if (numClique == 0) {
            //   System.out.println("      Number of k-cliques found : No such clique found.");
        }
        else {
            //  System.out.println("      Number of k-cliques found : "+numClique);
            //   System.out.print  ("  Vertices in the clique first found : ");
            for (int i=0; i<firstClique.size(); i++) {
                Integer v = (Integer)firstClique.get(i);
                System.out.print(v.intValue()+"  ");
            }
        }
        System.out.println();
    }

    public int[][] getGraph() {
        return graph;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }
}
