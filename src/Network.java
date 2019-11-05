import java.util.Arrays;
import java.util.Random;

public class Network {

    private int numberOfLayers;
    private int[] sizesOfLayers;
    public double[][] biases; // [1][2] znamena 3tí neurón na prvej vrstve, 0 vrstva sú input neuróny
    public double[][][] weights; //weights[1][j][k] odpoveda vahe a hrane medzi j-tym neuronom na 3 srstve a k-tym neuronom 2 vrstvy

    public Network(int[] sizes){
        numberOfLayers = sizes.length;
        sizesOfLayers = sizes;
        initBiases();
        initWeights();
    }

    public void initBiases() {
        biases = new double[numberOfLayers - 1][];
        Utils.init2DMatrix(biases, sizesOfLayers);

        Random generator = new Random();
        //Arrays.setAll( biases, i -> { return generator.nextDouble();}); guess this is prohibited
        for (int i = 0; i < numberOfLayers - 1; i++){
            for (int j = 0; j < biases[i].length; j++){
                biases[i][j] = generator.nextDouble();
            }
        }
    }

    public void initWeights() {
        weights = new double[numberOfLayers][][];
        Utils.init3DMatrix(weights, sizesOfLayers);

        Random generator = new Random();

        for (int i = 0; i < numberOfLayers - 1; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = generator.nextDouble();
                }
            }
        }
    }

    // no idea what this does
    public double[] feedForward(double[] array) {
        return array;
    }

    public void sgd(int[][] trainingData, int epochs, int miniBatchSize, double eta) {
        int n = trainingData.length;
        for (int i = 0; i < epochs; i++) {
            Utils.shuffleArray(trainingData);
            var miniBatches = createMiniBatches(trainingData, miniBatchSize);
            for (int j = 0; j < miniBatches.length; j++) {
                updateMiniBatch(miniBatches[j], miniBatchSize, eta);
            }
            //TODO print + evaluate
        }
    }

    // update network weight ans biases by applying gradient descent using back propagation
    // to single mini batch, minibatch is set of tuples(x,y) and eta
    private void updateMiniBatch(int[][] minibatch, int miniBatchSize, double eta){
        var matrix_b = Utils.createZero2DMatrix(sizesOfLayers);
        var matrix_w = Utils.createZero3DMatrix(sizesOfLayers);

        //minibatch is 2D array[number of pics][pixes in pics]

        //init arrays for delta, returned by
        var delta_b = new double[numberOfLayers][];
        var delta_w = new  double[numberOfLayers][][];

        //update delta_s using backprop TODO

        //update of biases and weights
        matrix_b = Utils.sum2DMatrices(matrix_b, delta_b);
        matrix_w = Utils.sum3DMatrices(matrix_w, delta_w);

        updateBiases(matrix_b, miniBatchSize, eta);
        updateWeights(matrix_w, miniBatchSize, eta);

    }

    private void updateBiases(double[][] matrix_b, int miniBatchLen, double eta) {
        double constant = -1 * (eta / miniBatchLen); // -1*, so that I can sum matrices later, and not to substract
        Utils.multiply2DMatrix(matrix_b, constant); //matrix_b is updated
        this.biases = Utils.sum2DMatrices(this.biases, matrix_b);

    }

    private void updateWeights(double[][][] matrix_w, int miniBatchLen, double eta) {
        double constant = -1 * (eta / miniBatchLen); // -1*, so that I can sum matrices later, and not to substract
        Utils.multiply3DMatrix(matrix_w, constant); //matrix_b is updated
        this.weights = Utils.sum3DMatrices(this.weights, matrix_w);

    }

    private int[][][] createMiniBatches(int[][] trainingSet, int miniBatchSize){
        var miniBatches = new int[trainingSet.length / miniBatchSize][miniBatchSize][];
        int startPos = 0;
        int endPos = miniBatchSize;
        for (int i = 0; i < miniBatchSize; i ++) {
                miniBatches[i] = Arrays.copyOfRange(trainingSet, startPos, endPos);
                startPos += miniBatchSize;
                endPos += miniBatchSize;
        }
        return miniBatches;
    }

}