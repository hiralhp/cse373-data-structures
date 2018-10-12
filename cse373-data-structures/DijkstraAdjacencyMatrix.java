public class DijkstraAdjacencyMatrix {

    static class Graph{
        int vertices;
        int matrix[][];

        public Graph(int vertex) {
            this.vertices = vertex;
            matrix = new int[vertex][vertex];
        }

        public void addEdge(int source, int destination, int weight) {
            //add edge
            matrix[source][destination]=weight;

            //add back edge for undirected graph
            matrix[destination][source] = weight;
        }

        //get the vertex with minimum distance which is not included in SPT
        int getMinimumVertex(boolean [] mst, int [] key){
            int minKey = Integer.MAX_VALUE;
            int vertex = -1;
            for (int i = 0; i <vertices ; i++) {
                if(mst[i]==false && minKey>key[i]){
                    minKey = key[i];
                    vertex = i;
                }
            }
            return vertex;
        }

        public void dijkstra_GetMinDistances(int sourceVertex){
            boolean[] spt = new boolean[vertices];
            int [] distance = new int[vertices];
            int INFINITY = Integer.MAX_VALUE;

            //Initialize all the distance to infinity
            for (int i = 0; i <vertices ; i++) {
                distance[i] = INFINITY;
            }

            //start from the vertex 0
            distance[sourceVertex] = 0;

            //create SPT
            for (int i = 0; i <vertices ; i++) {

                //get the vertex with the minimum distance
                int vertex_U = getMinimumVertex(spt, distance);

                //include this vertex in SPT
                spt[vertex_U] = true;

                //iterate through all the adjacent vertices of above vertex and update the keys
                for (int vertex_V = 0; vertex_V <vertices ; vertex_V++) {
                    //check of the edge between vertex_U and vertex_V
                    if(matrix[vertex_U][vertex_V]>0){
                        //check if this vertex 'vertex_V' already in spt and
                        // if distance[vertex_V]!=Infinity

                        if(spt[vertex_V]==false && matrix[vertex_U][vertex_V]!=INFINITY){
                            //check if distance needs an update or not
                            //means check total weight from source to vertex_V is less than
                            //the current distance value, if yes then update the distance

                            int newKey =  matrix[vertex_U][vertex_V] + distance[vertex_U];
                            if(newKey<distance[vertex_V])
                                distance[vertex_V] = newKey;
                        }
                    }
                }
            }
            //print shortest path tree
            printDijkstra(sourceVertex, distance);
        }

        public void printDijkstra(int sourceVertex, int [] key){
            System.out.println("Dijkstra Algorithm: (Adjacency Matrix)");
            for (int i = 0; i <vertices ; i++) {
                System.out.println("Source Vertex: " + sourceVertex + " to vertex " +   + i +
                        " distance: " + key[i]);
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
        graph.dijkstra_GetMinDistances(sourceVertex);
    }
}