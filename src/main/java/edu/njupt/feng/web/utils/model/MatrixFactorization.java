package edu.njupt.feng.web.utils.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MatrixFactorization {
    public static void main(String args[]) {
        Double[][] dataMatrix = {{0.0,2.0,0.0,0.0,9.0,0.0,6.0,0.0,0.0,0.0},
                {0.0,0.0,7.0,0.0,0.0,3.0,0.0,5.0,0.0,0.0},
                {0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,2.0,9.0},
                {0.0,0.0,0.0,8.0,0.0,0.0,6.0,0.0,0.0,0.0},
                {0.0,2.0,4.0,0.0,5.0,0.0,0.0,0.0,0.0,0.0},
                {0.0,0.0,6.0,3.0,0.0,0.0,1.0,0.0,7.0,0.0}};
        Map result = gradAscent(dataMatrix, 5);
        Double[][] p = (Double[][]) result.get("p");
        Double[][] q = (Double[][]) result.get("q");
        Double[][] product = (Double[][]) result.get("product");
        display(p);
        display(q);
        display(product);
    }

    public static Map gradAscent(Double[][] data, int K) {
        int m = data.length, n = data[0].length;
        Double[][] p = new Double[m][K];
        Double[][] q = new Double[K][n];

        Random random=new Random();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < K; j++) {
                p[i][j] = random.nextDouble();
            }
        }
        for (int i = 0; i < K; i++) {
            for (int j = 0; j < n; j++) {
                q[i][j] = random.nextDouble();
            }
        }

        double alpha = 0.0002, beta = 0.02, maxCycles = 10000;
        for (int step = 0; step < maxCycles; step++) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (data[i][j] > 0) {
                        double error = data[i][j];
                        for (int k = 0; k < K; k++) {
                            error -= p[i][k] * q[k][j];
                        }
                        for (int k = 0; k < K; k++) {
                            p[i][k] += alpha * (2 * error * q[k][j] - beta * p[i][k]);
                            q[k][j] += alpha * (2 * error * p[i][k] - beta * q[k][j]);
                        }
                    }
                }
            }
            double loss = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if(data[i][j] > 0) {
                        double error = 0.0;
                        for (int k = 0; k < K; k++) {
                            error += p[i][k] * q[k][j];
                        }
                        loss = (data[i][j] - error) * (data[i][j] - error);
                        for (int k = 0; k < K; k++) {
                            loss += beta * (p[i][k] * p[i][k] + q[k][j] * q[k][j]) / 2;
                        }
                    }

                }
            }
            if (loss < 0.001)
                break;
//            System.out.println("step=" + step + ", loss=" + loss);
        }

        Double[][] product = new Double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                product[i][j] = 0.0;
                for (int k = 0; k < K; k++) {
                    product[i][j] += p[i][k] * q[k][j];
                }
            }
        }

        Map result = new HashMap();
        result.put("p", p);
        result.put("q", q);
        result.put("product", product);
        return result;
    }

    public static void display(Double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
