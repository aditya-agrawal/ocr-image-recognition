package org.lnmiit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditya on 27/4/16.
 */
public class JunctionGraphEdge implements Comparable{
    Integer v1,v2;
    Integer weight;
    List<Integer> common;

    JunctionGraphEdge(int v1,int v2,int weight){
        this.weight = weight;
        this.v1 = v1;
        this.v2 = v2;
    }

    JunctionGraphEdge(int v1,int v2,List<Integer> common){
        this.v1 = v1;
        this.v2 = v2;
        this.common = new ArrayList<>(common);
        weight = common.size();
    }

    @Override
    public String toString() {
        return "JunctionGraphEdge{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                ", weight=" + weight +
                ", common=" + common +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        JunctionGraphEdge e1 = (JunctionGraphEdge)o;
        if(e1.weight==this.weight)
            return 0;
        return e1.weight < this.weight ? 1 : -1;
    }

}
