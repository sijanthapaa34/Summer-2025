public class Rectangle implements Resizable{
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
