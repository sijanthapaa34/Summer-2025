package Internship;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] numbers = {5, 2, 9, 1, 7};
        System.out.println(Arrays.toString(numbers));
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(numbers, 0, numbers.length-1);
    }

    private void sort(int[] numbers, int left, int right ) {
        if(left<right){
            int mid = (left+right)/2;

        }
    }
    private void merge(int[] numbers, int left, int mid, int right ) {
    }

}
