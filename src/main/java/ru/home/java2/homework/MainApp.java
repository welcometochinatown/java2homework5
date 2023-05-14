package ru.home.java2.homework;

import java.util.Arrays;

public class MainApp {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) throws InterruptedException {
        initializeArray();
        splitAndMerge();
    }

    public static void initializeArray() {
        float[] initialArray = new float[SIZE];

        for (int i = 0; i < SIZE; i++) {
            initialArray[i] = 1.0f;
        }

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < initialArray.length; i++) {
            initialArray[i] = (float) (initialArray[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
//        System.out.println("Initial array after formula: " + Arrays.toString(initialArray));
    }

    public static void splitAndMerge() throws InterruptedException {
        float[] initialArray = new float[SIZE];

        for (int i = 0; i < SIZE; i++) {
            initialArray[i] = 1.0f;
        }

        // Запускаем таймер
        long startTime = System.currentTimeMillis();

        // Разбиваем массив на 2 равных части
        float[] leftHalf = new float[HALF];
        float[] rightHalf = new float[HALF];
        System.arraycopy(initialArray,0, leftHalf, 0, HALF);
        System.arraycopy(initialArray,HALF, rightHalf, 0, HALF);

        // Создаем и запускаем поток для формулы к 1й половине
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                leftHalf[i] = (float) (leftHalf[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        thread1.start();

        // Создаем и запускаем поток для формулы ко 2й половине
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < HALF; i++) {
                // Проблема: целый массив после обработки формулой - отличается от массива разбитого на 2 части и обработанного формулой по частям,
                // Скорее всего проблема в данной части программы, но пока не пойму в чем конкретно.
                // Решение, мы делаем расчет формулой второй половины, как для 1й половины, не учитывая, что нужно делать смещение
                rightHalf[i] = (float) (rightHalf[i] * Math.sin(0.2f + (i + HALF) / 5) * Math.cos(0.2f + (i + HALF) / 5) * Math.cos(0.4f + (i + HALF) / 2));
            }
        });
        thread2.start();

        thread1.join();
        thread2.join();

        // Склеиваем массивы в один, после обработки
        float[] mergedArray = new float[SIZE];
        System.arraycopy(leftHalf, 0, mergedArray, 0, HALF);
        System.arraycopy(rightHalf, 0, mergedArray, HALF, HALF);

        System.out.println("Two thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
//        System.out.println("Merged array after formula: " + Arrays.toString(mergedArray));
    }
}
