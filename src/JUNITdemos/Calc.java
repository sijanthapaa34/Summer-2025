package JUNITdemos;

public class Calc {
    //one unit
    public int divide(int a, int b) {
     return a/b;
    }

    //Another unit
    public int multiply(int a, int b) {
        return a*b;
    }
}
class Main {
    public static void main(String[] args) {
        Calc calc = new Calc();
        int result = calc.divide(10,5);
        if (result == 2) {
            System.out.println("test case passed");
        }
        else {
            System.out.println("test case failed");
        }
    }
}
