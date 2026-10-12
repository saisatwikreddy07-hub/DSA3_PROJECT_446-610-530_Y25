import java.util.*;

/**
 * Interactive Computational Complexity Learning Platform
 * Compile: javac InteractiveComplexityPlatform.java
 * Run:     java InteractiveComplexityPlatform
 */
public class InteractiveComplexityPlatform {
    static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println(" INTERACTIVE COMPUTATIONAL COMPLEXITY PLATFORM");
        System.out.println("==============================================");
        while (true) {
            System.out.println("\nMAIN MENU");
            System.out.println("1. Learn P, NP, NP-Complete and NP-Hard");
            System.out.println("2. Solve 0/1 Knapsack (Dynamic Programming)");
            System.out.println("3. Graph Traversal (BFS and DFS)");
            System.out.println("4. Vertex Cover (Greedy Approximation)");
            System.out.println("5. Quick Quiz");
            System.out.println("0. Exit");
            int choice = number("Choose an option: ");
            switch (choice) {
                case 1 -> explainComplexity();
                case 2 -> knapsack();
                case 3 -> graphTraversal();
                case 4 -> vertexCover();
                case 5 -> quiz();
                case 0 -> {
                    System.out.println("Thank you for using the platform!");
                    return;
                }
                default -> System.out.println("Invalid choice. Enter 0 to 5.");
            }
        }
    }

    static void explainComplexity() {
        System.out.println("\n--- COMPLEXITY CLASSES ---");
        System.out.println("P: Decision problems solvable in polynomial time.");
        System.out.println("   Example: checking whether a path exists between two graph vertices.");
        System.out.println("NP: Proposed solutions can be verified in polynomial time.");
        System.out.println("   Example: verifying a proposed solution to a puzzle.");
        System.out.println("NP-Complete: Problems in NP that are at least as hard as every problem in NP.");
        System.out.println("   Examples: 3-SAT and the decision version of Hamiltonian Cycle.");
        System.out.println("NP-Hard: At least as hard as NP problems; not necessarily in NP.");
        System.out.println("   Example: the optimization version of Travelling Salesman Problem.");
        System.out.println("Note: It is not known whether P = NP.");
    }

    static void knapsack() {
        System.out.println("\n--- 0/1 KNAPSACK ---");
        int n = boundedNumber("Number of items (1-20): ", 1, 20);
        int[] weight = new int[n], value = new int[n];
        for (int i = 0; i < n; i++) {
            weight[i] = boundedNumber("Weight of item " + (i + 1) + ": ", 1, 10000);
            value[i] = boundedNumber("Value of item " + (i + 1) + ": ", 0, 10000);
        }
        int capacity = boundedNumber("Bag capacity: ", 1, 10000);
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int c = 0; c <= capacity; c++) {
                dp[i][c] = dp[i - 1][c];
                if (weight[i - 1] <= c) {
                    dp[i][c] = Math.max(dp[i][c],
                            value[i - 1] + dp[i - 1][c - weight[i - 1]]);
                }
            }
        }

        System.out.println("Maximum value = " + dp[n][capacity]);
        List<Integer> chosen = new ArrayList<>();
        int c = capacity;
        for (int i = n; i > 0; i--) {
            if (dp[i][c] != dp[i - 1][c]) {
                chosen.add(i);
                c -= weight[i - 1];
            }
        }
        Collections.reverse(chosen);
        System.out.println("Selected item numbers = " + chosen);
        System.out.println("Time/space complexity: O(n x capacity).");
        System.out.println("The algorithm compares taking each item with leaving it.");
    }

    static void graphTraversal() {
        System.out.println("\n--- BFS AND DFS ---");
        int n = boundedNumber("Number of vertices (1-30): ", 1, 30);
        int m = boundedNumber("Number of edges (0-100): ", 0, 100);
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int u = boundedNumber("Edge " + (i + 1) + " first vertex (1-" + n + "): ", 1, n) - 1;
            int v = boundedNumber("Edge " + (i + 1) + " second vertex (1-" + n + "): ", 1, n) - 1;
            if (u == v) {
                System.out.println("Self-loop not accepted; enter this edge again.");
                i--;
            } else {
                graph.get(u).add(v);
                graph.get(v).add(u);
            }
        }
        for (List<Integer> list : graph) Collections.sort(list);
        int start = boundedNumber("Starting vertex (1-" + n + "): ", 1, n) - 1;
        System.out.println("BFS visit order: " + bfs(graph, start));
        boolean[] visited = new boolean[n];
        List<Integer> order = new ArrayList<>();
        dfs(graph, start, visited, order);
        System.out.println("DFS visit order: " + order);
        System.out.println("Both have O(V + E) time complexity with adjacency lists.");
    }

    static List<Integer> bfs(List<List<Integer>> graph, int start) {
        boolean[] seen = new boolean[graph.size()];
        Queue<Integer> q = new ArrayDeque<>();
        List<Integer> order = new ArrayList<>();
        seen[start] = true;
        q.add(start);
        while (!q.isEmpty()) {
            int u = q.remove();
            order.add(u + 1);
            for (int v : graph.get(u)) {
                if (!seen[v]) {
                    seen[v] = true;
                    q.add(v);
                }
            }
        }
        return order;
    }

    static void dfs(List<List<Integer>> graph, int u, boolean[] seen, List<Integer> order) {
        seen[u] = true;
        order.add(u + 1);
        for (int v : graph.get(u)) if (!seen[v]) dfs(graph, v, seen, order);
    }

    static void vertexCover() {
        System.out.println("\n--- GREEDY VERTEX COVER ---");
        int n = boundedNumber("Number of vertices (1-30): ", 1, 30);
        int m = boundedNumber("Number of edges (0-100): ", 0, 100);
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int u = boundedNumber("Edge " + (i + 1) + " first vertex: ", 1, n) - 1;
            int v = boundedNumber("Edge " + (i + 1) + " second vertex: ", 1, n) - 1;
            if (u == v) {
                System.out.println("Self-loop not accepted; enter this edge again.");
                i--;
            } else edges.add(new int[]{u, v});
        }

        boolean[] covered = new boolean[edges.size()];
        Set<Integer> cover = new TreeSet<>();
        int remaining = edges.size();
        while (remaining > 0) {
            int best = -1, bestDegree = 0;
            for (int v = 0; v < n; v++) {
                int degree = 0;
                for (int e = 0; e < edges.size(); e++)
                    if (!covered[e] && (edges.get(e)[0] == v || edges.get(e)[1] == v)) degree++;
                if (degree > bestDegree) { bestDegree = degree; best = v; }
            }
            if (best == -1) break;
            cover.add(best + 1);
            for (int e = 0; e < edges.size(); e++) {
                if (!covered[e] && (edges.get(e)[0] == best || edges.get(e)[1] == best)) {
                    covered[e] = true;
                    remaining--;
                }
            }
        }
        System.out.println("Greedy vertex cover = " + cover);
        System.out.println("A vertex cover includes at least one endpoint of every edge.");
        System.out.println("This greedy method is a heuristic; it may not find the minimum cover.");
    }

    static void quiz() {
        String[] questions = {
            "Which class contains problems solvable in polynomial time?",
            "Which technique stores answers to overlapping subproblems?",
            "Which data structure is used by BFS?"
        };
        String[][] options = {
            {"A. P", "B. NP-Hard", "C. Exponential"},
            {"A. Random guessing", "B. Dynamic programming", "C. Bubble sort"},
            {"A. Queue", "B. Stack only", "C. Tree"}
        };
        char[] answers = {'A', 'B', 'A'};
        int score = 0;
        System.out.println("\n--- QUICK QUIZ ---");
        for (int i = 0; i < questions.length; i++) {
            System.out.println("\n" + questions[i]);
            for (String option : options[i]) System.out.println(option);
            String answer = line("Your answer (A/B/C): ").trim().toUpperCase();
            if (!answer.isEmpty() && answer.charAt(0) == answers[i]) {
                System.out.println("Correct!");
                score++;
            } else System.out.println("Incorrect. Correct answer: " + answers[i]);
        }
        System.out.println("Final score: " + score + "/" + questions.length);
    }

    static int number(String prompt) {
        while (true) {
            try { return Integer.parseInt(line(prompt).trim()); }
            catch (NumberFormatException e) { System.out.println("Enter a whole number."); }
        }
    }

    static int boundedNumber(String prompt, int min, int max) {
        while (true) {
            int x = number(prompt);
            if (x >= min && x <= max) return x;
            System.out.println("Enter a number from " + min + " to " + max + ".");
        }
    }

    static String line(String prompt) {
        System.out.print(prompt);
        return in.nextLine();
    }
}
