public class Throwthrows {
    public static double area(double r){
        double result = Math.PI*r*r;
        return result;
    }
    public static void main(String[] args) {
        try{
            int c = divide(6,0);
            System.out.println(c);
        }
        catch(Exception e){
            System.out.println("Exception caught");
        }
    }
    public static int divide(int a, int b) throws Exception{
        return a/b;
    }
}

