package Exceptionhandling;

public class Cone {

    private double radiusOfBase;
    private double heightOfCone;

    public Cone(double radius, double height){
        this. radiusOfBase= radius;
        this. heightOfCone= height;
    }
    public double getRadius(){
        return radiusOfBase;
    }
    public void setRadius(double radius){
        this.radiusOfBase = radius;
    }
    public double getHeight(){
        return heightOfCone;
    }
    public void setHeight(double height){
        this.radiusOfBase = radiusOfBase;
    }
    public double getSlantHeight(){
        double sh =  Math.sqrt((radiusOfBase * radiusOfBase) + (heightOfCone * heightOfCone));
        return sh;
    }
    public double getVolume(){
        double vol = (Math.PI* radiusOfBase* radiusOfBase* heightOfCone)/3;
        return vol;
    }
    public double getTSA(){
        double tsa =(Math.PI*radiusOfBase* radiusOfBase)+(Math.PI*radiusOfBase*getSlantHeight());
        return tsa;
    }

    public static void main(String [] args){
        Cone cone =new Cone(5, 12);
        System.out.println("Volume:  "+ cone.getVolume());
        System.out.println("Total Surface Area:  "+ cone.getTSA());
    }
}

