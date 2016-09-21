import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Hierholzer_algorithm {
	
	public static void main(String [] args){
		
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
				
				//if the edge is not yet visited, there is a new cycle
				if(!visitedEdge.containsKey(firstEdge)){
					CircularSinglyLinkedList<Vertex> newCycle = new CircularSinglyLinkedList<>();
					
					newCycle.add(firstEdge.from);
					newCycle.add(firstEdge.to);
					visitedEdge.put(firstEdge, firstEdge);
					
					//add the just visit node to the queue
					if(!visitedVertices.contains(firstEdge.to)){
						visitedVertices.add(firstEdge.to);
					}
					Vertex nextVertex = firstEdge.to;
					
					for(int j = 0; j < nextVertex.adj.size(); j++){						
						Edge nextNodeEdge = nextVertex.adj.get(j);
						
						if(!visitedEdge.containsKey(nextNodeEdge)){
							//route complete
							if(nextNodeEdge.to == start){
								tours.add(newCycle);
								break;
							}
							else{
								if(!visitedVertices.contains(nextNodeEdge.to)){
									visitedVertices.add(nextNodeEdge.to);
								}
								newCycle.add(nextNodeEdge.to);
								visitedEdge.put(nextNodeEdge, nextNodeEdge);								
								nextVertex = nextNodeEdge.to;
								j=0;
							}
						}
					}				
				}				
			}
		}
		
		return tours;
	}
}
