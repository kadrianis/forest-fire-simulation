import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {
    private Tree[][] trees; // Grid of trees
    private double propagationProbability; // Fire propagation probability
    private Random random = new Random(); // Random number generator

    // Constructor that initializes the grid of trees
    public Grid(int height, int width, double propagationProbability) {
        this.trees = new Tree[height][width];
        this.propagationProbability = propagationProbability;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                trees[i][j] = new Tree();
            }
        }
    }

    // Sets fire to a tree at position (x, y)
    public void setFire(int x, int y) {
        trees[x][y].setFire();
    }

    // Returns the grid of trees
    public Tree[][] getTrees() {
        return trees;
    }

    // Checks if a tree is on fire in the forest
    public boolean isOnFire() {
        for (Tree[] row : trees) {
            for (Tree tree : row) {
                if (tree.getState() == State.FIRE) {
                    return true;
                }
            }
        }
        return false;
    }

    // Executes a step of the simulation
    public void step() {
        List<int[]> fires = identifyFires();
        burnTrees(fires);
        spreadFires(fires);
    }

    // Identifies trees on fire
    private List<int[]> identifyFires() {
        List<int[]> fires = new ArrayList<>();
        for (int x = 0; x < trees.length; x++) {
            for (int y = 0; y < trees[x].length; y++) {
                if (trees[x][y].getState() == State.FIRE) {
                    fires.add(new int[]{x, y});
                }
            }
        }
        return fires;
    }

    // Burns the identified trees on fire
    private void burnTrees(List<int[]> fires) {
        for (int[] fire : fires) {
            int x = fire[0];
            int y = fire[1];
            trees[x][y].burnOut();
        }
    }

    // Spreads the fire to neighboring trees using the defined method "spreadFire"
    private void spreadFires(List<int[]> fires) {
        for (int[] fire : fires) {
            int x = fire[0];
            int y = fire[1];
            spreadFire(x, y);
        }
    }

    // Spreads the fire to a neighboring tree depending on the propagation probability
    private void spreadFire(int x, int y) {
        // propagation to the left
        if (x > 0 && trees[x - 1][y].getState() == State.INTACT && random.nextDouble() < propagationProbability) {
            trees[x - 1][y].setFire();
        }
        // propagation to the right
        if (x < trees.length - 1 && trees[x + 1][y].getState() == State.INTACT && random.nextDouble() < propagationProbability) {
            trees[x + 1][y].setFire();
        }
        // propagation upwards
        if (y > 0 && trees[x][y - 1].getState() == State.INTACT && random.nextDouble() < propagationProbability) {
            trees[x][y - 1].setFire();
        }
        // propagation downwards
        if (y < trees[x].length - 1 && trees[x][y + 1].getState() == State.INTACT && random.nextDouble() < propagationProbability) {
            trees[x][y + 1].setFire();
        }
    }

    // Counts the number of burned trees
    public int getNumberOfBurnedTrees() {
        int count = 0;
        for (Tree[] row : trees) {
            for (Tree tree : row) {
                if (tree.getState() == State.ASHES) {
                    count++;
                }
            }
        }
        return count;
    }

    // Counts the number of intact trees
    public int getNumberOfIntactTrees() {
        int count = 0;
        for (Tree[] row : trees) {
            for (Tree tree : row) {
                if (tree.getState() == State.INTACT) {
                    count++;
                }
            }
        }
        return count;
    }

    // Count the number of trees on fire
    public int getNumberOfOnFireTrees() {
        int count = 0;
        for (Tree[] row : trees) {
            for (Tree tree : row) {
                if (tree.getState() == State.FIRE) {
                    count++;
                }
            }
        }
        return count;
    }

}
