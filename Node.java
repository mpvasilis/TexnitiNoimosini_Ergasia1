import java.util.Comparator;

class Node implements Comparator<Node> {

    private static int depth;
    public int node;
    private int state;
    private Node parent;
    private int left, right, top, bottom;
    private int cost;


    public Node() {

    }

    public Node(int node, int cost) {
        this.node = node;
        this.cost = cost;
    }


    public Node(int s) {

        state = s;
        parent = null;
        left = 0;
        right = 0;
        bottom = 0;
        top = 0;

        cost = 0;
        depth++;
    }

    public int getState() {
        return state;
    }

    ;

    public Node getParent() {
        return parent;
    }

    ;

    public void setParent(Node par) {
        parent = par;
    }

    ;

    public int getLeft() {
        return left;
    }

    ;

    public void setLeft(int l) {
        left = l;
    }

    ;

    public int getRight() {
        return right;
    }

    ;

    public void setRight(int r) {
        right = r;
    }

    ;

    public int getTop() {
        return top;
    }

    ;

    public void setTop(int t) {
        top = t;
    }

    ;

    public int getBottom() {
        return bottom;
    }

    ;

    public void setBottom(int b) {
        bottom = b;
    }

    ;

    public int getCost() {
        return cost;
    }

    ;

    public int getDepth() {
        return depth;
    }

    ;

    @Override
    public int compare(Node node1, Node node2) {
        if (node1.cost < node2.cost)
            return -1;
        if (node1.cost > node2.cost)
            return 1;
        if (node1.node < node2.node)
            return -1;
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            return this.node == node.node;
        }
        return false;
    }
}
