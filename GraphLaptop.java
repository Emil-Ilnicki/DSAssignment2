import java.util.*;
import java.io.*;


public class Graph {

	private int nodes;
	private String action;
	private boolean[] visitedNodes;
	private LinkedList<Integer> adjList[];	
	
	public Graph(int nodes, String action) {
		this.nodes = nodes;
		this.action = action;
		this.visitedNodes = new boolean[nodes];
		this.adjList = new LinkedList[nodes];
		
		for (int i = 0; i < nodes; i++) {
			adjList[i] = new LinkedList<Integer>();
		}
	}
	
	public void addEdge(int node1, int node2) {
		this.adjList[node1-1].add(node2);
	}
	
	public void printEdge() {
		System.out.println();
		for (int i = 0; i < this.adjList.length; i++) {
			System.out.print((i+1) + ": ");
			for (int j = 0; j < adjList[i].size(); j++) {
				System.out.print(adjList[i].get(j) + " ");
			}
			System.out.println();
		}
		
	}
	
	public void isGraphEmpty() {
		for(int i = 0; i < this.nodes; i++) {
			if(!this.adjList[i].isEmpty()) {
				if(this.action.toUpperCase().equals("BFS")) {
					BFS(i+1);
				}else if (this.action.toUpperCase().equals("DFS")) {
					DFS(i+1);
				}else {
					System.out.println("No action");
				}
			}
		}
	}
	
	public void BFS(int node) {
		int focusNode = node-1;
			
		Queue<Integer> q = new LinkedList<Integer>();

		q.add(node);
		this.visitedNodes[focusNode] = true;
			
			while(!q.isEmpty()) {
				
				focusNode = q.poll();
				System.out.print (focusNode + " ");
				focusNode--;
				
				while(!adjList[focusNode].isEmpty()) {
					if (!this.visitedNodes[adjList[focusNode].peek()-1]) {
						
						this.visitedNodes[adjList[focusNode].peek()-1] = true;
						q.add(adjList[focusNode].remove());
						
					}else {
						adjList[focusNode].remove();
					}
				}				
			}
			
			isGraphEmpty();
	}
	
	public void DFS(int node) {
		int focusNode = node-1;
		
		Stack<Integer> s = new Stack<Integer>();
			
		if (!this.visitedNodes[focusNode]) {
			s.push(node);
			System.out.print(node + " ");
		}
			
 		this.visitedNodes[focusNode] = true;
 		int mainFocusNode = focusNode;
 			
 		while(!adjList[mainFocusNode].isEmpty()) {
 				focusNode = mainFocusNode;
 				while(!adjList[focusNode].isEmpty()) {
 				
// 					printEdge();
 				
//					System.out.println("Focus node: " + (focusNode+1));
// 					System.out.println("Focus nodes next value: " + adjList[focusNode].peek());
 				
 					if(!this.visitedNodes[adjList[focusNode].peek()-1]) {
 						
 						focusNode = adjList[focusNode].remove();
 						s.push(focusNode);
 						System.out.print(s.peek() + " ");
 						focusNode = s.peek() - 1;
 						this.visitedNodes[focusNode] = true;
 						
 					}else {
 						
 						s.pop();
 						adjList[focusNode].remove();
 						if(!s.isEmpty()) {
 							focusNode = s.peek()-1;
 						}
// 						System.out.println("Focus Node in else: " + (focusNode+1));
 					}
 				}
 			}	
 			
 			isGraphEmpty();
	}
	
	public void PATH (int startNode, int destinationNode) {
		int focusNode = startNode-1;
		Boolean found = false;
		
		Stack<Integer> s = new Stack<Integer>();
			
			
 		this.visitedNodes[focusNode] = true;
 		s.push(startNode);
 		int mainFocusNode = s.peek()-1;
 		Boolean firstPass = true;
 	
 		while(s.size() >= 1) {
 			if(found) {
 				break;
 			} else {
 				if(s.size()>1) {
 				s.pop();
 				mainFocusNode = s.peek()-1;
 				}else if(s.size() == 1 && !firstPass) {
 					mainFocusNode = s.pop()-1;
 				}
 			}
 			focusNode = mainFocusNode;
 			firstPass = false;
 				while(!adjList[focusNode].isEmpty()) {
 
// 				    	printEdge();
 				
// 				    	System.out.println("Focus node: " + (focusNode+1));
//			    		System.out.println("Focus nodes next value: " + adjList[focusNode].peek());
 				
 				    	if(!this.visitedNodes[adjList[focusNode].peek()-1]) {
 						
 				    		if (adjList[focusNode].peek() == destinationNode) {
 				    			s.push(destinationNode);
 				    			found = true;
 				    			break;
 				    		}
 						
 				    		focusNode = adjList[focusNode].remove();
 				    		s.push(focusNode);
 				    		focusNode = s.peek() - 1;
 				    		this.visitedNodes[focusNode] = true;
 						
 				    	}else {
 					
 				    		s.pop();
 				    		adjList[focusNode].remove();
 				    		if(!s.isEmpty()) {
 				    			focusNode = s.peek()-1;
 				    		}
 				    		System.out.println("Focus Node in else: " + (focusNode+1));
 				    	}	
 				}
 		}
 		
 		if (found) {
 			Object[] printPath = s.toArray();

 	 		
 	 		for (int i = 0; i < printPath.length; i++) {
 	 			if (i+1 == printPath.length) {
 	 				System.out.print(printPath[i]);
 	 			}
 	 			else {
 	 				System.out.print(printPath[i] + " => ");
 	 			}
 	 		}	
 		}
 		else {
 			System.out.println("A path was not found between " + startNode + " and " + destinationNode);

 		}

 			
	}
	
	
	public static void main (String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("How many nodes are there");	
		int nodes = input.nextInt();
		
		System.out.println("What action would you like to do");
		String action = input.next();
		
		System.out.println("What node do you want to start at?");
		int start = input.nextInt();
		
		Graph a = new Graph(nodes, action);
		
		a.addEdge(1,2);
		a.addEdge(2,3);
		a.addEdge(2,5);
		a.addEdge(3,4);
		a.addEdge(4,7);
		a.addEdge(4,8);
		a.addEdge(5,2);
		a.addEdge(5,6);
		a.addEdge(8,9);
		
//		a.addEdge(1, 3);
//		a.addEdge(1, 4);
//		a.addEdge(2, 4);
//		a.addEdge(3, 4);
//		a.addEdge(4, 1);
		
//		a.addEdge(1, 2);
//		a.addEdge(1, 3);
//		a.addEdge(1, 4);
//		a.addEdge(1, 5);
//		a.addEdge(1, 6);

		//a.printEdge();

		if(action.toUpperCase().equals("BFS")) {
			a.BFS(start);
		}
		else if(action.toUpperCase().equals("DFS")) {
			a.DFS(start);
		}else if(action.toUpperCase().equals("PATH")) {
			System.out.println("What is your destination node?");
			int dNode = input.nextInt();
			a.PATH(start, dNode);
		}
	}
}
