package com.bgpark.sort;

public class QuickSort {

    /**
     * Quick sort algorithm
     * - create pivot
     * - move smaller element than pivot move to left
     * - move larger element than pivot move to right
     * - compute recursively
     */
    public static void main(String[] args) {
        int[] arr = new int[]{1, 8, 3, 9, 4, 5, 7};
        // 1 2 3 4 5 7 8 9
        // [1, 3], 4, [5], 7, [8, 9]

        // 1. pivot
        int low = 0, high = arr.length - 1;
        quickSort(arr, low, high);

        for (int a : arr) {
            System.out.println(a);
        }
    }

    private static void quickSort(int[] arr, int low, int high) {
        if (low > high) {
            return;
        }

        System.out.println("low=%d, high=%d".formatted(low, high));

        int pivot = arr[high];

        // 2. left and right pointer
        int l = low, r = high;

        // 3. partitioning
        while (l < r) {
            while (arr[l] <= pivot && l < r) {
                l++;
            }
            while (arr[r] >= pivot && l < r) {
                r--;
            }
            // arr[l] arr[r] swap
            System.out.println("l=%d, r=%d".formatted(l, r));
            swap(arr, l, r);
        }
        // 4. recursion of left and right partition
        // [1, 5, 3, 4, 7, 8 ,9;
        // low       l-1 lr    p

        swap(arr, l, high);

        // recursively call quicksort for left and right partition
        quickSort(arr, low, l - 1);
        quickSort(arr, l + 1, high);
    }

    private static void swap(int[] arr, int l, int r) {
        int tmp = arr[l];
        arr[l] = arr[r];
        arr[r] = tmp;
    }


}
