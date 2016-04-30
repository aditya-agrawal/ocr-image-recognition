package org.lnmiit;

import java.io.IOException;
import java.util.*;


public class GraphicalModel {

	
	static int numlines = 65; //change this for the new file
	static int[] strlengths= new int[numlines*2];

	public static void doGraphTransformation() throws IOException {
		//read input and initialise
		int[][] small_images = Utilities.readImages("src/main/resources/OCRdataset/data/data-loopsWS.dat");
		int[][] small_chars = Utilities.readWords("src/main/resources/OCRdataset/data/truth-loopsWS.dat");
		float[][] ocrpotentials = Utilities.readOCRPotentials("src/main/resources/OCRdataset/potentials/ocr.dat");
		float[][] transpotentials = Utilities.readTransitionPotentials("src/main/resources/OCRdataset/potentials/trans.dat");

		//Check if directory exist
		Utilities.checkDir("results", "Checking directory and write permissions");


		Graph[] graph= new Graph[numlines];
		ArrayList<Set<Set<Integer>>> clusters = new ArrayList<>();
		ArrayList<JunctionGraph> junctionGraph = new ArrayList<>();
		//Creating Vertices
		for(int i=0;i<numlines;i++)
		{
			graph[i]= new Graph();
			int t= strlengths[2*i] + strlengths[2*i+1];
			Vertex[] vertices = new Vertex[t];
			for(int j=0;j<t;j++)
			{
				 vertices[j] = new Vertex("" + j);
				 if(j<strlengths[2*i])
				 vertices[j].OCRPotential=ocrpotentials[small_images[2*i][j]][small_chars[2*i][j]];
				 else
			     vertices[j].OCRPotential=ocrpotentials[small_images[2*i+1][j-strlengths[2*i]]][small_chars[2*i+1][j-strlengths[2*i]]];
				 if(j!=0 && j!=strlengths[2*i])
				 {
					     if(j<strlengths[2*i])
						 vertices[j].TransPotential=transpotentials[small_chars[2*i][j-1]][small_chars[2*i][j]];
						 else
					     vertices[j].TransPotential=transpotentials[small_chars[2*i+1][j-strlengths[2*i]-1]][small_chars[2*i+1][j-strlengths[2*i]]];
				 }

		         graph[i].addVertex(vertices[j], true);

			}
			//Trans-Potential-Edges
			for(int j=1;j<t;j++)
			{
				if(j!=strlengths[2*i])
				graph[i].addEdge(vertices[j], vertices[j-1]);

			}
			//Skip-Pair-&-Skip-Factor-Edges-&-Potentials
			for(int j=0;j<t;j++)
			{
				for(int k=j+1;k<t;k++)
				{
					if(j<strlengths[2*i])
					{
						if(k<strlengths[2*i])
						{
							if(small_images[2*i][j]==small_images[2*i][k])
							{
								if(small_chars[2*i][j]==small_chars[2*i][k])
								{
									vertices[j].SkipPotential=5;
									vertices[k].SkipPotential=5;
									graph[i].addEdge(vertices[j], vertices[k]);
								}
								else
								{
									vertices[j].SkipPotential=1;
									vertices[k].SkipPotential=1;
									graph[i].addEdge(vertices[j], vertices[k]);
								}
							}
						}
						else
						{
							if(small_images[2*i][j]==small_images[2*i+1][k-strlengths[2*i]])
							{
								if(small_chars[2*i][j]==small_chars[2*i+1][k-strlengths[2*i]])
								{
									vertices[j].SkipPotential=5;
									vertices[k].SkipPotential=5;
									graph[i].addEdge(vertices[j], vertices[k]);
								}
								else
								{
									vertices[j].SkipPotential=1;
									vertices[k].SkipPotential=1;
									graph[i].addEdge(vertices[j], vertices[k]);
								}
							}
						}
					}
					else
					{
						if(k<strlengths[2*i])
						{
							if(small_images[2*i+1][j-strlengths[2*i]]==small_images[2*i][k])
							{
								if(small_chars[2*i+1][j-strlengths[2*i]]==small_chars[2*i][k])
								{
									vertices[j].SkipPotential=5;
									vertices[k].SkipPotential=5;
									graph[i].addEdge(vertices[j], vertices[k]);
								}
								else
								{
									vertices[j].SkipPotential=1;
									vertices[k].SkipPotential=1;
									graph[i].addEdge(vertices[j], vertices[k]);
								}
							}
						}
						else
						{
							if(small_images[2*i+1][j-strlengths[2*i]]==small_images[2*i+1][k-strlengths[2*i]])
							{
								if(small_chars[2*i+1][j-strlengths[2*i]]==small_chars[2*i+1][k-strlengths[2*i]])
								{
									vertices[j].SkipPotential=5;
									vertices[k].SkipPotential=5;
									graph[i].addEdge(vertices[j], vertices[k]);
								}
								else
								{
									vertices[j].SkipPotential=1;
									vertices[k].SkipPotential=1;
									graph[i].addEdge(vertices[j], vertices[k]);
								}
							}
						}
					}


				}
			}

			//TRIANGULATION

			Trangulation.doTriangulation(small_images, graph, i, t, vertices);
            int[][] matrix = GraphMatrix.getGraphmatrix(graph[i]);
            clusters.add(getClusterGraph(matrix));
			junctionGraph.add(new JunctionGraph(clusters.get(i)));
		}


		System.out.println(junctionGraph.get(0));

		int[][] matrix = junctionGraph.get(0).toMatrix();

		MST mst = new MST(junctionGraph.get(0).node.size());
		ArrayList<JunctionGraphEdge> edges = mst.primMST(matrix);
		System.out.println(edges);

    }

    private static Set<Set<Integer>> getClusterGraph(int[][] matrix) {
        Clique clique = new Clique(10+"",2);
        clique.setGraph(matrix);
        Set<Set<Integer>> result1 = new HashSet<>();
        clique.doCliqueBT(new Vector(),0,result1);

        clique = new Clique(10+"",3);
        clique.setGraph(matrix);
        Set<Set<Integer>> result2 = new HashSet<>();
        clique.doCliqueBT(new Vector(),0,result2);

        result1 = removeSubsets(result2,result1);
        result1.addAll(result2);

        return result1;
    }

    private static Set<Set<Integer>> removeSubsets(Set<Set<Integer>> result2, Set<Set<Integer>> result1) {

        for(Set<Integer> clusterSize3 : result2){
            List<Integer> clusterList = new ArrayList<>(clusterSize3);
            clusterList.remove(0);
            Set<Integer> subcluster = new HashSet(clusterList);
            if(result1.contains(subcluster))
                result1.remove(subcluster);

            clusterList = new ArrayList<>(clusterSize3);
            clusterList.remove(1);
            subcluster = new HashSet(clusterList);
            if(result1.contains(subcluster))
                result1.remove(subcluster);

            clusterList = new ArrayList<>(clusterSize3);
            clusterList.remove(2);
            subcluster = new HashSet(clusterList);
            if(result1.contains(subcluster))
                result1.remove(subcluster);
        }

        return result1;
    }
}

		
