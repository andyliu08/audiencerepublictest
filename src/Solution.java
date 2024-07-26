
public class Solution {
    public static void main(String argv[]) {
        String cmdParam = "graph -N 6 -S 18";
        String[] parameters = cmdParam.split(" ");
        int vertexNum, edgeNum;

        try {
            vertexNum = Integer.parseInt(parameters[2]);
            edgeNum = Integer.parseInt(parameters[4]);
        } catch (Exception e) {
            System.out.println("Error: Please check whether you command is right format");
            System.out.println("Example: graph -N 8 -S 15");
            return;
        }

        if(edgeNum < vertexNum - 1 || edgeNum > vertexNum * (vertexNum - 1)) {
            System.out.println("Error: S should be in the range between " + (vertexNum-1) + " (inclusive) to " + (vertexNum*(vertexNum-1)) + " (inclusive)");
            return;
        }

        int[] vertex = (new RandomUtil(vertexNum)).generateTwoRandomNums();
        (new GraphHashMap(vertexNum, edgeNum)).createConnectedGraph().setAlgorithm(new Algorithm()).computeDiameter().computeShortestPath(vertex[0], vertex[1]);
    }
}
