import java.util.*;

public class VertexCover {

    public static Set<Integer> vertexCover(
            List<int[]> edges) {

        Set<Integer> cover = new HashSet<>();

        while (!edges.isEmpty()) {

            int[] edge = edges.remove(0);

            int u = edge[0];
            int v = edge[1];

            cover.add(u);
            cover.add(v);

            edges.removeIf(
                e -> e[0] == u || e[1] == u ||
                     e[0] == v || e[1] == v
            );
        }

        return cover;
    }

    public static void main(String[] args) {

        List<int[]> edges = new ArrayList<>();

        edges.add(new int[]{0, 1});
        edges.add(new int[]{0, 2});
        edges.add(new int[]{1, 3});
        edges.add(new int[]{2, 3});

        Set<Integer> result = vertexCover(edges);

        System.out.println("Vertex Cover: " + result);
    }
}