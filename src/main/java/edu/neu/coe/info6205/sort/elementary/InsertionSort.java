/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */


    public static Integer[] random(int a) {
        Random r= new Random();
        Integer[] array = new Integer[a];
        for(int i=0;i<a;i++)
        {
            array[i]=r.nextInt(a);
        }
        return array;
    }

    public static Integer[] ordered(int a) {
        Integer[] array = new Integer[a];
        for(int i=0;i<a;i++)
        {
            array[i]=i+1;
        }
        return array;
    }

    public static Integer[] partiallyOrdered(int a) {
        Random r = new Random();
        Integer[] array = new Integer[a];
        for (int i = 0; i < a / 4; i++) {
            array[i] = i + 1;
        }
        for (int i = a / 4; i < a * 3 / 4; i++) {
            array[i] = r.nextInt(a);
        }
        for (int i = a * 3 / 4; i < a; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    public static Integer[] reverseOrdered(int a) {
        Integer[] array = new Integer[a];
        int count =1;
        for(int i=0;i<a;i++)
        {
            array[i]=a+2-count;
            count++;
        }
        return array;
    }



    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();
         for (int i = from; i < to; i++) {
            X key = xs[i];
            int j = i - 1;
            while (j >= from && !helper.less(xs[j],key)) {
                helper.swap(xs, j, j+1);
                j = j - 1;
            }
            xs[j + 1] = key;
        }
    }

    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }


    private static <T extends Comparable<T>> T[] consumer(T[] ts) {
        sort(ts);
        return ts;
    }

    public static void main(String[] args) {

        final int start = 100;
        final int test = 10;
        Benchmark_Timer<Integer[]> benchmarker = new Benchmark_Timer<>("InsertionSort", InsertionSort::consumer);

        for (int i = 0, n = start; i < test; i++, n *= 2) {
            final int arr = n;
            int num=15;
            double randomTime=benchmarker.runFromSupplier(() -> random(arr), num);
            double orderedTime=benchmarker.runFromSupplier(() -> ordered(arr), num);
            double partiallyOrdered=benchmarker.runFromSupplier(() -> partiallyOrdered(arr), num);
            double reverseOrdered=benchmarker.runFromSupplier(() -> reverseOrdered(arr), num);
            System.out.println(arr + ", " + "random array: " + randomTime);
            System.out.println(arr + ", " + "ordered array: " + orderedTime);
            System.out.println(arr + ", " + " partially array: " + partiallyOrdered);
            System.out.println(arr + ", " + "reverse array: " + reverseOrdered);
        }

    }
}
