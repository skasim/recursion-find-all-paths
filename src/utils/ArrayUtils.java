package utils;

public class ArrayUtils {

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

  public static int[] addElem(int[] arr, int elem, int pathSize) {
    int[] newArr = new int[pathSize + 1];
    for (int i=0; i< pathSize; i++) {
      newArr[i] = arr[i];
    }
    newArr[pathSize] = elem;
    return newArr;
  }

  public static boolean allVisited(boolean[] visited) {
    boolean allVisited = true;
    for (int i=0; i<visited.length; i++) {
      if(visited[i]) {
        return false;
      }
    }
    return true;
  }
}
