package org.lnmiit;

import java.text.MessageFormat;
import java.util.ArrayList;

public class Vertex {

    float OCRPotential;
    float TransPotential;
    int SkipPotential;
    private final ArrayList<Edge> neighborhood;
    private final String label;

    /**
     * @param label The unique label associated with this Vertex
     */
    Vertex(String label) {
        this.label = label;
        this.neighborhood = new ArrayList<Edge>();
    }


    /**
     * This method adds an Edge to the incidence neighborhood of this graph iff
     * the edge is not already present.
     *
     * @param edge The edge to add
     */
    void addNeighbor(Edge edge) {
        if (this.neighborhood.contains(edge)) {
            return;
        }

        this.neighborhood.add(edge);
    }


    /**
     * @param other The edge for which to search
     * @return true iff other is contained in this.neighborhood
     */
    boolean containsNeighbor(Edge other) {
        return this.neighborhood.contains(other);
    }

    /**
     * @param index The index of the Edge to retrieve
     * @return Edge The Edge at the specified index in this.neighborhood
     */
    Edge getNeighbor(int index) {
        return this.neighborhood.get(index);
    }


    /**
     * @param e The Edge to remove from this.neighborhood
     */
    void removeNeighbor(Edge e) {
        this.neighborhood.remove(e);
    }


    /**
     * @return int The number of neighbors of this Vertex
     */
    int getNeighborCount() {
        return this.neighborhood.size();
    }


    /**
     * @return String The label of this Vertex
     */
    String getLabel() {
        return this.label;
    }


    /**
     * @return String A String representation of this Vertex
     */
    public String toString() {
        return MessageFormat.format("Vertex {0}", label);
    }

    /**
     * @return The hash code of this Vertex's label
     */
    public int hashCode() {
        return this.label.hashCode();
    }

    /**
     * @param other The object to compare
     * @return true iff other instanceof Vertex and the two Vertex objects have the same label
     */
    public boolean equals(Object other) {
        if (!(other instanceof Vertex)) {
            return false;
        }

        Vertex v = (Vertex) other;
        return this.label.equals(v.label);
    }


}
