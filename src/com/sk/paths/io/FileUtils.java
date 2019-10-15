package com.sk.paths.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

  /**
   * A helper method to convert char input to an integer.
   * @param c is a Char representing a character from input
   * @return int value.
   */
  public static int convertCharToInt(char c) {
    if (c == '0') {
      return 0;
    }
    else if (c == '1') {
      return 1;
    }
    else {
      return -1; // TODO more error handling here
    }
  }


  /**
   * Helper method to take a File object representing the output file and return a BufferedWrite object.
   * @param outFile: String value representing the output file
   * @return a BufferedWriter object
   */
  private static BufferedWriter createWriter(File outFile) {
    BufferedWriter writer = null;
    try {
      writer = new BufferedWriter(new FileWriter(outFile, true));
    } catch (IOException e) {
      System.err.println(e.toString());
    }
    return writer;

  }

  /**
   * Helper method to facilitate the writing of a file line by line (by appending to a file).
   * @param outFile: File object representing the output file.
   * @param line: String value representing a line of input text.
   * @throws IOException
   */
  public static void writeFileLineByLine(File outFile, String line)  {
    BufferedWriter writer = createWriter(outFile);
    try {
      writer.newLine();
      if (line != null) {
        writer.write(line);
      }
    } catch (IOException e) {
      System.err.println(e.toString());
    }
    try {
      writer.close();
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }


  public static int[] parseRow(String row, int matrixSize) {
    int[] rowArr = new int[matrixSize];
    int index = 0;
    for (char c : row.toCharArray()) {
      int num = convertCharToInt(c);
      if (num != -1) {
        rowArr[index] = convertCharToInt(c);
        index++;
      }
    }
    return rowArr;
  }
  // TODO Delete
  public static void printArray(int[][] arr, int matrixSize) {
    System.out.println("Array");
    for(int i=1; i<=matrixSize; i++)
    {
      for(int j=1; j<=matrixSize; j++)
      {
        System.out.print(arr[i][j]+ "  ");
      }
      System.out.println();
    }
  }
}
