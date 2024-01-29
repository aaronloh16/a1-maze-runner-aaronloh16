package ca.mcmaster.se2aa4.mazerunner;

public interface Traverser {
    String traverseMaze(char[][] maze) throws MazeTraversalException;;

    boolean verifyPath(char[][] maze, String path) throws PathVerificationException;



}
