//enum week{
//    Sunday, Monday, Tuesday, Wednesday, Thursday , Friday, Saturday;
//}

enum laptop{
    Macbook(2000) , nitro5(1500), tufgaming(1600), nitrov5(1400);
    public int price;
    private laptop (int price){
        this.price = price;
    }
    public int getprice(){
        return price;
    }
    public void setprice(int price){
        this.price = price;
    }

}
public class Enumdemo {
    public static void main(String[] args) {
//        week [] dy= week.values();
//        for (week d : dy){
//            System.out.println(d + " "+ d.ordinal());
//        }


//        week day = week.Monday;
//        switch (day){
//            case Sunday:
//                System.out.println("Sunday");
//                break;
//            case Monday:
//                System.out.println("MONDAY");
//                break;
//            case Tuesday:
//                System.out.println("TUESDAY");
//                break;
//            case Wednesday:
//                System.out.println("WEDNESDAY");
//                break;
//            case Thursday:
//                System.out.println("THURSDAY");
//                break;

        laptop lp = laptop.Macbook;
        System.out.println(lp);

        laptop [] LP = laptop.values();
        for (laptop l : LP){
            System.out.println(l + " "+ l.getprice());
        }
        }


    }


