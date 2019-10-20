package com.sk.paths.matrix;

/**
 * A simple POJO to track if a path has been found to facilitate the writing to output when file has not been found. The
 * POJO is helpful because it tracks whether a path has been found through the recursive statements.
 *
 * @author Samra Kasim
 */
public class PathFound {

    private boolean isPath; // tracks if a path has been found and sets to true if there is one

    // constructor
    public PathFound(boolean isPath) {
        this.isPath = isPath;
    }

    // Getter and Setter
    public boolean getIsPath() {
        return isPath;
    }

    public void setIsPath(boolean path) {
        isPath = path;
    }

}
