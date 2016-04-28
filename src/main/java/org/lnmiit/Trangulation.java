package org.lnmiit;

import static org.lnmiit.GraphicalModel.strlengths;

/**
 * Created by aditya on 27/4/16.
 */
public class Trangulation {
    public static void doTriangulation(int[][] small_images, Graph[] graph, int i, int t, Vertex[] vertices) {
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
