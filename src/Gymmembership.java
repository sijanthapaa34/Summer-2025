// Write a Java program to create a class called "GymMembership" with attributes for member name,
// membership type, and duration. Create a subclass "PremiumMembership" that adds attributes for
// personal trainer availability and spa access. Implement methods to calculate membership fees and
// check for special offers based on membership type.
public class Gymmembership {
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
