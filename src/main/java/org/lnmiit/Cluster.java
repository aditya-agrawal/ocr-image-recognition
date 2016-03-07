package org.lnmiit;

import java.util.HashSet;

/**
 * Created by aditya on 7/3/16.
 */
public class Cluster {
    private Vertex common;
    private HashSet<Vertex> vertices;

    private Cluster(Builder builder) {
        setCommon(builder.common);
        setVertices(builder.vertices);
    }

    public Vertex getCommon() {
        return common;
    }

    public void setCommon(Vertex common) {
        this.common = common;
    }

    public HashSet<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(HashSet<Vertex> vertices) {
        this.vertices = vertices;
    }

    public static final class Builder {
        private Vertex common;
        private HashSet<Vertex> vertices;

        public Builder() {
        }

        public Builder common(Vertex val) {
            common = val;
            return this;
        }

        public Builder vertices(HashSet<Vertex> val) {
            vertices = val;
            return this;
        }

        public Cluster build() {
            return new Cluster(this);
        }
    }
}
