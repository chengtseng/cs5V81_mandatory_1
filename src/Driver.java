/** Sample driver program using the graph class
 *  @author rbk
 */

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
	   	
		Scanner in;
	    if (args.length > 0) {
	    	File inputFile = new File(args[0]);
	        in = new Scanner(inputFile);
	    } else {
	        in = new Scanner(System.in);
	    }
	    		
	    Graph g = Graph.readGraph(in);
	   // System.out.print(g.vertices.get(80).adj);
	    
//	    for(int i = 1; i < g.vertices.size(); i++){
//	    	System.out.println(g.vertices.get(i).adj);
//	    }
	    
	    List<CircularSinglyLinkedList<Vertex>>  a = Graph.breakGraphIntoTours(g);
	    
//	    System.out.println(a.size());
//	    for(Entry<Vertex> v = a.get(1).header.next; v.element!=null;){
//	    	System.out.println(v.element);
//	    	v=v.next;
//	    }
	    	
	    
	    
//	    for(CircularSinglyLinkedList<Vertex> c : a){
//	    	c.printList();
//	    	System.out.println();
//	    }
	 
	    Graph.stitchTours(a);	    
	
		System.out.println("Input Graph:");
		
//		for(Vertex u: g) {
//		    System.out.print(u + ": ");
//		    for(Edge e: u.adj) {
//			Vertex v = e.otherEnd(u);
//			System.out.print(e + " ");
//		    }
//		    System.out.println();
//		}
		if(g.isBipartite()) {
		    System.out.println("Bipartite graph");
		} else {
		    System.out.println("Not a bipartite graph");
		}
    }
}

/* Sample runs:
$ java Driver < in.no
Input Graph:
1: (1,2) (1,3) 
2: (1,2) (2,3) 
3: (2,3) (1,3) 
Not a bipartite graph

$ java Driver < in.yes
Input Graph:
1: (1,2) (1,4) 
2: (1,2) (2,3) 
3: (2,3) (3,4) 
4: (3,4) (1,4) 
Bipartite graph
*/
