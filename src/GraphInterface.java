import java.util.List;

public interface GraphInterface {
    // get the number of vertex
    int getVertexNumber();

    // retrive all To-Vertexs with the weight from the start vertex
    // Return the array of string ["toVertex, weight"]
    List<String> getNextVertexArray(int start);

    // Generate a simple connected direct Graph with the assigned vertexNumber and edgeNumber
    GraphInterface createConnectedGraph();

    // Add the algorithm to compute the graph
    GraphInterface setAlgorithm(AlgorithmInterface alg);

    // Computer Diameter, eccentricity and radius
    GraphInterface computeDiameter();

    // Retrieve the shortest path between vertex1 and vertex2
    GraphInterface computeShortestPath(int vertex1, int vertex2);
}
