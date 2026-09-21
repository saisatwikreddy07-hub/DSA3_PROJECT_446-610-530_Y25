import java.util.*;

public class TSP {
    static int n;
    static int[][] graph;
    static boolean[] visited;
    static int minCost = Integer.MAX_VALUE;

    static void tsp(int current, int count, int cost) {
        if (count == n) {
            minCost = Math.min(minCost, cost + graph[current][0]);
            return;
        }

        for (int city = 0; city < n; city++) {
            if (!visited[city]) {
                visited[city] = true;
                tsp(city, count + 1, cost + graph[current][city]);
                visited[city] = false;
            }
        }
    }

    public static void main(String[] args) {
        graph = new int[][] {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        n = graph.length;
        visited = new boolean[n];

        visited[0] = true;
        tsp(0, 1, 0);

        System.out.println("Minimum TSP Cost: " + minCost);
    }
}