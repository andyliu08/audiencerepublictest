public interface AlgorithmInterface {

    // Computer Diameter, eccentricity and radius
    AlgorithmInterface computeDiameter(GraphInterface graph);

    // Get the shortest distance path between vertex1 and vertex2 using Dijkstra algorithm
    void getShortestPath(GraphInterface graph, int vertex1, int vertex2);

}
