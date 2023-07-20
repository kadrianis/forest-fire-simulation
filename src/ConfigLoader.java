import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class ConfigLoader {
    // Declare a Properties object to hold the properties loaded from the config file
    private Properties prop;

    // Constructor loads properties from the given config file
    public ConfigLoader(String fileName) throws IOException {
        prop = new Properties();
        FileInputStream in = new FileInputStream(fileName);
        prop.load(in); // Load properties from file
        in.close(); // Close the FileInputStream
    }

    // Method to get the height of the grid from the properties
    public int getHeight() {
        return Integer.parseInt(prop.getProperty("height"));
    }

    // Method to get the width of the grid from the properties
    public int getWidth() {
        return Integer.parseInt(prop.getProperty("width"));
    }

    // Method to get the probability of a tree catching fire from the properties
    public double getProbability() {
        return Double.parseDouble(prop.getProperty("probability"));
    }

    // Method to get the initial fires from the properties
    public int[][] getInitialFires(int height, int width) throws IllegalArgumentException {
        // Split the string of fires into lines
        String[] lines = prop.getProperty("fires").split(";");

        // Check that the number of specified fires doesn't exceed the grid height
        if (lines.length > height) {
            throw new IllegalArgumentException(
                    "Too many initial fires: number of fires " + lines.length + " exceeds grid height of " + height +
                            ". Please adjust your configuration file."
            );
        }

        // Declare an array to hold the coordinates of the fires
        int[][] fires = new int[lines.length][];

        // Loop through each line
        for (int i = 0; i < lines.length; i++) {
            // If the line is empty, continue to the next line
            if (lines[i].isEmpty()) {
                fires[i] = new int[0];
                continue;
            }

            // Split the line into its y-values
            String[] yValues = lines[i].split(",");
            fires[i] = new int[yValues.length];

            // Loop through each y-value
            for (int j = 0; j < yValues.length; j++) {
                try {
                    // Convert the y-value to an integer
                    fires[i][j] = Integer.parseInt(yValues[j]);

                    // Check that the fire position doesn't exceed the grid width
                    if (fires[i][j] >= width) {
                        throw new IllegalArgumentException(
                                "Fire position at line " + i + ", position " + j + " exceeds grid width of " + width +
                                        ". Value: " + yValues[j] + ". Please adjust your configuration file."
                        );
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "Invalid value at line " + i + ", position " + j + ": " + yValues[j] +
                                    ". All values should be integers. Please adjust your configuration file."
                    );
                }
            }
        }

        // Return the array of fire coordinates
        return fires;
    }
}
