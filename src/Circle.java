

public class Circle implements Resizable {
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
