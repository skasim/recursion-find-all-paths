package com.sk.paths.matrix;

import java.io.File;

import static com.sk.paths.io.FileUtils.writeArray;
import static com.sk.paths.io.FileUtils.writeFileLineByLine;
import static com.sk.paths.array.ArrayUtils.*;

/**
 * A class that features methods used to process the matrix and write output to the output file. The processMatrix
 * method parses the two-dimensional matrix to compare each node in the graph to every other node in the graph. While
 * the findPath method finds all the possible paths within a start node and an end node.
 *
 * @author Samra kasim
 */
public class ProcessMatrix {
  /**
   * The method takes in a two dimensional array and then loops through the array by using two for loops to compare
   * each node in the array to every other node in the array. The nodes are defined as start and end nodes. If a start
   * node is the same as the end node and is connected (indicated a self-loop), the method prints out the path. If the
   * start and end nodes are the same but it is not a self loop, the method will find all the paths in this cyclic loop.
   * Finally, if the start and end nodes are different, the method will find all the paths between these nodes.
   * The paths are written to the output file.
   * @param arr: int[] object representing the path.
   * @param matrixSize: int value representing the size of the matrix.
   * @param outfile: File object representing the output file.
   */
  public static void processMatrix(int[][] arr, int matrixSize, File outfile) {
    // Process the matrix using two for loops to access the value at arr[i][j]
    for (int i=1; i<=matrixSize; i++) {

      for (int j=1; j<=matrixSize; j++) {
        int start = i;
        int end = j;
        int totalNodes = matrixSize;

        writeFileLineByLine(outfile, "\nPaths from " + start +" to " + end);
        // If the start value equals the end value and arr[start][end] is 1, this means it's a self-loop so write to outputfile
        // and exist if statement
        if (start == end) {
          if (arr[start][end] == 1) {
            writeFileLineByLine(outfile,start + " " + end);
          } else {
            // If the start value equals the end value but it's not a self loop then find all the paths from the start value
            // to itself
            for (int l=1; l<= totalNodes; l++) {
              // Create a new visited array
              boolean[] visited = new boolean[totalNodes+1];
              // Set pathSize as 0
              int pathSize = 0;
              // Create a new path array
              int[] path = new int[0];
              // Add the start node to the array and increase the path size from 0 to 1
              path = addElem(path, start, pathSize);
              pathSize++;
              // If the start node is connected to the next node, then begin the process using the findPath recursive method
              // to find all paths. This is way of being able to get past the problem of findPath stopping when start node
              // equals end node. So the program takes the start node and compares it to every other node. If there is a link
              // between the start node and the second node, then it submits the second node as the start node while prepending
              // the real start node and the second node to the path and then proceeding to find the other nodes
              if (arr[start][l] == 1) {
                // isCyclic boolean indicates that this is a loop that ends in a cycle
                boolean isCyclic = true;
                path = addElem(path, l, pathSize);
                pathSize++;
                // Instantiate an object to track whether a path has been found. If no path is found for the start and
                // end nodes then "No Path Found" is written to output.
                PathFound pathFound = new PathFound(false);
                findPath(l, start, arr, totalNodes, visited, path, pathSize, isCyclic, outfile, pathFound);
              }
            }
          }
        } else {
          // If the start node does not equal the end, then we continue here by initializing the relevant variables
          boolean[] visited = new boolean[totalNodes+1];
          int pathSize = 0;
          int[] path = new int[0];
          // Add the start node to the path and increase pathSize by 1
          path = addElem(path, start, pathSize);
          pathSize++;
          // isCyclic boolean is set to false since this is not a cyclic loop
          boolean isCyclic = false;
          // Instantiate an object to track whether a path has been found. If no path is found for the start and
          // end nodes then "No Path Found" is written to output.
          PathFound pathFound = new PathFound(false);
          findPath(start, end, arr, totalNodes, visited, path, pathSize, isCyclic, outfile, pathFound);
        }
      }
    }
  }

  /**
   * A recursive method to find all the paths between two nodes. The method takes a start node and an end node. If the
   * two nodes are connected, it uses the end node as the new start node and repeats the method. If the start node
   * then equals the end node, the method prints out the path. The method backtracks once it has gone down one path
   * and repeats the recursive function to find other paths until it has exhausted all paths between the original start
   * and end nodes.
   * @param start: int value representing the start node
   * @param end: int value representing the end node.
   * @param arr: int[] object representing the path.
   * @param totalNodes: int value representing the total number of nodes in the matrix.
   * @param visited: boolean[] to track nodes that have already been visited by the method.
   * @param path: int[] representing the path, i.e., nodes that have been added to the path.
   * @param pathSize: int value representing the size of the path.
   * @param isCyclic: boolean value representing whether the path will be cyclic.
   * @param outfile: outfile: File object representing the output file.
   */
  private static void findPath(int start, int end, int[][] arr, int totalNodes, boolean[] visited, int[] path,
                               int pathSize, boolean isCyclic, File outfile, PathFound pathFound) {
    // Set the start node to visited
    visited[start] = true;

    if (start == end) {
      visited[start] = false;
      pathFound.setIsPath(true);
      writeArray(outfile, path, pathSize);
      return;
    }

    // This loop compares the start node to every other node
    for (int k=1; k<=totalNodes; k++) {
      // If the arr[start][someNode] = 1; i.e., there is a connection and tht node has not been visited
      if (arr[start][k] == 1) {
        if (!visited[k]) {
          // Then the node is marked as visited and the node is added to the path and pathSize is incremented by 1
          visited[k] = true;
          path = addElem(path, k, pathSize);
          pathSize++;
          // At this point, and I think this is really cool and it took me a while to figure out, we then pass the current
          // end node as the NEW start node. So we are getting bit by bit closer to the final destination end node
          findPath(k, end, arr, totalNodes, visited, path, pathSize, isCyclic, outfile, pathFound);
          // We delete the node from the path to allow backtracking as we exit the recursive statement and all the findPath
          // functions in the call stack start to return. We also decrease the pathSize by 1 to allow for the deletion
          path = deleteElem(path, k, pathSize, isCyclic);
          pathSize--;
        }
      }
    }
    // We also set the previously visited node to false to allow us to backtrack over this node again
    visited[start] = false;

    // Here we check to see if we have not found a path from the start node to the end node. If we haven't we print that out
    if (!pathFound.getIsPath() && pathSize == 1) {
      writeFileLineByLine(outfile, "No Path Found");
    }
  }
}
