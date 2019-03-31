import java.util.*;
import java.io.*;


public class Graph {

    private int nodes;
    private String action;
    private boolean[] visitedNodes;
    private LinkedList<Integer> adjList[];
    File inputPath;

    public Graph(int nodes, String action, File path) {
        this.nodes = nodes;
        this.action = action;
        this.inputPath = path;
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
        System.out.println("");
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
        int mainFocusNode = focusNode;

        while(!adjList[mainFocusNode].isEmpty()) {
            if(found) {
                break;
            }
            s.clear();
            s.push(startNode);
            focusNode = mainFocusNode;
            while(!adjList[focusNode].isEmpty()) {

// 				    printEdge();

//					System.out.println("Focus node: " + (focusNode+1));
// 					System.out.println("Focus nodes next value: " + adjList[focusNode].peek());

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
//					System.out.println("Focus Node in else: " + (focusNode+1));
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

    public void readLine(BufferedReader reader) throws IOException{
        String line;
        String [] read;

        while ((line = reader.readLine()) != null){
           read = line.split(" ");
           addEdge(Integer.parseInt(read[0]), Integer.parseInt(read[1]));
       }


    }


    public static void main (String[] args) throws IOException  {
        // first input is file name
        File myFile = new File(args[0]);
        FileReader fileReader = new FileReader(myFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // second input is action
        String action = args[1];

        // third input is starting node
        int start = Integer.valueOf(args[2]);

        Scanner input = new Scanner(System.in);
        System.out.println("How many nodes are there");
        int nodes = input.nextInt();

        Graph a = new Graph(nodes, action, myFile);
        a.readLine(bufferedReader);

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
