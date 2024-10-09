package InterfaceDemo;

@FunctionalInterface
interface A{
    void display();
}

//class longmethod implements A{
//    public void display(){
//        System.out.println("display");
//    }
//}

public class Functionalinter {
    public static void main(String[] args) {
//        this is the long method
//        A a = new longmethod();
//        a.display();

//        instead we can do this
        A a = new A() {
            public void display(){
                System.out.println("effective method");
            }
        };
        a.display();

    }

}
