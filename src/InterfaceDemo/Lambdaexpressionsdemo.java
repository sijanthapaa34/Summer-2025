package InterfaceDemo;
interface S{
    void start();
        }

 interface T{
    void start(String s , int p);
 }

public class Lambdaexpressionsdemo {
    public static void main(String[] args) {

        // lambda expression
        S obj = () ->

        {
            System.out.println("starting");
        };
        obj.start();

        //parameter
        T objt = ( s,  p) ->{
            System.out.println(s + " starting in "+ p);
        };
        objt.start("sijan", 1);
    }
}
