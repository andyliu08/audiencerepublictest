import java.util.*;

public class GraphHashMap implements GraphInterface {
    // Key is the index of fromVertex and Value is a key value pair <toVertex, weight>
    private HashMap<Integer, HashMap<Integer, Integer>> graph;

    private int vertexNumber;
    private int edgeNumber;

    // Generate the random value for weight
    private RandomUtil rndmWeight;

    // Generate the random vertex index
    private RandomUtil rndmVertex;

    // Max weight can be set
    final int maxWeightSetting = 5;

    // The Algorithm to compute the graph
    private AlgorithmInterface alg = null;

    GraphHashMap(int vertexNumber, int edgeNumber) {
        this.vertexNumber = vertexNumber;
        this.edgeNumber = edgeNumber;
        this.rndmWeight = new RandomUtil(maxWeightSetting);
        this.rndmVertex = new RandomUtil(this.vertexNumber);
        this.graph = new HashMap<>();
        for (int i = 1; i <= this.vertexNumber; i++) {
            this.graph.put(i, new HashMap<>());
        }
    }

    @Override
    // Generate a simple connected direct Graph with vertexNumber and edgeNumber
    public GraphHashMap createConnectedGraph() {
        List<Integer> vertexList = generateShuffleVertex();

        // create a minimum connected direct Graph based on the shuffle vertex index
        int edgeNumberCreated = createMiniConnectedGraph(vertexList);
        // add more edges until the edge number meet the expectation
        addRestEdges(vertexList, this.edgeNumber - edgeNumberCreated);

        printGraph();
        return this;
    }

    @Override
    public int getVertexNumber() {
        return this.vertexNumber;
    }

    @Override
    // Get the next level of all the vertex with weight staring from vertex start
    public List<String> getNextVertexArray(int start) {
        List<String> res = new ArrayList<>();
        HashMap<Integer, Integer> next = graph.get(start);
        // return the total max weight if no next vertex
        if (next == null || next.isEmpty())
            return res;

        for (Map.Entry<Integer, Integer> entry : next.entrySet()) {
            // add the string nextVertexIndex, weight
            res.add(entry.getKey().toString() + "," + entry.getValue().toString());
        }
        return res;
    }

    @Override
    // Set the Algorithm used to compute graph
    public GraphInterface setAlgorithm(AlgorithmInterface alg) {
        this.alg = alg;
        return this;
    }

    @Override
    public GraphInterface computeDiameter() {
        if(this.alg == null) {
            System.out.println("Algorithm is missing, please call setAlgorithm to set it");
            return this;
        }

        this.alg.computeDiameter(this);
        return this;
    }

    @Override
    // Get the shortest distance path between vertex1 and vertex2 using Dijkstra algorithm
    public GraphInterface computeShortestPath(int vertex1, int vertex2) {
        if(this.alg == null) {
            System.out.println("Algorithm is missing, please call setAlgorithm to set it");
            return this;
        }

        this.alg.getShortestPath(this, vertex1, vertex2);
        return this;
    }

    private List<Integer> generateShuffleVertex() {
        List<Integer> shuffleVertexList = new ArrayList<>();
        for (int i = 1; i <= this.vertexNumber; i++)
            shuffleVertexList.add(i);
        Collections.shuffle(shuffleVertexList);
        return shuffleVertexList;
    }

    private int createMiniConnectedGraph(List<Integer> list) {
        HashSet<Integer> src = new HashSet<>(list);
        HashSet<Integer> dst = new HashSet<>();
        int cur = list.get(0);
        int edgesNum = 0;
        src.remove(cur);
        dst.add(cur);
        int index = 1;
        while (!src.isEmpty()) {
            int neighbour = list.get(index);
            if (!dst.contains(neighbour)) {
                this.graph.get(cur).putIfAbsent(neighbour, this.rndmWeight.generateRandomNum());
                edgesNum++;
                src.remove(neighbour);
                dst.add(neighbour);
            }
            cur = neighbour;
            index++;
        }
        return edgesNum;
    }

    private void addRestEdges(List<Integer> vertexList, int restEdges) {
        // Add random edges if adding less than 60% of the max allowed edges
        if (this.edgeNumber <= this.vertexNumber * (this.vertexNumber - 1) * 0.6) {
            addRandomEdges(restEdges);
        } else {
            // add edges following the vertexList order
            addEdges(restEdges);
        }
    }

    private void addRandomEdges(int restEdges) {
        while (restEdges > 0) {
            int[] rndm2 = this.rndmVertex.generateTwoRandomNums();
            int cur = rndm2[0];
            int next = rndm2[1];
            if (cur == next)
                continue;
            if (!this.graph.get(cur).containsKey(next)) {
                this.graph.get(cur).putIfAbsent(next, this.rndmWeight.generateRandomNum());
                restEdges--;
            }
        }
    }

    private void addEdges(int restEdges) {
        for (int i = 1; i <= this.vertexNumber; i++) {
            for (int j = 1; j <= this.vertexNumber; j++) {
                if (i == j) continue;
                if (!this.graph.get(i).containsKey(j)) {
                    this.graph.get(i).putIfAbsent(j, this.rndmWeight.generateRandomNum());
                    restEdges--;
                    if (restEdges == 0)
                        return;
                }
            }
        }
    }

    private void printGraph() {
        System.out.println("Print out the randomly generated graph: ");
        System.out.println("{ ");
        for (int i = 1; i <= this.vertexNumber; i++) {
            System.out.print("   :" + i + "  [ ");
            if (this.graph.get(i).isEmpty()) {
                System.out.println("],");
            } else {
                for (Map.Entry<Integer, Integer> entry : this.graph.get(i).entrySet()) {
                    System.out.print("(:" + entry.getKey() + " " + entry.getValue() + ") ");
                }

                if (i == this.vertexNumber) {
                    System.out.println("]");
                } else {
                    System.out.println("],");
                }

            }
        }
        System.out.println("}");
        System.out.println("================================");
    }
}