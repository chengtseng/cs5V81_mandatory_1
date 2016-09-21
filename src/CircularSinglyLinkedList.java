import java.util.HashMap;
import java.util.Iterator;

public class CircularSinglyLinkedList<T> implements Iterable<T>{
	

	public static void main(String [] args){
		CircularSinglyLinkedList<Integer> a = new CircularSinglyLinkedList<>();
		a.add(1);
		a.add(2);
		a.printList();
	}
	
	Entry<T> header, tail;
	int size;
	HashMap<Vertex, Entry<Vertex>>nodeMap;
	
	CircularSinglyLinkedList(){
		this.header = new Entry<>(null, null);
		nodeMap = new HashMap<>();
		tail = header;
		size = 0;
		
	}	
	
	public Iterator <T> iterator(){
		return new SLLIterator<>(header);
	}
	
	private class SLLIterator<E> implements Iterator<E>{
		Entry<E> cursor, prev;
		
		SLLIterator(Entry<E> head){
			cursor = head;
			prev = null;					
		}
		
		public boolean hasNext(){
			return cursor.next != null;
		}
		
		public E next(){
			prev = cursor;
			cursor = cursor.next;
			return cursor.element;
		}
		
		public void remove(){
			prev.next = cursor.next;
			prev = null;
		}
	}
	
	void add(T x){		
		if(header.next == null){
			header.next = new Entry<>(x,header);
			//nodeMap.put(x, header);
			tail = header.next;
		}else{
			if(!nodeMap.containsKey(x)){
				//nodeMap.put(x, tail);
			}
			tail.next = new Entry<>(x, header);
			tail = tail.next;
		}		
		size++;		
	}
	
	void printList(){
		Entry<T> x = header.next;
		while(x.element != null){
			System.out.println(x.element);
			x = x.next;
		}
		System.out.println();
	}
}