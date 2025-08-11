package Internship;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SijanList implements List {

    private Object[] data;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
        for(Object element: data){
            if (o == element){
                return true;
            };
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            int index = 0;

            public boolean hasNext() {
                return index < size;
            }

            public Object next() {
                if (index >= size) {
                    System.out.println("No element");
                }
                return data[index++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        if (size < data.length) {
            data[size] = o;
            size++;
        } else {
            Object[] newData = new Object[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            data = newData;
            data[size] = o;
            size++;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (!contains(o)) {
            return false;
        }

        Object[] newData = new Object[data.length];
        int newIndex = 0;

        for (int i = 0; i < size; i++) {
            if (!data[i].equals(o)) {
                newData[newIndex] = data[i];
                newIndex++;
            }
        }
        data = newData;
        size--;
        return true;
    }


    @Override
    public boolean addAll(Collection c) {
        for(Object element: c){
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection c) {
        boolean modified = false;
        for (Object element : c) {
            add(index++, element);
            modified = true;
        }

        return modified;
    }

    @Override
    public void clear() {
        Object[] newData = new Object[data.length];
        data  = newData;
    }

    @Override
    public Object get(int index) {
        if ( index >= size) {
            return data[index];
        }
        return null;
    }

    @Override
    public Object set(int index, Object element) {
        if (index < data.length) {
            data[index] = element;
        } else {
            Object[] newData = new Object[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            data = newData;
            data[index] = element;
        }
        return null;
    }

    @Override
    public void add(int index, Object element) {
        add(element);
        for (int i = size - 1; i > index; i--) {
            Object temp = data[i];
            data[i] = data[i - 1];
            data[i - 1] = temp;
        }
    }

    @Override
    public Object remove(int index) {

        Object removedElement = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        data[size - 1] = null;
        size--;
        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i< data.length;i++){
            if(o == data[i]){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator listIterator() {
        return null;
    }

    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return List.of();
    }

    @Override
    public boolean retainAll(Collection c) {


        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean modified = false;
        for (Object element : c) {
            if (remove(element)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    public static void main(String[] args) {

        SijanList numbers = new SijanList();
        numbers.data = new Object[5];

        // 1. add()
        System.out.println("Adding numbers 10, 20, 30:");
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        System.out.println("Size: " + numbers.size());

        // 2. contains()
        System.out.println("\nContains 20? " + numbers.contains(20));
        System.out.println("Contains 99? " + numbers.contains(99));

        // 3. get()
        System.out.println("\nNumber at index 1: " + numbers.get(1));

        // 4. indexOf()
        System.out.println("\nIndex of 30: " + numbers.indexOf(30));

        // 5. set()
        System.out.println("\nSet index 1 to 25:");
        numbers.set(1, 25);
        System.out.println("Now at index 1: " + numbers.get(1));

        // 6. add(index, element)
        System.out.println("\nAdd 15 at position 1:");
        numbers.add(1, 15);
        System.out.println("Now at index 1: " + numbers.get(1));
        System.out.println("Size: " + numbers.size());

        // 7. remove(index)
        System.out.println("\nRemove at index 2:");
        Object removed = numbers.remove(2);
        System.out.println("Removed: " + removed);
        System.out.println("Size: " + numbers.size());

        // 8.  remove(object)
        System.out.println("\nRemove number 10:");
        boolean wasRemoved = numbers.remove((Object)10);
        System.out.println("Was removed? " + wasRemoved);
        System.out.println("Size: " + numbers.size());

        // 9. iterator
        System.out.println("\nCurrent numbers:");
        Iterator it = numbers.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        // 10.  addAll()
        System.out.println("\nAdd collection [40, 50, 60]:");
        Collection moreNumbers = List.of(40, 50, 60);
        numbers.addAll(moreNumbers);
        System.out.println("Size: " + numbers.size()); // 5


        // 12.  clear()
        System.out.println("\nClear all numbers:");
        numbers.clear();
        System.out.println("Size after clear: " + numbers.size());
        System.out.println("Is empty? " + numbers.isEmpty());
    }
}
