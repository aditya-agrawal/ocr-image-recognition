package org.lnmiit;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by aditya on 27/4/16.
 */
public class JunctionGraph {
    Map<Integer,Set<Integer>> node;
    List<JunctionGraphEdge> edge;

    JunctionGraph(){

    }

    JunctionGraph(Set<Set<Integer>> cliques){
        node = new HashMap<>();
        int i = 0;
        for(Set<Integer> clique : cliques){
            node.put(i++,clique);
        }
        edge = addEdges();
    }

    public Map<Integer, Set<Integer>> getNode() {
        return node;
    }

    public void setNode(Map<Integer, Set<Integer>> node) {
        this.node = node;
    }

    public List<JunctionGraphEdge> getEdge() {
        return edge;
    }

    public void setEdge(List<JunctionGraphEdge> edge) {
        this.edge = edge;
    }

    private List<JunctionGraphEdge> addEdges() {
        List<JunctionGraphEdge> edges = new ArrayList<>();
        for(int i=0;i<node.size()-1;i++){
            for(int j=i+1;j<node.size();j++){
                Set<Integer> cluster1 = node.get(i);
                Set<Integer> cluster2 = node.get(j);
                List<Integer> intersection = findIntersection(cluster1,cluster2);
                if(intersection.size() != 0){
                    edges.add(new JunctionGraphEdge(i,j,intersection));
                }
            }
        }
        return edges;
     //   edges.stream().forEach(e -> System.out.println(e));
    }

    private List<Integer> findIntersection(final Set<Integer> cluster1, final Set<Integer> cluster2) {
        List<Integer> intersection  = new ArrayList<>();
        intersection = cluster1.stream().filter(e -> cluster2.contains(e))
                .collect(Collectors.toList());

        return intersection;
    }

    @Override
    public String toString() {
        return "JunctionGraph{" +
                "node=" + node +
                ", edge=" + edge +
                '}';
    }

    public int[][] toMatrix() {
        int[][] matrix = new int[node.size()][node.size()];

        edge.stream().forEach(e -> {matrix[e.v1][e.v2] = e.weight;
            matrix[e.v2][e.v1] = e.weight; });

        return matrix;
    }

    public List<Integer> setCommonElements(Integer v1, Integer v2) {
        Set<Integer> set1 = node.get(v1);
        Set<Integer> set2 = node.get(v2);

        List<Integer> list = findIntersection(set1,set2);

        return list;
    }
}
