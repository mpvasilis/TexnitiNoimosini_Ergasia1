import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Grid {

    private static int n; //plithos grammwn kai stilwn tou plegmatos (n x n   komvoi)
    boolean[][] visited;
    private Node nodes[][];
    private int adjMatrix[][];

    public void createGrid() {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter the size of the grid: ");
        n = input.nextInt();

        nodes = new Node[n][n];
        adjMatrix = new int[n * n][n * n];

        int state = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String nodeName = "("+Integer.toString(i)+","+Integer.toString(j)+")";
                nodes[i][j] = new Node(state++, nodeName);

                setNodeNeighbours(i, j);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                fillAdjMatrix(i, j);
        }

        //printAdjMatrix();
    }

    void printAdjMatrix() {

        for (int i = 0; i < n * n; i++) {
            for (int j = 0; j < n * n; j++) {
                System.out.print(adjMatrix[i][j] + "  ");
            }
            System.out.println();
        }

        System.out.println("\n\n");
    }

    public int[][] getAdjMatrix() {

        return adjMatrix;

    }

    void fillAdjMatrix(int i, int j) {

        if (nodes[i][j].getRight() == 1) {
            addEdge(nodes[i][j].getState(), nodes[i][j + 1].getState());
        }
        if (nodes[i][j].getBottom() == 1) {
            addEdge(nodes[i][j].getState(), nodes[i + 1][j].getState());
        }

    }

    void addEdge(int i, int j) {
        adjMatrix[i][j] = 1;
        adjMatrix[j][i] = 1;
    }

    void removeEdge(int i, int j) {
        adjMatrix[i][j] = 0;
        adjMatrix[j][i] = 0;
    }

    //Apodidei se kathe akmi to varos tis
    public void setNodeNeighbours(int i, int j) {
        if (j != 0) {

            //to aristero melos tou komvou einai idio me to deksi tou patera tou
            nodes[i][j].setLeft(nodes[i][j - 1].getRight());
            if (i != n - 1)
                nodes[i][j].setBottom(1);
            //o teleutaios komvos dn exei deksia akmi
            if (j != n - 1)
                nodes[i][j].setRight(1);

            //osoi dn vriskodai stin prwti grammi
            //exoun kai panw akmi pou einai idia me tin katw akmi
            //tou panw komvou
            if (i != 0)
                nodes[i][j].setTop(nodes[i - 1][j].getBottom());

        } else {
            //oi prwtoi komvoi apo kathe grammi
            //exoun mono deksia kai katw akmi
            nodes[i][j].setRight(1);
            //stin teleutaia grammi prwtos komvos dn exei katw akmi
            if (i != n - 1)
                nodes[i][j].setBottom(1);
            //osoi dn vriskodai stin prwti grammi
            //exoun kai panw akmi pou einai idia me tin katw akmi
            //tou panw komvou
            if (i != 0)
                nodes[i][j].setTop(nodes[i - 1][j].getBottom());
        }
    }

    public void deleteEdges() {

        //pososto diagrafis akmwn
        int p;
        int edges, new_edges; //total number of edges
        int del_edges = 0;

        Scanner input = new Scanner(System.in);

        System.out.print("Enter the percentage of edges you want to delete: ");
        p = input.nextInt();

        edges = 2 * n * (n - 1);
        System.out.print("Total edges = " + edges);

        //se periptosi pou dwsei poli mikro pososto na diagrafete estw kai 1 akmi
        del_edges = (int) (Math.ceil(edges * (p * 0.01)));

        new_edges = edges - del_edges;

        System.out.print("\nAfter delete edges = " + new_edges + "\n\n");


        Random rand = new Random();

        int rand_i;
        int rand_j;

        for (int i = 0; i < del_edges; i++) {

            do {
                rand_i = rand.nextInt(n * n);
                rand_j = rand.nextInt(n * n);
            } while (adjMatrix[rand_i][rand_j] != 1);

            System.out.println("====DELETED EDGES====");
            System.out.println("-----------------\nNode1: "+rand_i+"\nNode2: "+rand_j);
            System.out.println("Node1["+(rand_i/n)+"]["+(rand_i%n)+"]\nNode2["+(rand_j / n)+"]["+(rand_j%n)+"]");
            System.out.println("=====================");

            if(Math.abs(rand_j - rand_i) == 1) {
                if(rand_i > rand_j) {
                    nodes[(rand_i / n)][(rand_i % n)].setLeft(0);
                    nodes[(rand_j / n)][(rand_j % n)].setRight(0);
                } else {
                    nodes[(rand_i / n)][(rand_i % n)].setRight(0);
                    nodes[(rand_j / n)][(rand_j % n)].setLeft(0);
                }
            } else {
                if(rand_i > rand_j) {
                    nodes[(rand_i / n)][(rand_i % n)].setTop(0);
                    nodes[(rand_j / n)][(rand_j % n)].setBottom(0);
                } else {
                    nodes[(rand_i / n)][(rand_i % n)].setBottom(0);
                    nodes[(rand_j / n)][(rand_j % n)].setTop(0);
                }
            }

            removeEdge(rand_i, rand_j);
        }

        //printAdjMatrix();
    }

    public void valueEdges() {

        int value;

        for (int i = 0; i < n*n; i++) {
            for (int j = 0; j < n*n; j++)
                if (adjMatrix[i][j] == 1) {
                    //value = (int) (Math.random() * 40 + 10);
                    value = (int) (Math.random() * 5 + 1);
                    adjMatrix[i][j] = value;
                    adjMatrix[j][i] = value;
                    ;
                }
        }

        //printAdjMatrix();
    }

    public void UCS() {
        System.out.println("\n\n--------- UCS ---------");

        UCS ucs = new UCS(n);
        ucs.uniformCostSearch(getAdjMatrix(), (int) (Math.random() * (n - 1) + 1), (int) (Math.random() * (n - 1) + 1));
        ucs.printPath();
        System.out.println("\n-----------------------");


    }

    public void IDS() {

        System.out.println("\n\n--------- IDS ---------");
        Node start = getRandomNode();
        Node goal = getRandomNode();

        IDS ids = new IDS(nodes, n);
        Node finish = ids.iterativeDeepeningSearch(start, goal);
        List<Node> path = ids.printPath(finish);
        System.out.println("Start: "+start.getValue()+"\nGoal: "+goal.getValue());
        System.out.println("Path: "+path);
        System.out.println("Nodes created: "+ids.getNodesCreated());
        System.out.println("-----------------------");
    }

    public void ASTAR() {

        System.out.println("\n\n--------- A* ---------");
        Node start = getRandomNode();
        Node goal = getRandomNode();

        ASTAR astar = new ASTAR(nodes, n);
        astar.aStarSearch(start, goal);
        List<Node> path = astar.printPath(goal);

        System.out.println("Start: "+start.getValue()+"\nGoal: "+goal.getValue());
        System.out.println("Path: "+path);
        System.out.println("-----------------------");
    }

    public Node getRandomNode() {

        Random rand = new Random();

        int rand_i = rand.nextInt(n);
        int rand_j = rand.nextInt(n);

        return nodes[rand_i][rand_j];
    }


}
