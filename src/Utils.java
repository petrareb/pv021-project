import java.util.Arrays;
import java.util.Random;

public class Utils {

    public static double[] sigmoid (double[] array) {

        var result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = 1.0 / (1.0 + Math.exp(array[i]));
        }
        return result;
    }

    public static void shuffleArray(int[][] array) //shuffles array
    {
        int index;
        int[] temp;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public static void init2DMatrix(double[][] matrix, int[] sizeOfLayers){
        for (int i = 0; i < sizeOfLayers.length - 1; i++) {
            matrix[i] = new double[sizeOfLayers[i + 1]];
        }
    }

    public static void init3DMatrix(double[][][] matrix, int[] sizeOfLayers){
        for (int i = 0; i < sizeOfLayers.length - 1; i++) {
            int from = sizeOfLayers[i];
            int to = sizeOfLayers[i + 1];
            matrix[i] = new double[to][from];
        }
    }

    public static double[][] createZero2DMatrix(int[] sizesOfLayers){
        double[][] matrix = new double[sizesOfLayers.length][];
        init2DMatrix(matrix, sizesOfLayers);
        for (int i = 0; i < sizesOfLayers.length - 1; i++){
            Arrays.fill(matrix[i], 0);
        }
        return matrix;
    }

    public static double[][][] createZero3DMatrix(int[] sizesOfLayers){
        double[][][] zeroMatrix = new double[sizesOfLayers.length][][];
        init3DMatrix(zeroMatrix, sizesOfLayers);
        for (int i = 0; i < sizesOfLayers.length - 1; i++){
            for (int j = 0; j < sizesOfLayers[i+1]; j++){
                Arrays.fill(zeroMatrix[i][j], 0);
            }
        }
        return zeroMatrix;
    }

    public static double[][] sum2DMatrices(double[][] first, double[][] second){
        double[][] result = new double[first.length][];
        for (int i = 0; i < first.length; i++){
            for (int j = 0; j< first[i].length; j++){
                result[i] = new double[first[i].length];
                result[i][j] = first[i][j] + second[i][j];
            }
        }
        return result;
    }

    public static double[][][] sum3DMatrices(double[][][] first, double[][][] second){
        double[][][] result = new double[first.length][][];
        for (int i = 0; i < first.length; i++){
            //result[i] = new double[first[i].length][];
            result[i] = sum2DMatrices(first[i], second[i]);
        }
        return result;
    }

    public static void multiply2DMatrix(double[][] matrix, double constant){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0 ; j < matrix[i].length; j++) {
                matrix[i][j] *= constant;
            }
        }
    }

    public static void multiply3DMatrix(double[][][] matrix, double constant){
        for (int i = 0; i < matrix.length; i++){
            multiply2DMatrix(matrix[i], constant);
        }
    }
}
