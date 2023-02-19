package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */
public class Main {

    private static DecimalFormat decimalFormat=new DecimalFormat("0.00");

    public static void main(String[] args) {
//        processArgs(args);
//        System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
        System.out.println("Machine has " + Runtime.getRuntime().availableProcessors() + " available processors.");
        for(int threads = 1; threads <= Runtime.getRuntime().availableProcessors(); threads++) {
            for(int runs = 1, arrSize = 500_000; runs <= 3; runs++, arrSize *= 2) {
                benchmark(arrSize, threads);
            }
        }
    }

      private static void benchmark(int arrSize, int count) {
          System.out.println("Array size: " + arrSize + "\t\tThreads: " + count);
          Random random = new Random();
          int[] arr = new int[arrSize];
          ArrayList<Long> list = new ArrayList<>();
          for (double cutoffRatio = 0.05; cutoffRatio < 1; cutoffRatio += 0.1) {
              ParSort.cutoff = (int) (arrSize * cutoffRatio);
              ParSort.forkJoinPool = new ForkJoinPool(count);
              long ti;
              long st = System.currentTimeMillis();
              for (int t = 0; t < 10; t++) {
                  for (int i = 0; i < arr.length; i++) arr[i] = random.nextInt(10000000);
                  ParSort.sort(arr, 0, arr.length);
              }
              long et = System.currentTimeMillis();
              ti = (et - st);
              list.add(ti);

              System.out.println("cutoff-ratio：" + (decimalFormat.format(cutoffRatio)) + "\t\t10times Time:" + ti + "ms");
          }
      }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
