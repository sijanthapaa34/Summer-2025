package InterfaceDemo;

// Write a Java program to create a class called "GymMembership" with attributes for member name,
// membership type, and duration. Create a subclass "InterfaceDemo.Main.InterfaceDemo.PremiumMembership" that adds attributes for
// personal trainer availability and spa access. Implement methods to calculate membership fees and
// check for special offers based on membership type.
class Gymmembership {
    private String memberName;
    private String membershipType;
    private int duration;
    public Gymmembership() {}
    public Gymmembership(String memberName, String membershipType, int duration) {
        this.memberName = memberName;
        this.membershipType = membershipType;
        this.duration = duration;
    }
    public double calculateFees(){
        double baseFees = 1000;
        double totalFees = baseFees * duration;
        return totalFees;
    }

    public String checkSpecialoffer() {
        if (membershipType.equalsIgnoreCase("Annual")) {
            return("Annual offer 10%");
        }
        else{
            return("No special offer");
        }
    }
    public void display(){
        System.out.println("Member Name: " + memberName);
        System.out.println("Membership Type: " + membershipType);
        System.out.println("Duration: " + duration);
        System.out.println("Fees: " + calculateFees());
        System.out.println("SpecialOffer: " + checkSpecialoffer());

    }
}

class PremiumMembership extends Gymmembership{
    private boolean ptAvailability;
    private boolean spaAccess;

    public PremiumMembership(String memberName, String membershipType, int duration, boolean ptAvailability, boolean spaAccess) {
        super(memberName, membershipType, duration);
        this.ptAvailability = ptAvailability;
        this.spaAccess = spaAccess;
    }
    public double calculateFees(){
        double totalFees = super.calculateFees();
        if(spaAccess){
            totalFees *= 0.5;
        }
        return totalFees;
    }
    public String isPtAvailability() {
        if(ptAvailability){
            return ("personal trainer is available");
        }
        else{
            return ("personal trainer is not available");
        }
    }

    public String isSpaAccess() {
        if(spaAccess){
            return ("10% extra for spa");
        }
        else{
            return ("spa cannot be accessed");
        }
    }
    public void display()
    {
        super.display();
        System.out.println(isPtAvailability());
        System.out.println(isSpaAccess());
    }
}

public class Main {
    public static void main(String[] args)
    {
        Gymmembership gym = new Gymmembership("Ram","Annual", 12);
        gym.display();
        PremiumMembership pgym = new PremiumMembership("Sijan", "Annual",12, false, true);
        pgym.display();
    }}

