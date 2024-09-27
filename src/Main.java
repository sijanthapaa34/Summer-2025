public class Main {
    public static void main(String[] args)
    {
        Gymmembership gym = new Gymmembership("Ram","Annual", 12);
        gym.display();
        PremiumMembership pgym = new PremiumMembership("Sijan", "Annual",12, false, true);
        pgym.display();
    }
}