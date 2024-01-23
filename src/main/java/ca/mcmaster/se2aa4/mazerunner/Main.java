package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        //This MVP is only for a straight path such as in straight.maz.txt, but the Mazeloader
        // doesn't recognize the empty line as spaces for some reason so must place character at end of line

        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option verify = new Option("p", true, "verify path");
        options.addOption(verify);

        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException exp) {
            System.err.println("Parsing failed because: " + exp.getMessage());
            return;
        }

        if (cmd == null || !cmd.hasOption("input")) {
            System.err.println("No input provided");
            return;
        }

        String inputFilePath = cmd.getOptionValue("input");
        logger.info("** Starting Maze Runner");



        MazeLoader loader = new MazeLoader();


        char[][] maze = loader.loadMaze(inputFilePath);


        if (maze == null) {
            logger.error("Failed to load the maze.");
            return;
        }


        logger.info("**** Maze to traverse:");
        printMaze(maze);


        MazeTraverser traverser = new MazeTraverser();

        if (cmd.hasOption(verify)) {
            String pathToVerify = cmd.getOptionValue(verify);
            System.out.println("Path to verify: " + pathToVerify);
            boolean isValid = traverser.verifyPath(maze, pathToVerify);
            logger.info("Path verification result: " + (isValid ? "Valid" : "Invalid"));
        } else {
            logger.info("**** Computing path:");
            String path = traverser.traverseMaze(maze);
            System.out.println(path);
        }

        logger.info("** End of MazeRunner");

        }


    private static void printMaze(char[][] maze){
        for (char[] row : maze) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }
}

