package ca.mcmaster.se2aa4.mazerunner;

public class MazeTraverser {
    public String traverseMaze(char[][] maze) {
        // ASSUMES the maze is a straight horizontal line with walls on top and bottom.

        int pathRow = findPathRow(maze);
        if (pathRow == -1) {
            return "No path found in the maze";
        }

        StringBuilder path = new StringBuilder();


        for (int col = 0; col < maze[0].length - 1; col++) {
            if (maze[pathRow][col] == ' ') {
                path.append('F');
            } else {
                return "Encountered an obstacle";
            }
        }

        return path.toString();
    }




        public boolean verifyPath(char[][] maze, String path) {

            long forwardMoves = path.chars().filter(ch -> ch == 'F').count(); //count forward moves in path

            int mazeWidth = maze[0].length;

            // Verify if the number of forward moves equals the maze width minus 1, very MVP
            return forwardMoves == (mazeWidth - 1);
        }




    private int findPathRow(char[][] maze) {
        for (int row = 0; row < maze.length; row++) {
            // Check if the first and last columns in this row are not walls,
            // indicating that this row contains the path
            if (maze[row][0] == ' ') {
                return row;
            }
        }
        return -1;
    }
}

