import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class KnuthProblem {
  private static final BigDecimal EPSILON = new BigDecimal("0.00000001");
private static final int MAX_DEPTH = 0;

  private static class Node {
    BigDecimal value;
    String operation;
    Node parent;

    Node(BigDecimal value, String operation, Node parent) {
      this.value = value;
      this.operation = operation;
      this.parent = parent;
    }
  }

  public static void main(String[] args) {
    // Read the target number and search method from the user
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the target number: ");
    BigDecimal target = scanner.nextBigDecimal();
    System.out.print("Enter the search method (BFS or IDS): ");
    String searchMethod = scanner.next();
    System.out.print("Enter the time limit (in seconds): ");
    int timeLimit = scanner.nextInt();

    if (searchMethod.equals("BFS")) {


      // Initialize the data structures for the breadth-first search
      Deque<Node> queue = new ArrayDeque<>();
      Set<BigDecimal> visited = new HashSet<>();

      // Add the initial node to the queue
      queue.add(new Node(new BigDecimal(4), "", null));

      // Start the timer
      long startTime = System.currentTimeMillis();

      // Perform the breadth-first search
      while (!queue.isEmpty()) {
        Node node = queue.poll();
        BigDecimal value = node.value;

        // Check if the value is the target number
        if (value.equals(target)) {
          // Print the solution
          Deque<String> solution = new ArrayDeque<>();
          while (node != null) {
            solution.push(node.operation);
            node = node.parent;
          }
          System.out.println(solution);

          // Print the time taken
          long endTime = System.currentTimeMillis();
          long timeTaken = endTime - startTime;
          System.out.println("Time taken: " + timeTaken + " milliseconds");

          return;
        }
        

        // Check if the value is an integer
        if (value.subtract(value.setScale(0, RoundingMode.FLOOR)).abs().compareTo(EPSILON) <= 0) {
          // Perform the factorial operation
         BigDecimal factorial = BigDecimal.ONE;

         if (value.compareTo(new BigDecimal(200)) == -1) {
             for (BigDecimal i = BigDecimal.ONE; i.compareTo(value) <= 0; i = i.add(BigDecimal.ONE)) {
                 factorial = factorial.multiply(i);
             }
         }
         else {
             factorial = value;
         }


          queue.add(new Node(factorial, "factorial", node));
        }

        // Perform the square root operation
        BigDecimal sqrt = BigDecimal.valueOf(Math.sqrt(value.doubleValue()));
        queue.add(new Node(sqrt, "square root", node));

        // Perform the floor operation
        BigDecimal floor = value.setScale(0, RoundingMode.FLOOR);
        queue.add(new Node(floor, "floor", node));

        // Mark the value as visited
        visited.add(value);
      }

      // Print that the target number was not found
      System.out.println("Target number not found");

      // Print the time taken
      long endTime = System.currentTimeMillis();
      long timeTaken = endTime - startTime;
      System.out.println("Time taken: " + timeTaken + " milliseconds");
     }
        else if (searchMethod.equals("IDS")) {
  // Initialize a set of visited values
  Set<BigDecimal> visited = new HashSet<>();

  // Start the timer
  long startTime = System.currentTimeMillis();

  // Perform the iterative deepening search
  int depth = 0;
  while (true) {
    // Initialize the data structures for the depth-limited search
    Deque<Node> stack = new ArrayDeque<>();
    stack.add(new Node(new BigDecimal(4), "", null));

    // Perform the depth-limited search
    
    //if MAX_DEPTH is reached stop
//    if (depth == MAX_DEPTH) {
//        break;
//    }
    boolean found = false;
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      BigDecimal value = node.value;

      // Check if the value is the target number
      if (value.subtract(target).abs().compareTo(EPSILON) <= 0) {
    	  System.out.println("the value and the target are"+ value.subtract(target).abs().compareTo(EPSILON));
      
        // Print the solution
        Deque<String> solution = new ArrayDeque<>();
        while (node != null) {
          solution.push(node.operation);
          node = node.parent;
        }
        System.out.println(solution);
        found = true;
        break;
      }
      
      // Check if the value is an integer
      if (value.subtract(value.setScale(0, RoundingMode.FLOOR)).abs().compareTo(EPSILON) <= 0) {
        // Perform the factorial operation
       BigDecimal factorial = BigDecimal.ONE;

       if (value.compareTo(new BigDecimal(200)) == -1) {
           for (BigDecimal i = BigDecimal.ONE; i.compareTo(value) <= 0; i = i.add(BigDecimal.ONE)) {
               factorial = factorial.multiply(i);
           }
       }
       else {
           factorial = value;
       }


       stack.add(new Node(factorial, "factorial", node));
      }


      // Perform the square root operation
      BigDecimal sqrt = BigDecimal.valueOf(Math.sqrt(value.doubleValue()));
      stack.add(new Node(sqrt, "square root", node));

      // Perform the floor operation
      BigDecimal floor = value.setScale(0, RoundingMode.FLOOR);
      stack.add(new Node(floor, "floor", node));

      // Mark the value as visited
      visited.add(value);
    }

    // Check if a solution was found
    if (found) {
      // Print the time taken
      long endTime = System.currentTimeMillis();
      long timeTaken = endTime - startTime;
      System.out.println("Time taken: " + timeTaken + " milliseconds");

      // Return from the method
      return;
      
    }

    // Increase the depth limit and reset the visited set
    depth++;
    visited.clear();
  }
        }else {
            System.out.println("Invalid search method");
          }

      // Print that the target number was not found
      System.out.println("Target number not found");

     }
}
