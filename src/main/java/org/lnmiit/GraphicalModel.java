package org.lnmiit;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;


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
			doTriangulation(small_images, graph, i, t, vertices);
            int[][] matrix = GraphMatrix.getGraphmatrix(graph[i]);
            Set<Set<Integer>> clusters = getClusterGraph(matrix);
            System.out.println(clusters);
		}

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

        result1.addAll(result2);

        return result1;
    }

    private static void doTriangulation(int[][] small_images, Graph[] graph, int i, int t, Vertex[] vertices) {
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
                            if((k-j)==3)
                            {
                                graph[i].addEdge(vertices[j], vertices[k-1]);
                            }

                            if((k-j)==4)
                            {
                                graph[i].addEdge(vertices[j], vertices[k-2]);
                                graph[i].addEdge(vertices[k], vertices[k-2]);
                            }

                            if((k-j)==5)
                            {
                                graph[i].addEdge(vertices[j], vertices[j+2]);
                                graph[i].addEdge(vertices[k], vertices[k-2]);
                                graph[i].addEdge(vertices[j], vertices[k-2]);
                            }



                        }
                    }
                    else
                    {
                        if(small_images[2*i][j]==small_images[2*i+1][k-strlengths[2*i]])
                        {
                            int p;
                            int q;
                            for(p=j+1;p<strlengths[2*i];p++)
                            {
                                for(q=k+1;q<t;q++)
                                {
                                    if(small_images[2*i][p]==small_images[2*i+1][q-strlengths[2*i]])
                                            {
                                                if((p-j)+(q-k)==2)
                                                {
                                                    graph[i].addEdge(vertices[j], vertices[q]);
                                                }
                                                if((p-j)+(q-k)==3)
                                                {
                                                    if((p-j)>(q-k))
                                                    {
                                                        graph[i].addEdge(vertices[p-1], vertices[q]);
                                                        graph[i].addEdge(vertices[j], vertices[q]);
                                                    }
                                                    else
                                                    {
                                                        graph[i].addEdge(vertices[p], vertices[q-1]);
                                                        graph[i].addEdge(vertices[k], vertices[p]);
                                                    }
                                                }

                                                if((p-j)+(q-k)==4)
                                                {
                                                    if((p-j)==(q-k))
                                                    {
                                                        graph[i].addEdge(vertices[j], vertices[k+1]);
                                                        graph[i].addEdge(vertices[j+1], vertices[k+1]);
                                                        graph[i].addEdge(vertices[j+2], vertices[k+1]);
                                                    }
                                                    if((p-j)<(q-k))
                                                    {
                                                        graph[i].addEdge(vertices[j+1], vertices[k]);
                                                        graph[i].addEdge(vertices[j+1], vertices[k+2]);
                                                        graph[i].addEdge(vertices[j+1], vertices[k+1]);
                                                    }
                                                    if((p-j)>(q-k))
                                                    {
                                                        graph[i].addEdge(vertices[k+1], vertices[j]);
                                                        graph[i].addEdge(vertices[k+1], vertices[j+1]);
                                                        graph[i].addEdge(vertices[k+1], vertices[j+2]);
                                                    }

                                                }
                                                if((p-j)+(q-k)==5)
                                                {
                                                    if((p-j)==1)
                                                    {
                                                        graph[i].addEdge(vertices[j+1], vertices[k]);
                                                        graph[i].addEdge(vertices[j+1], vertices[k+1]);
                                                        graph[i].addEdge(vertices[j+1], vertices[k+2]);
                                                        graph[i].addEdge(vertices[j+1], vertices[k+3]);
                                                    }
                                                    if((p-j)==4)
                                                    {
                                                        graph[i].addEdge(vertices[j], vertices[k+1]);
                                                        graph[i].addEdge(vertices[j+2], vertices[k+1]);
                                                        graph[i].addEdge(vertices[j+3], vertices[k+1]);
                                                        graph[i].addEdge(vertices[j+4], vertices[k+1]);
                                                    }
                                                    if((p-j)==3)
                                                    {
                                                        graph[i].addEdge(vertices[j], vertices[k+1]);
                                                        graph[i].addEdge(vertices[j+1], vertices[k+1]);
                                                        graph[i].addEdge(vertices[j+2], vertices[k+1]);
                                                        graph[i].addEdge(vertices[j+3], vertices[k+1]);
                                                    }
                                                    if((p-j)==2)
                                                    {
                                                        graph[i].addEdge(vertices[j+1], vertices[k]);
                                                        graph[i].addEdge(vertices[j+1], vertices[k+1]);
                                                        graph[i].addEdge(vertices[j+1], vertices[k+2]);
                                                        graph[i].addEdge(vertices[j+1], vertices[k+3]);
                                                    }

                                                }

                                            }
                                }
                            }

                        }
                    }
                }
                else
                {
                    if(k>strlengths[2*i])
                    {
                        if(small_images[2*i+1][j-strlengths[2*i]]==small_images[2*i][k-strlengths[2*i]])
                        {
                            if((k-j)==3)
                            {
                                graph[i].addEdge(vertices[j], vertices[k-1]);
                            }

                            if((k-j)==4)
                            {
                                graph[i].addEdge(vertices[j], vertices[k-2]);
                                graph[i].addEdge(vertices[k], vertices[k-2]);
                            }

                            if((k-j)==5)
                            {
                                graph[i].addEdge(vertices[j], vertices[j+2]);
                                graph[i].addEdge(vertices[k], vertices[k-2]);
                                graph[i].addEdge(vertices[j], vertices[k-2]);
                            }
                        }
                    }
                }

            }

        }
	}
}

		
