package ThreadsDemo;
class Z{

}
//class A implements Runnable {
//    public void run(){
//        for(int i =0; i<=5;i++){
//        System.out.println("A");
//        try {
//            Thread.sleep( 10);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        }}
//}
class B extends Thread{
    public void run(){
        for(int i =0; i<=5;i++){
            System.out.println("B");
            try {
                Thread.sleep( 10);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
public class Demo1 {
    public static void main(String[] args) {
//        A a = new A();
        //Anonymous class
//        Runnable obj1 = new Runnable() {
//            @Override
//            public void run() {
//                for(int i =0; i<=5;i++){
//                    System.out.println("A");
//                    try {
//                        Thread.sleep( 10);
//
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//        };


        //Lambda expressions
        Runnable obj1 = () ->
        {

                for(int i =0; i<=5;i++){
                    System.out.println("A");
                    try {
                        Thread.sleep( 10);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

        };




        B b = new B();
        Thread t1 = new Thread(obj1);


        t1.start();
        b.start();
    }
}
