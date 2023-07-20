import java.io.IOException;

public class Simulation {

    // Method to display the current state of the grid
    private static void displayGrid(Grid grid) {
        Tree[][] trees = grid.getTrees();
        for (Tree[] row : trees) {
            for (Tree tree : row) {
                char c;
                switch (tree.getState()) {
                    case FIRE:
                        c = '*';
                        break;
                    case ASHES:
                        c = '-';
                        break;
                    default: // State.INTACT
                        c = '.';
                }
                System.out.print(c + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        try {
            // Load configurations from the file
            ConfigLoader config = new ConfigLoader("C:\\Users\\kanis\\IdeaProjects\\forest-fire-simulation\\src\\config.properties");

            // Get the configurations
            int height = config.getHeight();
            int width = config.getWidth();
            double probability = config.getProbability();

            // Create a grid with the configurations
            Grid grid = new Grid(height, width, probability);

            // Get the initial fires
            int[][] fires = config.getInitialFires(height, width);

            // Set fire to the specified positions
            for (int x = 0; x < fires.length; x++) {
                for (int y : fires[x]) {
                    grid.setFire(x, y);
                }
            }

            // Display the initial state of the forest
            System.out.println("Initial state of the forest:");
            displayGrid(grid);
            System.out.println("Number of burned trees: " + grid.getNumberOfBurnedTrees());
            System.out.println("Number of unburned trees: " + grid.getNumberOfIntactTrees());
            System.out.println("Number of on fire trees: " + grid.getNumberOfOnFireTrees());

            System.out.println();
            // Counter for the number of steps
            int stepCount = 0;

            // Loop until the forest is no longer on fire
            while (grid.isOnFire()) {
                grid.step();
                System.out.println("Step " + (++stepCount) + ":");
                System.out.println("Number of burned trees: " + grid.getNumberOfBurnedTrees());
                System.out.println("Number of unburned trees: " + grid.getNumberOfIntactTrees());
                System.out.println("Number of on fire trees: " + grid.getNumberOfOnFireTrees());

                displayGrid(grid);
            }

            // Display the number of steps that the fire propagation took
            System.out.println("The fire propagation is finished after " + stepCount + " steps.");
        } catch (IllegalArgumentException e) {
            // If the configurations are not correct, display the error message and terminate the program
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            // If the config file loading fails, display the error message and terminate the program
            System.out.println("Failed to load config file: " + e.getMessage());
            System.exit(1);
        }
    }
}
