package Internship;

import java.util.*;

public class SijanSet  {
        private List<Integer>[] table;
        private int capacity = 16;
        private int size = 0;

        public SijanSet() {
            table = new ArrayList[capacity];
            for (int i = 0; i < capacity; i++) {
                table[i] = new ArrayList<>();
            }
        }

        public void add(int value) {
            int index = Math.abs(value % capacity);
            List<Integer> bucket = table[index];
            if (!bucket.contains(value)) {
                bucket.add(value);
                size++;

                if (bucket.size() > 8) {
                    System.out.println("High collision at index " + index);
                }

                if (size >= capacity * 0.75) {
                    resize();
                }
            }
        }

        public boolean contains(int value) {
            int index = Math.abs(value % capacity);
            return table[index].contains(value);
        }

        private void resize() {
            capacity *= 2;
            List<Integer>[] oldTable = table;
            table = new ArrayList[capacity];
            for (int i = 0; i < capacity; i++) {
                table[i] = new ArrayList<>();
            }

            size = 0;
            for (List<Integer> bucket : oldTable) {
                for (int value : bucket) {
                    add(value);
                }
            }
        }


    public static void main(String[] args) {
        SijanSet set = new SijanSet();

        for (int i = 0; i < 30; i++) {
            set.add(i);
        }

        set.add(10); // duplicate test

        System.out.println("Set contains 18? " + set.contains(18));
        System.out.println("Set contains 100? " + set.contains(100));

    }
}
