import java.util.*;

public class ASTAR {

    private int numberOfNodes; //n
    private Node[][] nodes;
    private int adjMatrix[][];
    private int nodesCreated;
    private static int counter = 0;

    public ASTAR(Node[][] grid, int numberOfNodes, int adjMatrix[][]) {

        this.numberOfNodes=numberOfNodes;
        this.nodes=grid;
        this.adjMatrix=adjMatrix;
    }

    public void aStarSearch(Node source, Node goal) {

        Set<Node> explored = new HashSet<Node>();

        //100 capacity megalos arithmos, analoga me to fValue ginetai i sigrisi
        PriorityQueue<Node> queue = new PriorityQueue<>(100, new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                if(node1.getfVal() > node2.getfVal()) {
                    return 1;
                }else if (node1.getfVal() < node2.getfVal()) {
                    return -1;
                }else
                    return 0;
            }
        });

        //cost apo tin arxi
        source.setCost(0);

        queue.add(source);

        boolean found = false;

        if(getNeighbhours(source).isEmpty()) {
            System.out.println("No path leading to goal.");
        } else {

            while ((!queue.isEmpty()) && (!found)) {

                Node current = queue.poll();

                explored.add(current);
                setNodesCreated(++counter);
                //goal found
                if (current.getState() == goal.getState()) {
                    found = true;
                }

                //check all neighbhours of current code
                for (Node n : getNeighbhours(current)) {

                    Node child = n;
                    int cost = adjMatrix[current.getState()][child.getState()];
                    int temp_g_scores = current.getCost() + cost;
                    int temp_f_scores = temp_g_scores + heuristicManh(child, goal);

                    /*if child node has been evaluated and
                                    the newer f_score is higher, skip*/

                    if ((explored.contains(child)) &&
                            (temp_f_scores >= child.getfVal())) {
                        continue;
                    }

                     /*else if child node is not in queue or
                                    newer f_score is lower*/

                    else if ((!queue.contains(child)) ||
                            (temp_f_scores < child.getfVal())) {

                        child.setParent(current);
                        child.setCost(temp_g_scores);
                        child.setfVal(temp_f_scores);

                        if (queue.contains(child)) {
                            queue.remove(child);
                        }

                        queue.add(child);

                    }
                }
            }
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

    public int heuristicManh(Node source, Node goal) {

        int manhDist;

        int sourceI = source.getState() / numberOfNodes; //se pia grammi vriskete o komvos
        int sourceJ = source.getState() % numberOfNodes; //se pia stili vriskete o komvos

        int destI = goal.getState() / numberOfNodes; //se pia grammi vriskete o komvos
        int destJ = goal.getState() % numberOfNodes; //se pia stili vriskete o komvos

        int counterJ = 0; //orizodia apostasi
        int counterI = 0; //katakorifi apostasi

        //an einai se pio katw grammi i arxi tote psaxnoume pros ta pisw
        //alliws pame brosta
        if(sourceI > destI) {

            for(int i=sourceI;i>0;i--) {

                if(i != destI) {
                    counterI++;
                } else {
                    break;
                }
            }
        } else {
            for(int i=sourceI;i<numberOfNodes;i++) {

                if(i != destI) {
                    counterI++;
                } else {
                    break;
                }
            }
        }

        //an einai se pio meta stili tote psaxnoume pros ta piso
        //alliws pame brosta
        if(sourceJ > destJ) {

            for(int j=sourceJ;j>0;j--) {

                if(j != destJ) {
                    counterJ++;
                } else {
                    break;
                }
            }
        } else {

            for (int j = sourceJ; j < numberOfNodes; j++) {

                if (j != destJ) {
                    counterJ++;
                } else {
                    break;
                }
            }
        }

        manhDist = counterI + counterJ;

        return manhDist;
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