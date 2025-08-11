package Practice;

import java.util.Scanner;

public class ReverseWord {
    public static String reverse(String text){

        String[] words = text.split("");
        String finalText = "";
        for (String word : words) {
            String reverse = "";
            for (int i = word.length() - 1; i >= 0; i--) {
                reverse += word.charAt(i);
            }
            finalText+=reverse;
        }
        return finalText;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Sentence or Paragraph: ");
        String text = scanner.nextLine();
        System.out.println(reverse(text));

    }
}
