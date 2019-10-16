package com.sk.paths.matrix;

import java.io.File;
import java.util.Arrays;

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
    for (int i=1; i<=matrixSize; i++) {

      for (int j=1; j<=matrixSize; j++) {
        // Find paths
        int start = i;
        int end = j;
        int totalNodes = matrixSize;

        System.out.println("Paths from " + start +" to " + end + ""); // TODO Delete all sys outs
        writeFileLineByLine(outfile, "\nPaths from " + start +" to " + end);
        if (start == end) {
          if (arr[start][end] == 1) {
            System.out.println("  PATH: [" + start + ", " + end + "]");
            writeFileLineByLine(outfile,start + " " + end);
          } else {
            for (int l=1; l<= totalNodes; l++) {
              boolean[] visited = new boolean[totalNodes+1];
              int pathSize = 0;
              int[] path = new int[0];
              path = addElem(path, start, pathSize);
              pathSize++;

              if (arr[start][l] == 1) {
                boolean isCyclic = true;
                path = addElem(path, l, pathSize);
                pathSize++;
                findPath(l, start, arr, totalNodes, visited, path, pathSize, isCyclic, outfile);
              }
            }
          }
        } else {
            boolean[] visited = new boolean[totalNodes+1];
            int pathSize = 0;
            int[] path = new int[0];
            path = addElem(path, start, pathSize);
            pathSize++;
            boolean isCyclic = false;
            findPath(start, end, arr, totalNodes, visited, path, pathSize, isCyclic, outfile);
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
                               int pathSize, boolean isCyclic, File outfile) {
    visited[start] = true;

    if (start == end) {
      visited[start] = false;
      System.out.println("  PATH: " + Arrays.toString(path));
      writeArray(outfile, path, pathSize);
      return;
    }

    for (int k=1; k<=totalNodes; k++) {

      if (arr[start][k] == 1) {
        if (!visited[k]) {

          visited[k] = true;
          path = addElem(path, k, pathSize);
          pathSize++;
          findPath(k, end, arr, totalNodes, visited, path, pathSize, isCyclic, outfile);
          path = deleteElem(path, k, pathSize, isCyclic);
          pathSize--;
        }
      }
    }
    visited[start] = false;
    if (arr[start][end] == 0  && pathSize == 1) {
      System.out.println("  No direct path found from " + start + " to " + end);
          writeFileLineByLine(outfile, "No direct path found from " + start + " to " + end);
    }
  }
}
