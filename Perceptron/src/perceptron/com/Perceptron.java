package perceptron.com;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;

class Perceptron {
    private LinkedList<Float> W_1 = new LinkedList<>();
    private LinkedList<Float> W_2 = new LinkedList<>();
    private LinkedList<Float> W_3 = new LinkedList<>();
    private LinkedList<Integer> X_1 = new LinkedList<>();
    private LinkedList<Integer> X_2 = new LinkedList<>();
    private LinkedList<Integer> X_3 = new LinkedList<>();
    private LinkedList<Float> alpha = new LinkedList<>();
    private LinkedList<Integer> Y = new LinkedList<>();
    private LinkedList<Integer> T = new LinkedList<>();
    private LinkedList<Float> ithaTY = new LinkedList<>();
    private LinkedList<Integer> delta = new LinkedList<>();
    private LinkedList<Float> delta_W_1 = new LinkedList<>();
    private LinkedList<Float> delta_W_2 = new LinkedList<>();
    private LinkedList<Float> delta_W_3 = new LinkedList<>();
    private LinkedList<Float> delta_teta = new LinkedList<>();

    private final float teta = (float) 0.5;
    private final float itha = (float) 0.3;
    private boolean one;

    private Random rand = new Random();

    Perceptron() {
        one = true;

        for (int index = 0; index < 7; index++) {
            alpha.add((float) 0);
            Y.add(0);
            ithaTY.add((float) 0);
            delta_W_1.add((float) 0);
            delta_W_2.add((float) 0);
            delta_W_3.add((float) 0);
            delta_teta.add((float) 0);
            delta.add(0);
        }
    }

    void incoming() {
        for (int x1 = 0; x1 <= 1; x1++) {
            for (int x2 = 0; x2 <= 1; x2++) {
                for (int x3 = 0; x3 <= 1; x3++) {
                    if (x1 != 0 || x2 != 0 || x3 != 0) {
                        X_1.add(x1);
                        X_2.add(x2);
                        X_3.add(x3);
                    }
                }
            }
        }
    }

    void weights() {
        float w1 = rand.nextFloat();
        float w2 = rand.nextFloat();
        float w3 = rand.nextFloat();

        for (int index = 0; index < X_1.size(); index++) {
            W_1.add(w1);
            W_2.add(w2);
            W_3.add(w3);
        }
    }

    void logicalFunction() {
        int F;

        for (int index = 0; index < X_1.size(); index++) {
            F = (X_1.get(index) & (~X_2.get(index)));
            if (F <= X_3.get(index)) T.add(1);
            else if (F > X_3.get(index)) T.add(0);
        }
    }

    void learningPerceptron() {
        float copy_W_1, copy_W_2, copy_W_3;
        int right;
        double a;

        do {
            right = 0;

            for (int index = 0; index < X_1.size(); index++) {
                if (one) {
                    if (index == X_1.size()-1) {
                        one = false;
                    }
                }

                a = (X_1.get(index) * W_1.get(index)) + (X_2.get(index) * W_2.get(index)) + (X_3.get(index) * W_3.get(index));
                alpha.set(index, (float) a);

                if (alpha.get(index) > teta) Y.set(index, 1);
                else Y.set(index, 0);

                delta.set(index, T.get(index) - Y.get(index));

                if (!Objects.equals(Y.get(index), T.get(index))) {
                    ithaTY.set(index, itha * delta.get(index));
                } else ithaTY.set(index, (float) 0);

                delta_W_1.set(index, delta.get(index) * W_1.get(index));
                delta_W_2.set(index, delta.get(index) * W_2.get(index));
                delta_W_3.set(index, delta.get(index) * W_3.get(index));
                delta_teta.set(index, delta.get(index) * teta);

                if (index == X_1.size()-1) {
                    out();

                    for (int i = 0; i < X_1.size(); i++) {
                        if (!Objects.equals(Y.get(i), T.get(i))) {
                            copy_W_1 = W_1.get(i);
                            copy_W_2 = W_2.get(i);
                            copy_W_3 = W_3.get(i);

                            float Delta_1 = itha * delta.get(i) * X_1.get(i);
                            float Delta_2 = itha * delta.get(i) * X_2.get(i);
                            float Delta_3 = itha * delta.get(i) * X_3.get(i);

                            W_1.set(i, copy_W_1 + Delta_1);
                            W_2.set(i, copy_W_2 + Delta_2);
                            W_3.set(i, copy_W_3 + Delta_3);
                        }
                    }
                }

                if (Objects.equals(Y.get(index), T.get(index))) {
                    right++;
                }
            }
        } while (right != 7);
    }

    private void out() {
        System.out.println("|   w1   |   w2   |   w3   |  0   | x1 | x2 | x3 |  a   | Y | T | n(T-Y) | bw1  | bw2  | bw3  |  b0  |");
        System.out.println("------------------------------------------------------------------------------------------------------");

        for (int index = 0; index < X_1.size(); index++) {
            System.out.printf("|  %.2f  |  %.2f  |  %.2f  | %.2f | %d  | %d  | %d  | %.2f | %d | %d |  %.2f  | %.2f | %.2f | %.2f | %.2f | \n",
                    W_1.get(index), +W_2.get(index), +W_3.get(index), teta, X_1.get(index), X_2.get(index),
                    X_3.get(index), alpha.get(index), Y.get(index), T.get(index), ithaTY.get(index), delta_W_1.get(index),
                    delta_W_2.get(index), delta_W_3.get(index), delta_teta.get(index));
        }

        System.out.println("______________________________________________________________________________________________________");
    }
}