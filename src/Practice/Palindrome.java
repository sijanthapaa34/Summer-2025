package Practice;

import java.util.Scanner;

public class Palindrome {

    public static boolean isPalindrome(String text) {
        int length = text.length();
        for(int i = 0; i < length / 2; i++) {
            if (text.charAt(i) != text.charAt(length-1-i)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Enter a Word: ");
            String text = scanner.nextLine();
            if (isPalindrome(text)) {
                System.out.println("It is palindrome");
            } else {
                System.out.println("It is not palindrome");
            }
            System.out.print("0 to Exit: ");
            int exit = scanner.nextInt();
            scanner.nextLine();
            if(exit ==  0){
                break;
            }
        }
    }
}
