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
        logger.info("\n" + getMazeString(maze));


        MazeTraverser traverser = new MazeTraverser();

        try {
            if (cmd.hasOption(verify)) {
                String pathToVerify = cmd.getOptionValue(verify).replace(" ", "");
                logger.info("Path to verify: " + pathToVerify);
                boolean isValid = traverser.verifyPath(maze, pathToVerify);
                System.out.println("Result: " + (isValid ? "correct path" : "incorrect path"));
            } else {
                logger.info("**** Computing path:");
                String path = traverser.traverseMaze(maze);
                OutputFormatter formatter = new OutputFormatter();
                String factorizedPath = formatter.toFactorizedForm(path);
                System.out.println(factorizedPath);
            }
        }catch (MazeTraversalException e) {
            logger.error("Maze traversal failed: " + e.getMessage());
        } catch (PathVerificationException e) {
            logger.error("Path verification failed: " + e.getMessage());
        }

        logger.info("** End of MazeRunner");

        }


    private static String getMazeString(char[][] maze) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : maze) {
            for (char cell : row) {
                sb.append(cell);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}

