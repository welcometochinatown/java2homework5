package ru.home.java2.homework;

import java.util.Arrays;

public class ArraySplitTest {
    static final int SIZE = 10;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        float[] arr = new float[SIZE];

        Arrays.fill(arr, 1.0f);

        float[] leftHalf = new float[HALF];
        float[] rightHalf = new float[HALF];
        System.arraycopy(arr, 0, leftHalf, 0, HALF);
        System.arraycopy(arr, HALF, rightHalf, 0, HALF);

        System.out.println(Arrays.toString(leftHalf));
        System.out.println(Arrays.toString(rightHalf));

        float[] mergedArray = new float[SIZE];
        System.arraycopy(leftHalf, 0, mergedArray, 0, HALF);
        System.arraycopy(rightHalf, 0, mergedArray, HALF, HALF);
        System.out.println(Arrays.toString(mergedArray));
    }
}
