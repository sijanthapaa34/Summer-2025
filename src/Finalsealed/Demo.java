package Finalsealed;

//to extend sealed class, use final or non-sealed class
sealed class A extends Thread implements Cloneable permits B,C{
}
non-sealed class B extends A{

}
final class C extends A{

}
class D extends B {

}

sealed interface X permits Y{

}
non-sealed interface Y extends X{

}
public class Demo {
    public static void main(String[] args) {

    }
}
