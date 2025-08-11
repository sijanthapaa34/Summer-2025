package Internship;

import java.util.Scanner;

public class Arrow extends Thread {

    static Scanner scanner = new Scanner(System.in);
    private static int xPosition = 0;
    private static int yPosition = 0;

    private String arrow;
    private boolean running = true;

    public Arrow(String arrow) {
        this.arrow = arrow;
    }

    public void stopArrow() {
        running = false;
    }

    public void run() {
        while (running) {

            System.out.print("\033[H\033[2J");

            for (int y = 0; y < yPosition; y++) {
                System.out.println();
            }

            for (int x = 0; x < xPosition; x++) {
                System.out.print(" ");
            }

            System.out.println(arrow);

            movePosition(arrow);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void movePosition(String arrow) {
        switch (arrow) {
            case ">":
                xPosition++;
                break;
            case "<":
                if (xPosition > 0) {
                    xPosition--;
                }
                break;
            case "v":
                yPosition++;
                break;
            case "^":
                if (yPosition > 0) {
                    yPosition--;
                }
                break;
        }
    }



    public static void main(String[] args) throws InterruptedException {
        Arrow currentArrow = null;

        while (true) {
            System.out.println("\n> : r" +
                    "\n< : l" +
                    "\nv : d" +
                    "\n^ : u" +
                    "\nexit : x");
            System.out.print("Enter a key: ");
            String ans = scanner.nextLine();

            if (currentArrow != null && currentArrow.isAlive()) {
                currentArrow.stopArrow();
            }

            if (ans.equals("x")) {
                System.out.println("Exiting...");
                break;
            }

            switch (ans) {
                case "r":
                    currentArrow = new Arrow(">");
                    break;
                case "l":
                    currentArrow = new Arrow("<");
                    break;
                case "d":
                    currentArrow = new Arrow("v");
                    break;
                case "u":
                    currentArrow = new Arrow("^");
                    break;
                default:
                    System.out.println("Invalid input!");
                    continue;
            }

            currentArrow.start();
        }
    }
}
