package org.lnmiit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aditya on 27/4/16.
 */
public class JunctionGraphEdge {
    Integer v1,v2;
    List<Integer> common;

    JunctionGraphEdge(int v1,int v2,List<Integer> common){
        this.v1 = v1;
        this.v2 = v2;
        this.common = new ArrayList<>(common);
    }

    @Override
    public String toString() {
        return "JunctionGraphEdge{" +
                "v1=" + v1 +
                ", v2=" + v2 +
                ", common=" + common +
                '}';
    }
}
