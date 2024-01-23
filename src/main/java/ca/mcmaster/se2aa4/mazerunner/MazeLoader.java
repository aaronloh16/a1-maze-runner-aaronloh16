package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MazeLoader {

    // This class loads the maze from a file into a 2D character array.
    public char[][] loadMaze(String filePath) {
        ArrayList<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {  // Read the maze file line by line
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line); // Add each line to the list
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null; //returns null if the maze cant be loaded
        }


        int numRows = lines.size();
        int numCols = lines.isEmpty() ? 0 : lines.get(0).length();


        char[][] maze = new char[numRows][numCols];


        for (int i = 0; i < numRows; i++) {  // Fill the 2D array with characters from the maze file
            String currentLine = lines.get(i);
            for (int j = 0; j < currentLine.length(); j++) {
                // Copy each character into the 2D array
                maze[i][j] = currentLine.charAt(j);
            }
        }


        return maze;
    }
}
