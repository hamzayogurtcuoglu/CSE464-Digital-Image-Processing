import java.io.*;
import java.util.Scanner;

public class NearestNeighbour {
    private int featureSize = 256;
    private int trainSamples = 0;
    private int testSamples = 0;
    private int classCount = 0;

    NearestNeighbour(LBP localBinaryPatterns) throws IOException {
        try {

            Scanner scanner1 = new Scanner(new File("images/classes.txt"));
            Scanner scanner2 = new Scanner(new File("images/test.txt"));
            Scanner scanner3 = new Scanner(new File("images/train.txt"));

            classCount = Integer.parseInt(scanner1.nextLine());
            testSamples = Integer.parseInt(scanner2.nextLine());
            trainSamples = Integer.parseInt(scanner3.nextLine());

            double[][] trainFeatures = new double[trainSamples][featureSize];
            String[] trainLabels = new String[trainSamples];
            double[][] testFeatures = new double[testSamples][featureSize];
            String[] testLabels = new String[testSamples];
            int[] numberOfSamplesPerClass = new int[classCount];
            String[] results = new String[testSamples];
            String s = null;

            int sample;
            String[] tokens;

            int trainBegin = 4800;

            int i;
            for (sample = 0; sample<trainSamples; ++sample) {
                s = scanner3.nextLine();
                tokens = s.split(" ");

                trainFeatures[sample] = localBinaryPatterns.imageFeatures[trainBegin];

                trainBegin++;
                trainLabels[sample] = tokens[1];
            }

            s = null;
            int testBegin = 0;
            for (sample = 0;  sample<testSamples; ++sample) {
                s = scanner2.nextLine();
                tokens = s.split(" ");

                testFeatures[sample] = localBinaryPatterns.imageFeatures[testBegin];

                testBegin++;
                testLabels[sample] = tokens[1];
                ++numberOfSamplesPerClass[Integer.parseInt(testLabels[sample])];
            }


            double[] max = new double[featureSize];

            for (i = 0; i < max.length; ++i) {
                max[i] = 0.0D;
            }

            double[] min = new double[featureSize];

            for (i = 0; i < max.length; ++i) {
                min[i] = 1.7976931348623157E308D;
            }

            // her feature pixel için max ve minleri dolduruyor.

            for (i = 0; i < featureSize; ++i) {
                for (int j = 0; j < trainSamples; ++j) {
                    if (trainFeatures[j][i] > max[i]) {
                        max[i] = trainFeatures[j][i];
                    }

                    if (trainFeatures[j][i] < min[i]) {
                        min[i] = trainFeatures[j][i];
                    }
                }
            }

            for (int j = 0; j < trainFeatures.length; ++j) {
                for (i = 0; i < featureSize; ++i) {
                    if (max[i] - min[i] != 0.0D) {
                        trainFeatures[j][i] = (trainFeatures[j][i] - min[i]) / (max[i] - min[i]);
                    }
                }
            }

            for (int j = 0; j < testFeatures.length; ++j) {
                for (i = 0; i < featureSize; ++i) {
                    if (max[i] - min[i] != 0.0D) {
                        testFeatures[j][i] = (testFeatures[j][i] - min[i]) / (max[i] - min[i]);
                    }
                }
            }

            for (i = 0; i < testSamples; ++i) {
                double minDist = 1.7976931348623157E308D;

                for (int j = 0; j < trainSamples; ++j) {
                    double dist = L2Distance.euclideanNormalized(testFeatures[i], trainFeatures[j]);
                    if (dist < minDist) {
                        minDist = dist;
                        results[i] = trainLabels[j];
                    }
                }
            }

            double[] accuracies = new double[classCount];

            for (i = 0; i < testLabels.length; ++i) {
                if (testLabels[i].equals(results[i])) {
                    ++accuracies[Integer.parseInt(testLabels[i])];
                }
            }

            for (i = 0; i < classCount; ++i) {
                accuracies[i] /= (double) numberOfSamplesPerClass[i];

                System.err.println("Accuracy of class " + i + " is " + accuracies[i] * 100.0D + "%");
                if (i == 0){
                    System.err.println("Accuracy of class " + i + " is " + accuracies[i] * 100.0D + "%");
                }
            }
        }catch (Exception var23) {
            var23.printStackTrace();
        }

    }
}
