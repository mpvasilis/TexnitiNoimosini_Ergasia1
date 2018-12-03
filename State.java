
import java.util.ArrayList;


public interface State {
    boolean isGoal();

    ArrayList<State> genSuccessors();

    double findCost();

    void printState();

    boolean equals(State s);
}
