package com.sk.paths;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static com.sk.paths.io.FileUtils.*;
import static com.sk.paths.matrix.ProcessMatrix.processMatrix;

/**
 * The program reads an input file, which contains matrices representing a connected graph. The program processes the
 * matrix using a recursive function findPath to find the paths between two nodes and when a particular path has been
 * processed to backtrack and find new paths, until all possible paths have been exhausted. The program uses the processMatrix
 * method to loop through the two-dimensional array to ensure all nodes are processed. The program writes the output to
 * an output file.
 *
 *  To execute the program, refer to the README.
 *
 * @author Samra Kasim
 */
public class Paths {
  /**
   * Main method to enter the program. Input and output filepaths are provided as arguments in the command line.
   * The class reads each row of input text character by character. The method any comments provided using // in the
   * input file. The method then reads the first line of input as the size of the matrix and uses that value to process
   * the remaining rows of the matrix and adds then to a two dimensional matrix. The method then passes that array to
   * the processMatrix function, which process the matrix and compares every node to every other node and writes the
   * generated paths to an output file. The method loops through the input file until all matrices are processed.
   *
   * @param args Takes two command line arguments, the input filepath and the output filepath
   */
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
      // Instantiate Scanner object to read in the input file
      Scanner scanner = new Scanner(new File(inFilepath));

      writeFileLineByLine(outFile, "########################################\n");
      writeFileLineByLine(outFile, "#    Lab2: Find All Paths              #\n");
      writeFileLineByLine(outFile, "#    By: Samra Kasim                   #\n");
      writeFileLineByLine(outFile, "########################################\n");

      int matrixCount = 1;

      // Parse the input file line by line
      while(scanner.hasNextLine()) {
        String line = scanner.nextLine();

        try {
          // Set the first line as the matrix size
          int matrixSize = Integer.parseInt(line);
          // Error handling for a misplaced matrix size when no matrix follows
          if (matrixSize == 0 || matrixSize == 1) throw new NumberFormatException();
          writeFileLineByLine(outFile, "\n########################################\n");
          writeFileLineByLine(outFile, "              Matrix " + matrixCount +" of Size " + matrixSize+"\n");
          writeFileLineByLine(outFile, "########################################\n");
          matrixCount++;
          // Create a two dimensional array based on the matrix. Matrix size is increased by 1 to allow for indexing
          // beginning at 1 instead of 0
          int[][] arr = new int[matrixSize + 1][matrixSize + 1];
          writeFileLineByLine(outFile, "\n" + line);
          // Using the matrix size, loop the same number of lines as the matrix size
          for (int i = 1; i <= matrixSize; i++) {
            String row = scanner.nextLine();
            writeFileLineByLine(outFile, row);
            // Parse each row of the matrix and add the values to the two dimensional array
            int[] parsedRow = parseRow(row, matrixSize);
            for (int j = 1; j <= matrixSize; j++) {
              arr[i][j] = parsedRow[j - 1];
            }
          }
          // Process the matrix by providing the two dimensional array and write paths to outputfile
          processMatrix(arr, matrixSize, outFile);
        } catch (NumberFormatException e) {
          System.err.println(e.toString());
        }
      }
      // Close the scanner once the input file has been processed
      scanner.close();
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }
}
