package com.sk.paths.array;

/**
 * A class representing utility methods to manage handling a Java primitive array. The class makes it easier to add
 * and delete elements from a primitive array and mimics how it would be handled in Java ArrayList.
 *
 * @author Samra Kasim
 */
public class ArrayUtils {
  /**
   * Helper to delete an element from an array. The method creates a new array that is one size less than the size of
   * the original passed in array. It uses the pathSize variable to manage this to mitigate using the .length method.
   * The method then uses the old array to rebuild a new array and leaves off the last element. The method functions
   * slightly different if isCyclic is true because that represents a cycle in the graph (e.g, 121), so for these cyclic
   * paths the method keeps the first array as is from the original array and only manipulates the array from index 1
   * onwards.
   * @param arr: int[] object representing the path.
   * @param elem: An int value representing the element (a node) to be inserted.
   * @param pathSize: int object representing the size of the path.
   * @param isCyclic: boolean value representing whether the path will be cyclic.
   * @return: An int[] representing the newly formed array.
   */
  public static int[] deleteElem(int[] arr, int elem, int pathSize, boolean isCyclic) {
    int[] newArr = new int[pathSize-1];
    if (!isCyclic) {
      int count = 0;
      for (int i=0; i<pathSize; i++) {
        if (arr[i] != elem) {
          try {
            newArr[count] = arr[i];
            count++;
          } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e.toString());
            return arr;
          }
        }
      }
    } else{
      newArr[0] = arr[0];
      int count = 1;
      for (int i=1; i<pathSize; i++) {
        if (arr[i] != elem) {
          try {
            newArr[count] = arr[i];
            count++;
          } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e.toString());
            return arr;
          }
        }
      }
    }
    return newArr;
  }

  /**
   * Helper method to add an element to an array. The method creates a new array that is one size larger than the size of
   * the original passed in array. It uses the pathSize variable to manage this. The method then uses the old array to
   * rebuild a new array and adds the new element at the end of the array.
   * @param arr: int[] object representing the path.
   * @param elem: An int value representing the element (a node) to be inserted.
   * @param pathSize: int object representing the size of the path.
   * @return: An int[] representing the newly formed array.
   */
  public static int[] addElem(int[] arr, int elem, int pathSize) {
    int[] newArr = new int[pathSize + 1];
    for (int i=0; i< pathSize; i++) {
      newArr[i] = arr[i];
    }
    newArr[pathSize] = elem;
    return newArr;
  }
}
