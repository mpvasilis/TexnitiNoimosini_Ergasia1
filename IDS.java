
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IDS {

    private int numberOfNodes; //n
    private Node[][] nodes;

    public IDS(Node[][] grid, int numberOfNodes) {

        this.numberOfNodes=numberOfNodes;
        this.nodes=grid;
    }

    public Node iterativeDeepeningSearch(Node root, Node goal) {
        // loops through until a goal node is found
        for (int depth = 0; depth < Integer.MAX_VALUE; depth++) {
            Node found = DLS(root, depth, goal);
            if (found != null) {
                return found;
            }
        }
        // this will never be reached as it
        // loops forever until goal is found
        return null;
    }

    public Node DLS(Node current, int depth, Node goal) {
        if (depth == 0 && current == goal) {
            return current;
        }
        if (depth > 0) {
            for (Node child : getNeighbhours(current)) {
                //child.setParent(current);
                Node found = DLS(child, depth - 1, goal);
                if (found != null) {
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
