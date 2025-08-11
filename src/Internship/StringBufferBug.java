package Internship;

public class StringBufferBug extends Thread{

    static StringBuilder sb = new StringBuilder();
    char[]  chars;

    public StringBufferBug(String text) {
        this.chars = text.toCharArray();
    }

//    public void insert(char character[]){
//        for(Character c: character) {
//            System.out.println(c);
//        }
//    }

    public static void main(String[] args) throws InterruptedException {
        StringBufferBug thread1 = new StringBufferBug("Sijan");
        StringBufferBug thread2 = new StringBufferBug("Thapa");
        thread1.start();
        thread2.start();

        System.out.println(sb);
    }
    public void run() {
//        String s1 = "Sijan";
//        String s2 = "Thapa";
//        insert(s1.toCharArray());
//        insert(s2.toCharArray());

        for(Character c: chars){
            sb.append(c);
        }

    }
}
