package com.sk.paths.matrix;

import java.io.File;
import java.util.Arrays;

import static com.sk.paths.io.FileUtils.writeArray;
import static com.sk.paths.io.FileUtils.writeFileLineByLine;
import static com.sk.paths.array.ArrayUtils.*;

public class ProcessMatrix {

  public static void processMatrix(int[][] arr, int matrixSize, File outfile) {
    for (int i=1; i<=matrixSize; i++) {

      for (int j=1; j<=matrixSize; j++) {
        // Find paths
        int start = i;
        int end = j;
        int totalNodes = matrixSize;

        System.out.println("Paths from " + start +" to " + end + "");
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

  private static void findPath(int start, int end, int[][] arr, int totalNodes, boolean[] visited, int[] path, int pathSize, boolean isCyclic, File outfile) {
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
