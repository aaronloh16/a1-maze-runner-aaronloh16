package ca.mcmaster.se2aa4.mazerunner;

public class MazeTraverser {

    public String traverseMaze(char[][] maze) {
        // Initialize position and direction(needs implementing), so far assuming east to west traveral only
        int x = findStartX(maze);
        int y = 0;
        Direction direction = Direction.EAST;

        StringBuilder path = new StringBuilder();

        while (maze[x][y] != 'E') { // Assuming 'E' marks the exit, will need to configure maze better so this can be recognized
            if (canTurnRight(x, y, direction, maze)) {
                direction = turnRight(direction);
                path.append('R');
            } else if (canMoveForward(x, y, direction, maze)) {
                moveForward(direction);
                path.append('F');
            } else {
                direction = turnLeft(direction);
                path.append('L');
            }
        }

        return path.toString();

        //need to implement the turn and move helper methods, just skeleton for now
        //also need to track direction somehow
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

