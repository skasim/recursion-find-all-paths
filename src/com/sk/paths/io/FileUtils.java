package com.sk.paths.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class representing utility methods to facilitate reading of input file and writing to output file. Since the input
 * is read character by character, there are some unique helper methods that were needed such as convertingCharToInt to
 * return the integer representation of a character. The parsing of the a row of input into an array and writing
 * an array to output are also handled in this class.
 *
 * @author Samra Kasim
 */
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

  /**
   * Helper method to facilitate the writing of an com.sk.paths.array to output. The method first loops through the com.sk.paths.array and
   * then prints out the com.sk.paths.array integer by interger
   * @param outFile: File object representing the output file.
   * @param arr: int[] object representing the path com.sk.paths.array
   * @param pathSize: int object representing the size of the path to mitigate use of .length or .size functions
   */
  public static void writeArray(File outFile, int[] arr, int pathSize)  {
    BufferedWriter writer = createWriter(outFile);
    try {
      writer.newLine();
      for (int i=0; i<pathSize; i++) {
        writer.write(arr[i] + " ");
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

  /**
   * Helper method to take a row of input and based on the matrix of the size convert into an int com.sk.paths.array
   * @param row: A string of number from the input file representing a row of the matrix
   * @param matrixSize: int value representing the size of the matrix
   * @return: An int[] representing the String row in an com.sk.paths.array form
   */
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
}
