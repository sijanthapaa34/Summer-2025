package Internship;

import java.util.Arrays;

public class MyString {
    static char[] globalStrings = new char[100];
    private static int startIndexExclusive = 0;
    int index;
    int length;
    MyString(char[] s) {
        this.index = startIndexExclusive;
        this.length = s.length;
        System.arraycopy(s, 0, globalStrings, startIndexExclusive, s.length);
        startIndexExclusive += s.length;
    }

    public String concat(MyString c) {
        int newLength = this.length + c.length;
        System.out.println();
        char[] newArray = new char[newLength];

//        for(int i = 0; i<this.index; i++){
//            if(globalStrings[i] == '\0'){
//                newArray[i] = globalStrings[i];
//            }
//            else{
//                newArray[i] = globalStrings[i];
//            }
//        }
//        for(int i = this.index; i<this.length; i++){
//                newArray[i] = globalStrings[i];
//        }
//        for(int i = c.index; i<c.length; i++){
//                newArray[i] = globalStrings[i];
//        }
        for(int i = 0; i<this.length; i++){
                newArray[i] = globalStrings[this.index+i];
        }
        for(int i = 0; i<c.length; i++){
                newArray[this.length+i] = globalStrings[c.index+i];
        }
        return new String(newArray);
    }

    public String toString() {
        char[] chars = Arrays.copyOfRange(globalStrings, index, index + length);
        return new String(chars);
    }

    public static void main(String[] args) {
        MyString a = new MyString("hello".toCharArray());


        MyString b = new MyString("world".toCharArray());

        MyString e = new MyString("Pokhara".toCharArray());

        String f = b.concat(e);
        System.out.println("concat a b: "+f);

        String c = a.toString();
        String d = b.toString();
        System.out.println("a "+c);


        System.out.println("b "+d);

        System.out.println(a.toString() );

    }
}
