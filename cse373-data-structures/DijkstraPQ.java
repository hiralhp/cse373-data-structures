import javafx.util.Pair;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class DijkstraPQ {
   static class Edge {
      int source;
      int destination;
      int weight;
   
      public Edge(int source, int destination, int weight) {
         this.source = source;
         this.destination = destination;
         this.weight = weight;
      }
   }

   static class Graph {
      int vertices;
      LinkedList<Edge>[] adjacencylist;
   
      Graph(int vertices) {
         this.vertices = vertices;
         adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
         for (int i = 0; i <vertices ; i++) {
            adjacencylist[i] = new LinkedList<>();
         }
      }
   
      public void addEdge(int source, int destination, int weight) {
         Edge edge = new Edge(source, destination, weight);
         adjacencylist[source].addFirst(edge);
      
         edge = new Edge(destination, source, weight);
         adjacencylist[destination].addFirst(edge); //for undirected graph
      }
   
      public void dijkstra_GetMinDistances(int sourceVertex){
      
         boolean[] SPT = new boolean[vertices];
            //distance used to store the distance of vertex from a source
         int [] distance = new int[vertices];
      
            //Initialize all the distance to infinity
         for (int i = 0; i <vertices ; i++) {
            distance[i] = Integer.MAX_VALUE;
         }
            //Initialize priority queue
            //override the comparator to do the sorting based keys
         PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(vertices, 
               new Comparator<Pair<Integer, Integer>>() {
                  @Override
                  public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                    //sort using distance values
                     int key1 = p1.getKey();
                     int key2 = p2.getKey();
                     return key1-key2;
                  }
               });
            //create the pair for for the first index, 0 distance 0 index
         distance[0] = 0;
         Pair<Integer, Integer> p0 = new Pair<>(distance[0],0);
            //add it to pq
         pq.offer(p0);
      
            //while priority queue is not empty
         while(!pq.isEmpty()){
                //extract the min
            Pair<Integer, Integer> extractedPair = pq.poll();
         
                //extracted vertex
            int extractedVertex = extractedPair.getValue();
            if(SPT[extractedVertex]==false) {
               SPT[extractedVertex] = true;
            
                    //iterate through all the adjacent vertices and update the keys
               LinkedList<Edge> list = adjacencylist[extractedVertex];
               for (int i = 0; i < list.size(); i++) {
                  Edge edge = list.get(i);
                  int destination = edge.destination;
                        //only if edge destination is not present in mst
                  if (SPT[destination] == false) {
                            ///check if distance needs an update or not
                            //means check total weight from source to vertex_V is less than
                            //the current distance value, if yes then update the distance
                     int newKey =  distance[extractedVertex] + edge.weight ;
                     int currentKey = distance[destination];
                     if(currentKey>newKey){
                        Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                        pq.offer(p);
                        distance[destination] = newKey;
                     }
                  }
               }
            }
         }
            //print Shortest Path Tree
         printDijkstra(distance, sourceVertex);
      }
   
      public void printDijkstra(int[] distance, int sourceVertex){
         System.out.println("Dijkstra Algorithm: (Adjacency List + Priority Queue)");
         for (int i = 0; i <vertices ; i++) {
            System.out.println("Source Vertex: " + sourceVertex + " to vertex " +   + i +
                        " distance: " + distance[i]);
         }
      }
   
   }
   public static void main(String[] args) {
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
      for (int i = ammount - 1; i >= 0; i--) {
         
         if(i < lastRow){
            graph.addEdge(i, i+skip, maze[row+1][col]);
           
         } 
         if(i%maze[0].length != lastCol){
            graph.addEdge(i, i+1, maze[row][col+1]);
         }
         
         if(col == 0){
            col = maze[0].length - 1;
            row--;
         } 
         else {
            col--;
         }
      
      }
      graph.dijkstra_GetMinDistances(0);
   }

}