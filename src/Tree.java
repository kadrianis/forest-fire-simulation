public class Tree {


    private State state;

    // The tree is intact when it is created
    public Tree() {
        this.state = State.INTACT;
    }

    // Set the tree on fire
    public void setFire() {
        if (this.state == State.INTACT) {
            this.state = State.FIRE;
        }
    }

    // The tree burns out and becomes ashes
    public void burnOut() {
        if (this.state == State.FIRE) {
            this.state = State.ASHES;
        }
    }

    // Get the state of the tree
    public State getState() {
        return state;
    }
}
