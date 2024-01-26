package ca.mcmaster.se2aa4.mazerunner;

public class OutputFormatter {
    public String toCanonicalForm(String path) {
        //could use this if i want to add spaces between groups of same directions
        return path;
    }


    public String toFactorizedForm(String path) {
        if (path == null || path.isEmpty()) {
            return "empty or null path";
        }

        StringBuilder factorizedPath = new StringBuilder();
        char prevChar = path.charAt(0);
        int count = 1;

        for (int i = 1; i < path.length(); i++) {
            char currentChar = path.charAt(i);

            if (currentChar == prevChar) {
                count++;
            } else {
                appendCharAndCount(factorizedPath, prevChar, count);
                count = 1;
                prevChar = currentChar;
            }
        }

        // needed to add this because last group of characters needs to be
        // appended because there will be no change in character to trigger append
        appendCharAndCount(factorizedPath, prevChar, count);

        return factorizedPath.toString();
    }

    //Helper method to appends count and direction to Path
    private void appendCharAndCount(StringBuilder sb, char character, int count) {
        if (count > 1) {
            sb.append(count);
        }
        sb.append(character);
    }
}
