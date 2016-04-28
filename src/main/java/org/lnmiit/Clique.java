package org.lnmiit;

import java.io.*;
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

    /**
     * Top-level function which creates an instance of 'CliqueBT' class and
     * invokes its methods to find k-cliques in graphs.
     * strings of graph representation - total number of vertices
     *             followed by pairs of vertices which indicate edges.
     * @throws IOException
     */
//    public static void main(String[] args) throws IOException {
//     /* System.out.println(" Enter a positive integer 'n' followed by " +
//      		"some number of pairs of integers\n in the range 0 to n-1." +
//      		" 'N' represents the number of vertices in the graph,\n and each " +
//      		"pair u v represents an edge between vertices u and v."+
//      		"\n\n Input Note : The first line MUST contain ONLY the number of vertices."+
//      		" Then,\n\t      a pair of vertices seperated by space can follow from "+
//      		"the\n\t      second line. And, ONLY ONE of any duplicate edges such"+
//      		" as\n\t      (u,v) and (v,u) should be entered.");
//      System.out.println(" Input Example : A graph of 5 vertices and e(0,1), "+
//            "e(0,3), e(1,2),...,e(3,4)\n\t\t should have an input as shown below.");
//      System.out.println("\t\t 5");     System.out.println("\t\t 0 1");
//      System.out.println("\t\t 0 3");   System.out.println("\t\t 1 2");
//      System.out.println("\t\t 1 4");   System.out.println("\t\t 2 3");
//      System.out.println("\t\t 2 4");   System.out.println("\t\t 3 4");
//      System.out.print("\nEnter a string representaion of a graph.\n" + "=> ");
//*/
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        //String firstLine = br.readLine();  // 1st line contains only # of vertices
//        String firstLine = args[0];
//        StringTokenizer token = new StringTokenizer(firstLine);
//        String numNodes = token.nextToken();
//        // Create an instance with given number of nodes in a graph
//
////firstLine = br.readLine();
//        firstLine = args[1];
//        token = new StringTokenizer(firstLine);
//        String numNodes1 = token.nextToken();
//        totalnodes=Integer.parseInt(numNodes1);
//        System.out.println("total here "+totalnodes+" ");
//        int size=Integer.parseInt(numNodes);
//        System.out.println(numNodes1+"--"+size);
//        Clique cliqueBT = new Clique(numNodes1,size);
//
//
//        try{
//            //  genSynNtwrk();
//            br = new BufferedReader(new FileReader("netscience.txt"));
//            String str=new String();
//            String str2=new String();
//
//            int term=0;
//            String[] terms=new String[3];
//            //keep reading till the end
//            while (!(str=br.readLine()).contentEquals("END OF INPUT")) {
//                term=0;
//                StringTokenizer Tok = new StringTokenizer(str);
//                while(Tok.hasMoreTokens()){
//                    str2=Tok.nextToken();
//                    terms[term]=str2;
//                    term++;
//                }
//                // connect(Integer.parseInt(terms[0]),Integer.parseInt(terms[1]));
//                cliqueBT.addEdge(terms[0], terms[1]);
//
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//
//
//
//
//        }
//
//
//     /* cliqueBT.addEdge("0","1");  // add edge to a graph
//        cliqueBT.addEdge("0","3");  // add edge to a graph
//        cliqueBT.addEdge("1","2");  // add edge to a graph
//        cliqueBT.addEdge("1","4");  // add edge to a graph
//        cliqueBT.addEdge("2","3");  // add edge to a graph
//        cliqueBT.addEdge("2","4");  // add edge to a graph
//        cliqueBT.addEdge("3","4");  // add edge to a graph
//       cliqueBT.addEdge("3","1");  // add edge to a graph
//        cliqueBT.addEdge("1","5");  // add edge to a graph
//        cliqueBT.addEdge("2", "5");
//      //  cliqueBT.addEdge("2", "6");
//     cliqueBT.addEdge("2", "6");
//     cliqueBT.addEdge("6", "5");
//    // cliqueBT.addEdge("2", "8");
//    // cliqueBT.addEdge("8", "1");
//    // cliqueBT.addEdge("8", "5");
//*/
//        // Read all edges in the form of pairs of vertices
//
//
//        // Stamp the starting time of the algorithm.
//        long time1 = System.currentTimeMillis();
//
//        // Perform backtracking for clique with initially empty solution
//        cliqueBT.doCliqueBT(new Vector(), 0 ,null);
//
//        // Stamp the ending time of the algorithm.
//        long time2 = System.currentTimeMillis();
//
//        // Determine running time of DFS
//        long elapse = time2 - time1;
//
//        System.out.println("\n  Running Time of the algorithm : "+(long)elapse+" ms.");
//        //   cliqueBT.showResult();  // show results of the backtracking for clique
//    }

    public int[][] getGraph() {
        return graph;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }
}
