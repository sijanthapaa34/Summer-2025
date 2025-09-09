package Internship;

import java.util.Arrays;

public class SelectionSort {
    public static void main(String[] args) {
        int[] numbers = {5, 2, 9, 1, 7};
        System.out.println(Arrays.toString(numbers));
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.sort(numbers);
    }

    private void sort(int[] numbers) {
        int length = numbers.length;

        for(int i = 0; i < length; i++){
            int minIndex = i;
            for(int j  = i+1; j < length; j++){
                if(numbers[j]<numbers[minIndex]){
                    minIndex = j;
                }
            }
            int temp = numbers[minIndex];
            numbers[minIndex] = numbers[i];
            numbers[i]= temp;
        }
        System.out.println(Arrays.toString(numbers));
    }

}
