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

}