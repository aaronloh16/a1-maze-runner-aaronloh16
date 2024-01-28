package ca.mcmaster.se2aa4.mazerunner;

public interface Traverser {
    String traverseMaze(char[][] maze) throws MazeTraversalException;;

    boolean pathSimulate(char[][] maze, String path, int startX, int startY, MazeTraverser.Direction startingDirection, int[] exitCoordinates ) throws PathVerificationException;



}
