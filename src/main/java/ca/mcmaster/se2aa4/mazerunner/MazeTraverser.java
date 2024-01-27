package ca.mcmaster.se2aa4.mazerunner;

public class MazeTraverser implements Traverser {

    public enum Direction {
        NORTH, EAST, SOUTH, WEST;

        public Direction turnRight() {
            return switch (this) {
                case NORTH -> EAST;
                case EAST -> SOUTH;
                case SOUTH -> WEST;
                case WEST -> NORTH;
                default -> throw new IllegalStateException("Unknown direction: " + this);
            };
        }


        public Direction turnLeft() {
            return switch (this) {
                case NORTH -> WEST;
                case WEST -> SOUTH;
                case SOUTH -> EAST;
                case EAST -> NORTH;
                default -> throw new IllegalStateException("Unknown direction: " + this);
            };
        }
    }


    private int[] getAdjacentCoordinates(int x, int y, Direction direction) {
        return switch (direction) {
            case NORTH -> new int[]{x - 1, y};
            case EAST -> new int[]{x, y + 1};
            case SOUTH -> new int[]{x + 1, y};
            case WEST -> new int[]{x, y - 1};
        };
    }

    private boolean isValidMove(int x, int y, char[][] maze) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == ' ';
    }

    private boolean canTurnRight(int x, int y, Direction direction, char[][] maze) {
        int[] rightCoordinates = getAdjacentCoordinates(x, y, direction.turnRight());
        return isValidMove(rightCoordinates[0], rightCoordinates[1], maze);
    }

    private boolean canTurnLeft(int x, int y, Direction direction, char[][] maze) {
        int[] leftCoordinates = getAdjacentCoordinates(x, y, direction.turnLeft());
        return isValidMove(leftCoordinates[0], leftCoordinates[1], maze);
    }

    private boolean canMoveForward(int x, int y, Direction direction, char[][] maze) {
        int[] forwardCoordinates = getAdjacentCoordinates(x, y, direction);
        return isValidMove(forwardCoordinates[0], forwardCoordinates[1], maze);
    }

    private int[] moveForward(int x, int y, Direction direction) {
        return getAdjacentCoordinates(x, y, direction);
    }

    private int findStartX(char[][] maze) {  //used to be called findPathRow in  MVP
        for (int x = 0; x < maze.length; x++) {
            if (maze[x][0] == ' ') {
                return x;
            }
        }
        return -1; // if its not found
    }

    private int[] findExitCoordinates(char[][] maze) {
        for (int x = 0; x < maze.length; x++) {
            if (maze[x][maze[0].length - 1] == ' ') { //same as find entrance but for west side
                return new int[]{x, maze[0].length - 1};
            }
        }
        return new int[]{-1, -1};
    }





    public String traverseMaze(char[][] maze) {
        int[] exitCoordinates = findExitCoordinates(maze);
        if (exitCoordinates[0] == -1 || exitCoordinates[1] == -1) {
            return "Exit not found";
        }

        int x = findStartX(maze);
        int y = 0;
        Direction direction = Direction.EAST;

        if (x == -1) {
            return "Entrance not found";
        }

        StringBuilder path = new StringBuilder();

        int iterationCount = 0;
        int limit = 100000; // Set a limit to prevent infinite loops

        while ((x != exitCoordinates[0] || y != exitCoordinates[1]) && iterationCount < limit) {
            System.out.println("Step " + iterationCount + ": Position (" + x + ", " + y + "), Direction: " + direction);

            if (canTurnRight(x, y, direction, maze)) {
                direction = direction.turnRight();
                path.append('R');
                int[] newCoordinates = moveForward(x, y, direction);
                x = newCoordinates[0];
                y = newCoordinates[1];
                path.append('F');
            } else if (canMoveForward(x, y, direction, maze)) {
                int[] newCoordinates = moveForward(x, y, direction);
                x = newCoordinates[0];
                y = newCoordinates[1];
                path.append('F');
            } else if (canTurnLeft(x, y, direction, maze)) { // Check if can turn left
                direction = direction.turnLeft();
                path.append('L');
            } else {
                // Dead-end, turn around
                direction = direction.turnLeft().turnLeft();
                path.append('L').append('L');
            }
            iterationCount++;
        }

        if (iterationCount >= limit) {
            return "possible infinite loop";
        }

        return path.toString();
    }


    public boolean verifyPath(char[][] maze, String path) {
        int startX = findStartX(maze);
        int[] exitCoordinates = findExitCoordinates(maze);
        if (startX == -1 || exitCoordinates[0] == -1) {
            return false; //invalid maze
        }

        boolean checkEastWest = pathSimulate(maze, path, startX, 0, Direction.EAST, exitCoordinates);
        boolean checkWestEast = pathSimulate(maze, path, exitCoordinates[0],exitCoordinates[1],Direction.WEST, new int[]{startX, 0});
        return checkEastWest||checkWestEast;
    }


    public boolean pathSimulate(char[][] maze, String path,int startX, int startY, Direction startingDirection, int[] exitCoordinates ){
        int x = startX;
        int y = startY;
        Direction direction = startingDirection; // Assuming the entrance faces east

        for (char move : path.toCharArray()) {
            if (move == 'F') {
                if (canMoveForward(x, y, direction, maze)) {
                    int[] newCoordinates = moveForward(x, y, direction);
                    x = newCoordinates[0];
                    y = newCoordinates[1];
                } else {
                    return false; //trying to go into a wall = concussion, which is bad
                }
            } else if (move == 'R') {
                direction = direction.turnRight();
            } else if (move == 'L') {
                direction = direction.turnLeft();
            } else {
                return false; //if user inuputs unknown character
            }
        }

        return x == exitCoordinates[0] && y == exitCoordinates[1]; // Check if the path ends at the exit
    }





}

