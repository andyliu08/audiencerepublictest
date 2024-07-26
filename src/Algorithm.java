import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Algorithm implements AlgorithmInterface {

    // record the max total weight for each vertex
    private int maxWeight = 0;

    @Override
    // Computer Diameter, eccentricity and radius
    public Algorithm computeDiameter(GraphInterface graph) {
        int radius = 0;
        int diameter = 0;
        System.out.println("The eccentricity of all the Vertex: ");
        for (int i = 1; i <= graph.getVertexNumber(); i++) {
            LinkedList<Integer> currentPath = new LinkedList<>();
            currentPath.add(i);
            maxWeight = 0;
            int eccentricity = computeEcceBacktrack(graph, i, currentPath, 0);
            if (i == 1) {
                diameter = eccentricity;
                radius = eccentricity;
            }

            radius = Math.min(radius, eccentricity);
            diameter = Math.max(diameter, eccentricity);
            System.out.println("The eccentricity of Vertex " + i + ": " + eccentricity);
        }

        System.out.println("================================");
        System.out.println("The diameter of the graph: " + diameter);
        System.out.println("The radius of the graph:   " + radius);
        System.out.println("================================");

        return this;
    }

    @Override
    // Get the shortest distance path between vertex1 and vertex2 using Dijkstra algorithm
    public void getShortestPath(GraphInterface graph, int vertex1, int vertex2) {
        // Try to get the shortest path from vertex1 to vertex2 and from vertex2 to vertex1
        String[] resForward = dijkstra(graph, vertex1, vertex2).split(",");
        String[] resBackward = dijkstra(graph, vertex2, vertex1).split(",");

        int disForward = Integer.parseInt(resForward[0]);
        int disBackward = Integer.parseInt(resBackward[0]);

        System.out.println("The shortest distance path between vertex " + vertex1 + " and vertex " + vertex2 + " :");
        if (disForward < disBackward) {
            System.out.println(resForward[1]);
            System.out.println("Total Weight: " + disForward);
        } else {
            System.out.println(resBackward[1]);
            System.out.println("Total Weight: " + disBackward);
        }
    }

    // Compute eccentricity of Vertex start using backtracking
    private int computeEcceBacktrack(GraphInterface graph, int start, LinkedList<Integer> currentPath, int dist) {
        List<String> next = graph.getNextVertexArray(start);
        // return the total max weight if no next vertex
        if (next.isEmpty())
            return maxWeight;

        for (String n : next) {
            int nextVer = Integer.parseInt(n.split(",")[0]);
            int weight = Integer.parseInt(n.split(",")[1]);

            // skip if the next vertex already visited
            if (currentPath.contains(nextVer)) continue;

            currentPath.add(nextVer);
            dist += weight;

            maxWeight = Math.max(maxWeight, dist);
            computeEcceBacktrack(graph, nextVer, currentPath, dist);

            currentPath.removeLast();
            dist -= weight;
        }

        return maxWeight;
    }

    private String reversePath(int vertex1, int vertex2, int[] predecessor) {
        StringBuilder sb = new StringBuilder();
        while (vertex1 != vertex2) {
            sb.append(" ").append(vertex2);
            vertex2 = predecessor[vertex2];
        }

        String[] sbs = sb.toString().trim().split(" ");
        StringBuilder revsb = new StringBuilder();
        for (int i = sbs.length - 1; i >= 0; i--) {
            revsb.append(" -> ").append(sbs[i]);
        }

        return revsb.toString();
    }

    private String generateShortestPath(int vertex1, int vertex2, int[] predecessor, int weight) {
        return weight + ", " + vertex1 + reversePath(vertex1, vertex2, predecessor);
    }

    private String dijkstra(GraphInterface graph, int vertex1, int vertex2) {
        int vertexCnt = graph.getVertexNumber() + 1;
        int[] dist = new int[vertexCnt];
        boolean[] visited = new boolean[vertexCnt];
        for (int i = 0; i < vertexCnt; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        int[] predecessor = new int[vertexCnt];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(vertex1);
        dist[vertex1] = 0;
        while (!queue.isEmpty()) {
            Integer vertex = queue.poll();
            if (visited[vertex]) continue;
            visited[vertex] = true;

            List<String> next = graph.getNextVertexArray(vertex);
            // skip if no next vertex
            if (next.isEmpty())
                continue;

            for (String n : next) {
                int nextVer = Integer.parseInt(n.split(",")[0]);
                int weight = Integer.parseInt(n.split(",")[1]);

                if (dist[vertex] < (dist[nextVer] - weight)) {
                    dist[nextVer] = dist[vertex] + weight;
                    predecessor[nextVer] = vertex;
                }
                queue.add(nextVer);
            }
        }

        // Return the maximum Integer for the weight if the current searching path can not be reachable
        if (dist[vertex2] == Integer.MAX_VALUE)
            return Integer.MAX_VALUE + ", ";

        return generateShortestPath(vertex1, vertex2, predecessor, dist[vertex2]);
    }
}
