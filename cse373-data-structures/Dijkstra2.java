import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
class Graph{
   int vertices;
   int matrix[][];

   public Graph(int vertex) {
      this.vertices = vertex;
      matrix = new int[vertex][vertex];
   }
}
class Vertex implements Comparable<Vertex>
{
   public final String name;
   public Edge[] adjacencies;
   public double minDistance = Double.POSITIVE_INFINITY;
   public Vertex previous;
   public Vertex(String argName) { name = argName; }
   public String toString() { 
      return name; }
   public int compareTo(Vertex other)
   {
      return Double.compare(minDistance, other.minDistance);
   }

}


class Edge
{
   public final Vertex target;
   public final double weight;
   public Edge(Vertex argTarget, double argWeight)
   { target = argTarget; weight = argWeight; }
}

public class Dijkstra2
{
   public static void computePaths(Vertex source)
   {
      source.minDistance = 0.;
      PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
      vertexQueue.add(source);
   
      while (!vertexQueue.isEmpty()) {
         Vertex u = vertexQueue.poll();
      
            // Visit each edge exiting u
         for (Edge e : u.adjacencies)
         {
            Vertex v = e.target;
            double weight = e.weight;
            double distanceThroughU = u.minDistance + weight;
            if (distanceThroughU < v.minDistance) {
               vertexQueue.remove(v);
            
               v.minDistance = distanceThroughU ;
               v.previous = u;
               vertexQueue.add(v);
            }
         }
      }
   }

   public static List<Vertex> getShortestPathTo(Vertex target)
   {
      List<Vertex> path = new ArrayList<Vertex>();
      for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
         path.add(vertex);
   
      Collections.reverse(path);
      return path;
   }

   public static void main(String[] args)
   {
      int[][] maze = new int[4][4];
      maze[0][0] = 0;
      maze[0][1] = 4;
      maze[0][2] = 3;
      maze[0][3] = 2;
      
      maze[1][0] = 5;
      maze[1][1] = 2;
      maze[1][2] = 6;
      maze[1][3] = 1;
      
      maze[2][0] = 2;
      maze[2][1] = 3;
      maze[2][2] = 10;
      maze[2][3] = 4;
      
      maze[3][0] = 1;
      maze[3][1] = 9;
      maze[3][2] = 8;
      maze[3][3] = 0;
      
   
      int vertices = maze.length * maze[0].length;
      Graph graph = new Graph(vertices);
      int ammount = maze.length * maze[0].length;
      int lastRow = ammount - maze[0].length;
      int lastCol = maze[0].length - 1;
      int skip = maze[0].length;
      int row = maze.length - 1;
      int col = maze[0].length - 1;
      int sourceVertex = 0;
   
        // mark all the vertices 
      Vertex A = new Vertex("A");
      Vertex B = new Vertex("B");
      Vertex D = new Vertex("D");
      Vertex F = new Vertex("F");
      Vertex K = new Vertex("K");
      Vertex J = new Vertex("J");
      Vertex M = new Vertex("M");
      Vertex O = new Vertex("O");
      Vertex P = new Vertex("P");
      Vertex R = new Vertex("R");
        //Vertex Z = new Vertex("Z");
      Vertex  = new Vertex("0");
   
        // set the edges and weight
      for(int i = 0; i < 1; i++){
         i.adjacencies = new Edge[]{ new Edge(M, 8) };
      
      }
      A.adjacencies = new Edge[]{ new Edge(M, 8) };
      B.adjacencies = new Edge[]{ new Edge(D, 11) };
      D.adjacencies = new Edge[]{ new Edge(B, 11) };
      F.adjacencies = new Edge[]{ new Edge(K, 23) };
      K.adjacencies = new Edge[]{ new Edge(O, 40) };
      J.adjacencies = new Edge[]{ new Edge(K, 25) };
      M.adjacencies = new Edge[]{ new Edge(R, 8) };
      O.adjacencies = new Edge[]{ new Edge(K, 40) };
      P.adjacencies = new Edge[]{ new Edge(Z, 18) };
      R.adjacencies = new Edge[]{ new Edge(P, 15) };
      Z.adjacencies = new Edge[]{ new Edge(P, 18) };
   
   
      computePaths(A); // run Dijkstra
      System.out.println("Distance to " + Z + ": " + Z.minDistance);
      List<Vertex> path = getShortestPathTo(Z);
      System.out.println("Path: " + path);
   }
}