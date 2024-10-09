package InterfaceDemo;
interface New{
    int add(int a,int b);
}
public class Lambdawexpression2 {
    public static void main(String[] args) {
//        for return type(regular method)
//        New obj = (int a,int b)-> {
//            return a+b;
//        };

// for return type
        New obj = (int a,int b)-> a+b;

        System.out.println(obj.add(1,2));
    }
}
