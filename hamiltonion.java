public class Hamiltonian {

    static boolean isSafe(
            int v, int[][] graph,
            int[] path, int pos) {

        if (graph[path[pos - 1]][v] == 0)
            return false;

        for (int i = 0; i < pos; i++) {
            if (path[i] == v)
                return false;
        }

        return true;
    }

    static boolean solve(
            int[][] graph,
            int[] path, int pos) {

        if (pos == graph.length) {
            return graph[path[pos - 1]][path[0]] == 1;
        }

        for (int v = 1; v < graph.length; v++) {

            if (isSafe(v, graph, path, pos)) {

                path[pos] = v;

                if (solve(graph, path, pos + 1))
                    return true;

                path[pos] = -1;
            }
        }

        return false;
    }
}