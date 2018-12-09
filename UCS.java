import java.util.*;

public class UCS

{
    private int numberOfNodes; //n
    private Node[][] nodes;
    private int adjMatrix[][];
    private int nodesCreated;
    private static int counter = 0;

    public UCS(Node[][] grid, int numberOfNodes, int adjMatrix[][]) {
        this.numberOfNodes=numberOfNodes;
        this.nodes=grid;
        this.adjMatrix=adjMatrix;
    }

    public void uniformCostSearch(Node source, Node goal) {

        source.setCost(0);

        PriorityQueue<Node> queue = new PriorityQueue<Node>(20,
                new Comparator<Node>(){

                    //override compare method
                    public int compare(Node i, Node j){
                        if(i.getCost() > j.getCost()){
                            return 1;
                        }

                        else if (i.getCost() < j.getCost()){
                            return -1;
                        }

                        else{
                            return 0;
                        }
                    }
                }

        );

        queue.add(source);
        Set<Node> explored = new HashSet<Node>();
        boolean found = false;

        if(getNeighbhours(source).isEmpty()) {
            System.out.println("No path leading to goal.");
        } else {

            //while frontier is not empty
            do{

                Node current = queue.poll();
                explored.add(current);

                setNodesCreated(++counter);
                //end if path is found
                if(current.getState() == goal.getState()){
                    found = true;
                }

                for (Node n : getNeighbhours(current)) {

                    Node child = n;
                    int cost = adjMatrix[current.getState()][child.getState()];

                    //add node to queue if node has not been explored
                    if(!explored.contains(child) && !queue.contains(child)){
                        //setNodesCreated(++counter);
                        child.setCost(current.getCost() + cost);
                        child.setParent(current);
                        queue.add(child);

                    }


                    //current path is shorter than previous path found
                    else if((queue.contains(child))&&(child.getCost()>(current.getCost()+cost))){
                        //setNodesCreated(++counter);
                        child.setParent(current);
                        child.setCost(current.getCost() + cost);
                        queue.remove(child);
                        queue.add(child);

                    }
                }

            }while(!queue.isEmpty()&& (found==false));
        }


    }


    public ArrayList<Node> getNeighbhours(Node current) {

        ArrayList<Node> neighbhours = new ArrayList<>();

        int currentI = current.getState() / numberOfNodes;
        int currentJ = current.getState() % numberOfNodes;

        if(current.getRight() != 0) {
            neighbhours.add(nodes[currentI][currentJ+1]);
        }
        if(current.getBottom() != 0) {
            neighbhours.add(nodes[currentI+1][currentJ]);
        }
        if(current.getLeft() != 0) {
            neighbhours.add(nodes[currentI][currentJ-1]);
        }
        if(current.getTop() != 0) {
            neighbhours.add(nodes[currentI-1][currentJ]);
        }

        return neighbhours;
    }

    public void setNodesCreated(int nodesCreated) {
        this.nodesCreated = nodesCreated;
    }

    public int getNodesCreated() {
        return nodesCreated;
    }

    public static List<Node> printPath(Node target){
        List<Node> path = new ArrayList<>();

        for(Node node = target; node!=null; node = node.getParent()){
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    }
    /*
    public static final int MAX_VALUE = 9999;
    private PriorityQueue<Node> priorityQueue;
    private Set<Integer> steady; // nodes που έχουν περαστεί
    private int distances[]; //πίνακας αποστάσεων
    private int N; //πλήθος κόμβων
    private int adjacencyMatrix[][]; //πίνακας γειτνίασης
    private LinkedList<Integer> path; //η διαδρομή απο το source node έως το destination
    private int[] parent;
    private int source, destination;


    // constructor
    public UCS(int N) {
        this.N = N;
        this.priorityQueue = new PriorityQueue<>(N, new Node());
        this.distances = new int[N + 1];
        this.path = new LinkedList<Integer>();
        this.adjacencyMatrix = new int[N + 1][N + 1];
        this.parent = new int[N + 1];
        this.steady = new HashSet<Integer>();

    }

    // εκκίνηση του UCS
    public int uniformCostSearch(int adjacencyMatrix[][], int source, int destination) {
        int evaluationNode;
        this.source = source;
        this.destination = destination;

        for (int i = 1; i <= N; i++) {
            distances[i] = MAX_VALUE;
        }
        for (int sourcenode = 1; sourcenode <= N; sourcenode++) {
            for (int destinationnode = 1; destinationnode <= N; destinationnode++) {
                this.adjacencyMatrix[sourcenode][destinationnode] = adjacencyMatrix[sourcenode][destinationnode];
            }
        }
        priorityQueue.add(new Node(source, 0));
        distances[source] = 0;

        while (!priorityQueue.isEmpty()) {
            evaluationNode = getNodeWithMinDistanceFromPriorityQueue();
            System.out.println("Checking node: " + evaluationNode);
            if (evaluationNode == destination) {
                return distances[destination];
            }
            steady.add(evaluationNode);
            addFrontiersToQueue(evaluationNode);
        }
        return distances[destination];
    }


    private void addFrontiersToQueue(int evaluationNode) {
        for (int destination = 1; destination <= N; destination++) {
            if (!steady.contains(destination)) {
                if (adjacencyMatrix[evaluationNode][destination] != MAX_VALUE) {
                    if (distances[destination] > adjacencyMatrix[evaluationNode][destination] + distances[evaluationNode])

                    {
                        distances[destination] = adjacencyMatrix[evaluationNode][destination] + distances[evaluationNode];
                        parent[destination] = evaluationNode;
                    }

                    Node node = new Node(destination, distances[destination]);
                    priorityQueue.remove(node);
                    priorityQueue.add(node);
                }
            }
        }
    }

    // βρίσκει το node με την μικρότερη απόστασταση από το priority queue
    private int getNodeWithMinDistanceFromPriorityQueue() {
        Node node = priorityQueue.remove();
        return node.node;
    }

    // εκτυπώνει την διαδρομή από το source node μέχρι το τελικό
    public void printPath() {
        path.add(destination);
        boolean found = false;
        int vertex = destination;
        while (!found) {
            if (vertex == source) {
                found = true;
                continue;
            }
            path.add(parent[vertex]);
            vertex = parent[vertex];
        }

        System.out.println("The path between " + source + " and " + destination + " is:");
        Iterator<Integer> iterator = path.descendingIterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + "\t");
        }

    }
    */
}