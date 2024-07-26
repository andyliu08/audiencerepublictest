import java.util.*;

public class RandomUtil {

    private Random rndm = new Random();
    private int maxVal;

    RandomUtil(int maxVal) {
        this.maxVal = maxVal;
    }
    public int generateRandomNum() { return rndm.nextInt(this.maxVal - 1) + 1; }

    public int[] generateTwoRandomNums() {
        int[] rndm2 = new int[2];
        rndm2[0] = generateRandomNum();
        rndm2[1] = generateRandomNum();
        while(rndm2[0] == rndm2[1]) {
            rndm2[1] = generateRandomNum();
        }
        return rndm2;
    }
}