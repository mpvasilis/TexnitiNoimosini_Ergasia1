
import java.util.*;

public class IDS {

    private int numberOfNodes; //n
    private Node[][] nodes;
    private int nodesCreated;
    private static int counter = 0;
    public final int MAX_NODS_CREATED=100;
    public final int EXECUTION_TIME_MILLIS = 5000; // μέγιστος χρόνος εκτέλεσης του αλγορίθμου

    public Set<Node> explored = new HashSet<Node>();

    public IDS(Node[][] grid, int numberOfNodes) {

        this.numberOfNodes=numberOfNodes;
        this.nodes=grid;
    }

    public void setNodesCreated(int nodesCreated) {
        this.nodesCreated = nodesCreated;
    }

    public int getNodesCreated() {
        return nodesCreated;
    }

    public Node iterativeDeepeningSearch(Node root, Node goal) {

        long startTime = System.currentTimeMillis();

        // loops through until a goal node is found
        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            Node found = DLS(root, depth, goal);
            if(explored.contains(root)) {

            }
            if (found != null) {
                if(getNeighbhours(found).isEmpty()) {
                    System.out.println("No path leading to goal.");
                    return null;
                }

                return found;
            }
            long timenow = System.currentTimeMillis();
            long elapsedTime = timenow - startTime;
            if (elapsedTime > EXECUTION_TIME_MILLIS) {
                System.out.println("No path leading to goal, execution time limit reached.");
                return null;
            }
        }

        return null;
    }

    public Node DLS(Node current, int depth, Node goal) {


        if (depth == 0 && current == goal) {
            return current;
        }
        if (depth > 0) {
            if(getNeighbhours(current).isEmpty()){
                return current;
            }
            for (Node child : getNeighbhours(current)) {

                Node found = DLS(child, depth - 1, goal);
                explored.add(child);
                if (found != null) {
                    setNodesCreated(++counter);
                    child.setParent(current);
                    return found;
                }
            }
        }
        return null;
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

    public static List<Node> printPath(Node target){
        List<Node> path = new ArrayList<>();

        for(Node node = target; node!=null; node = node.getParent()){
            path.add(node);
        }

        Collections.reverse(path);

        return path;
    }
}
