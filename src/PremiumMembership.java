// Write a Java program to create a class called "GymMembership" with attributes for member name,
// membership type, and duration. Create a subclass "PremiumMembership" that adds attributes for
// personal trainer availability and spa access. Implement methods to calculate membership fees and
// check for special offers based on membership type.
public class PremiumMembership extends Gymmembership{
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
