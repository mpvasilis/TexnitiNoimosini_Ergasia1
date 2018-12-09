import java.util.*;

public class UCS

{
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
}