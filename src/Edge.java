/**
 * Class that represents an edge of a Graph
 * @author rbk
 *
 */

import java.lang.Exception;

public class Edge {
    Vertex from; // head vertex
    Vertex to; // tail vertex
    int weight;// weight of edge
  

    /**
     * Constructor for Edge
     * 
     * @param u
     *            : Vertex - Vertex from which edge starts
     * @param v
     *            : Vertex - Vertex on which edge lands
     * @param w
     *            : int - Weight of edge
     */
    Edge(Vertex u, Vertex v, int w) {
		from = u;
		to = v;
		weight = w;
    }

    /**
     * Method to find the other end of an edge, given a vertex reference
     * This method is used for undirected graphs
     * 
     * @param u
     *            : Vertex
     * @return
		  : Vertex - other end of edge
     */
    public Vertex otherEnd(Vertex u) {
	assert from == u || to == u;
	// if the vertex u is the head of the arc, then return the tail else return the head
		if (from == u) {
		    return to;
		} else {
		    return from;
		} 
    }

    /**
     * Return the string "(x,y)", where edge goes from x to y
     */
    public String toString() {
    	return "(" + from + "," + to + ")";
    }

    public String stringWithSpaces() {
    	return from + " " + to + " " + weight;
    }
}
