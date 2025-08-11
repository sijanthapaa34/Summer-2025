package Internship;

import java.util.Arrays;

public class StringBuilderImpl {
    char[] globalStrings = new char[1000];
    int globalEndIndex = 0;
    int index;
    int length;

    public StringBuilderImpl(String str) {
        this.index = globalEndIndex;
        this.length = 0;
        append(str);
    }

    public StringBuilderImpl append(String str){
        if ((globalStrings.length - globalEndIndex) < str.length()) {
            char[] newGlobalStrings = new char[globalStrings.length * 2];
            System.arraycopy(globalStrings, 0, newGlobalStrings, 0, globalEndIndex);
            globalStrings = newGlobalStrings;
        }
        if (str == null) {
            return this;
        }
        char[] s = str.toCharArray();

        System.arraycopy(s, 0, globalStrings, globalEndIndex, s.length);

        this.length += s.length;
        globalEndIndex += s.length;

        return this;
    }
    public StringBuilderImpl insert(int offset, String str){

        if ((globalStrings.length - globalEndIndex) < str.length()) {
            char[] newGlobalStrings = new char[globalStrings.length * 2];
            System.arraycopy(globalStrings, 0, newGlobalStrings, 0, globalEndIndex);
            globalStrings = newGlobalStrings;
        }
        if (str == null) {
            return this;
        }
        char[] s = str.toCharArray();
        int stringLength = s.length;

        for(int i = length-1; i>=offset; i--){
            globalStrings[index+stringLength+i] = globalStrings[index+i];
        }

        for(int i = 0; i<stringLength; i++){
            globalStrings[offset+i] = s[i];
        }

        length+=stringLength;
        globalEndIndex += stringLength;

        return this;
    }

    public StringBuilderImpl delete(int start, int end){

        int count = end-start;
        for (int i = start; i < length - count; i++) {
            globalStrings[index + i] = globalStrings[index + i + count];
        }
        length-=count;
        globalEndIndex -= count;

        return this;
    }

    public String toString() {
        char[] chars = Arrays.copyOfRange(globalStrings, index, index + length);
        return new String(chars);
    }

    public static void main(String[] args) {
        StringBuilderImpl sb = new StringBuilderImpl("RamThapa");
        System.out.println(sb);
        System.out.println(sb.append("Magar"));
        System.out.println(sb.insert(3, "Bahadur"));
        System.out.println(sb.delete(0,9));
    }

}
