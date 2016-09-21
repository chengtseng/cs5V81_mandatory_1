/**
 * Class to represent a graph
 *  @author rbk
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Graph implements Iterable<Vertex> {
    List<Vertex> vertices; // vertices of graph
    int size; // number of verices in the graph
    boolean directed;  // true if graph is directed, false otherwise
    

    /**
     * Constructor for Graph
     * 
     * @param size : int - number of vertices
     * 
     */
    Graph(int size) {
		this.size = size;
		this.vertices = new ArrayList<>(size + 1);
		this.vertices.add(0, null);  	// Vertex at index 0 is not used
		this.directed = false;  // default is undirected graph
								// create an array of Vertex objects
		for (int i = 1; i <= size; i++)
		    this.vertices.add(i, new Vertex(i));
    }

    /**
     * Find vertex no. n
     * @param n : int
     *           
     */
    Vertex getVertex(int n) {
    	return this.vertices.get(n);
    }
    
    /**
     * Method to add an edge to the graph
     * 
     * @param a
     *            : int - one end of edge
     * @param b
     *            : int - other end of edge
     * @param weight
     *            : int - the weight of the edge
     */
    void addEdge(Vertex from, Vertex to, int weight) {
    	Edge e = new Edge(from, to, weight);

		if(this.directed) {
		    from.adj.add(e);
		    to.revAdj.add(e);
		} else {
		    from.adj.add(e);
		    to.adj.add(e);
		}
    }

    /**
     * Method to create iterator for vertices of graph
     */
    public Iterator<Vertex> iterator() {
		Iterator<Vertex> it = this.vertices.iterator();
		it.next();  // Index 0 is not used.  Skip it.
		return it;
    }

    // Run BFS from a given source node
    // Precondition: nodes have already been marked unseen
    public void bfs(Vertex src) {
		src.seen = true;
		src.distance = 0;
		Queue<Vertex> q = new LinkedList<>();
		q.add(src);
		while(!q.isEmpty()) {
		    Vertex u = q.remove();
		    for(Edge e: u.adj) {
				Vertex v = e.otherEnd(u);
				if(!v.seen) {
				    v.seen = true;
				    v.distance = u.distance + 1;
				    q.add(v);
				}
		    }
		}
    }

    // Check if graph is bipartite, using BFS
    public boolean isBipartite() {
		for(Vertex u: this) {
		    u.seen = false;
		}
		for(Vertex u: this) {
		    if(!u.seen) {
			bfs(u);
		    }
		}
		for(Vertex u:this) {
		    for(Edge e: u.adj) {
		    	Vertex v = e.otherEnd(u);
		    	if(u.distance == v.distance) {
		    		return false;
		    	}
		    }
		}
		return true;
    }


    // read a directed graph using the Scanner interface
    public static Graph readDirectedGraph(Scanner in) {
    	return readGraph(in, true);
    }
    
    // read an undirected graph using the Scanner interface
    public static Graph readGraph(Scanner in) {
    	return readGraph(in, false);
    }
    
    public static Graph readGraph(Scanner in, boolean directed) {
		// read the graph related parameters
		int n = in.nextInt(); // number of vertices in the graph
		int m = in.nextInt(); // number of edges in the graph
		
		// create a graph instance
		Graph g = new Graph(n);
		g.directed = directed;
		for (int i = 0; i < m; i++) {
		    int u = in.nextInt();
		    int v = in.nextInt();
		    int w = in.nextInt();
		   // System.out.println(u+" "+v+" "+w);
		    g.addEdge(g.getVertex(u), g.getVertex(v), w);
		}
		in.close();
		System.out.print("DDDDDDDD");
		return g;
    }
    
    //tours-list of routes found
  	//visitedVertices-store vertices by it's reaching order
  	//visitEdge-store visited edge
  	//start from a vertex, if there is an unvisited edge then we have a new cycle
  	//add the vertex and the other end of the first edge to the new route. Mark the 
  	//edge and add the next vertex if it is new discovered.
  	//Go from next vertex and traverse its adj edge list. If the there is edge not 
  	//visited, examine the other end of the edge, if it is the start node, route found. 
  	//If it is not, add edge and add node if needed, set the next examine node to the
  	//other node and traverse the new next node's edge list from the beginning.
  	public static List<CircularSinglyLinkedList<Vertex>> breakGraphIntoTours(Graph g){
  		List<CircularSinglyLinkedList<Vertex>> tours = new LinkedList<>();
  		Queue <Vertex>visitedVertices = new LinkedList<>();
  		HashMap <Edge,Edge> visitedEdge = new HashMap<>();
  			
  		visitedVertices.add(g.getVertex(1));
  				
  		while(!visitedVertices.isEmpty()){
  			
  			Vertex start = visitedVertices.poll();
  			
  			//traverse through all the edges in adj edge list
  			for(int i = 0; i <start.adj.size(); i++){
  				Edge firstEdge = start.adj.get(i);
  				Vertex first_edge_from = start;
  				Vertex first_edge_to = firstEdge.otherEnd(start);
  				
  				//if the edge is not yet visited, there is a new cycle
  				if(!visitedEdge.containsKey(firstEdge)){
  					CircularSinglyLinkedList<Vertex> newTour = new CircularSinglyLinkedList<>();
  					
  					newTour.nodeMap.put(first_edge_from, newTour.header);
  					newTour.add(first_edge_from);					
  					newTour.nodeMap.put(first_edge_to, newTour.tail);
  					newTour.add(first_edge_to);
  					visitedEdge.put(firstEdge, firstEdge);
  					
  					//add the just visit node to the queue
  					if(!visitedVertices.contains(first_edge_to)){
  						visitedVertices.add(first_edge_to);
  					}
  					Vertex nextVertex = first_edge_to;
  					Edge nextNodeEdge;
  					
  					while(nextVertex!=start){
  						//System.out.println("DDDDDDDDDDDDDDDDDDDDDD");
  						for(int j = 0; j < nextVertex.adj.size(); j++){						
  	  						nextNodeEdge = nextVertex.adj.get(j);
  	  						//Vertex next_edge_from = nextVertex;
  	  		  				Vertex next_edge_to = nextNodeEdge.otherEnd(nextVertex);  	  		  				
  	  		  				
  	  		  				if(!visitedEdge.containsKey(nextNodeEdge)){  	  		  				
  	  							if(!visitedVertices.contains(next_edge_to)){
  										visitedVertices.add(next_edge_to);
  								}
  	  							
  	  							if(!newTour.nodeMap.containsKey(next_edge_to)){
  	  		  					newTour.nodeMap.put(next_edge_to, newTour.tail);
  	  							}
  	  							
  	  							if(next_edge_to != start)
  	  								newTour.add(next_edge_to);
  	  							
  								visitedEdge.put(nextNodeEdge, nextNodeEdge);								
  								nextVertex = next_edge_to;
  								break;  							
  	  						}
  	  					}	
  					}
  					tours.add(newTour);  					
  				}				
  			}
  		}
  		
  		return tours;
  	}
  	
  	//all the CircularSinglyLinkedList contains a hash table to have its vertex stored
  	//Take the first list out and merge the remaining list into it.
  	//find the prevNode of the start of every route in the last route's hash table.
  	public static CircularSinglyLinkedList<Vertex> stitchTours(List<CircularSinglyLinkedList<Vertex>> listOfTours){
  		  		
  		CircularSinglyLinkedList<Vertex> currentList;
  		for(int i = 1; i < listOfTours.size(); i++){
  			currentList = listOfTours.get(i);
  			Entry<Vertex> breakPoint = listOfTours.get(i-1).nodeMap.get(currentList.header.next.element);
  			Entry<Vertex> stichPoint = breakPoint.next;
  			breakPoint.next = currentList.header.next;
  			currentList.tail.next = stichPoint;  			
  		}
  		
  		return listOfTours.get(0);
  	}

}
