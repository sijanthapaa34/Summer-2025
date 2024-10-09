package InterfaceDemo;

interface Resizable {
    public void resizeWidth(int width);
    public void resizeHeight(int height);
    public void resize(int width, int height, boolean maintainAspectRatio);
}
class Circle implements Resizable {
    private double radius;
    public Circle(double radius) {
        this.radius = radius;
    }
    public void resizeWidth(int width){
        this.radius = width/2;
    }
    public void resizeHeight(int height){
        this.radius = height/2;
    }
    public void resize(int width, int height, boolean maintainAspectRatio){
        if (maintainAspectRatio){
            this.radius = Math.min(width, height)/2;
            System.out.println("radius with maintained aspect ratio: " + this.radius);
        }
        else{
            this.radius = (width+ height)/4;
            System.out.println("radius without maintained aspect ratio: " + this.radius);
        }
    }
    public void display(){
        System.out.println("radius: " + this.radius);
    }
}

class Rectangle implements Resizable {
    private int width;
    private int height;
    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public void resizeWidth(int width){
        this.width = width;
        System.out.println("Resized width: " + width);
    }
    public void resizeHeight(int height){
        this.height = height;
        System.out.println("Resized height: " + height);
    }
    public void resize(int width, int height, boolean maintainAspectRatio) {
        if (maintainAspectRatio) {
            double aspectRatio = (double)width / height;
            this.width = width;
            this.height = (int) (aspectRatio * height);
            System.out.println("New Width and Height with maintained aspect ratio: " + width +","+height);
        }
        else{
            this.width = width;
            this.height = height;
            System.out.println("New Width and Height without maintained aspect ratio: " + width +","+height);
        }
    }
    public void display(){
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
    }

}
public class Resize {
    public static void main(String[] args) {
        Circle circle = new Circle(10);
        circle.resizeWidth(10);
        circle.resizeHeight(10);
        circle.display();
        Rectangle rectangle = new Rectangle(10,10);
        rectangle.resizeWidth(10);
        rectangle.resizeHeight(10);
        rectangle.display();

    }
}