package com.sk.paths;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static com.sk.paths.io.FileUtils.*;
import static com.sk.paths.matrix.ProcessMatrix.processMatrix;

public class Paths {

  public static void main(String[] args) {
    // Check if input and output filepaths are provided. If not, exit program execution.
    if (args.length != 2) {
      System.err.println("Input and output file paths must be provided to run this simulation. Exiting now.");
      System.exit(1);
    }
    // Variables related to program IO
    String inFilepath = args[0];
    String outFilepath = args[1];
    File outFile = new File(outFilepath);

    try {
      // Instantiate objects used throughout project execution
      Scanner scanner = new Scanner(new File(inFilepath));

      while(scanner.hasNextLine()) {

        String line = scanner.nextLine();
        writeFileLineByLine(outFile, "\n" + line);
        int matrixSize = Integer.parseInt(line);
        int[][] arr = new int[matrixSize+1][matrixSize+1];

        for (int i=1; i<=matrixSize; i++) {
          String row = scanner.nextLine();
          writeFileLineByLine(outFile, row);
          int[] parsedRow = parseRow(row, matrixSize);
          for (int j=1; j<=matrixSize; j++) {
            arr[i][j] = parsedRow[j-1];
          }
        }
        System.out.println("MATRIX " + matrixSize);
        printArray(arr, matrixSize);
        processMatrix(arr, matrixSize, outFile);
      }

      scanner.close();
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }
}
