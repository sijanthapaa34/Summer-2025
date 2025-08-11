package Internship;

import java.util.*;

public class EtchSketch extends Thread {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final Scanner scanner = new Scanner(System.in);

    private static int xPosition = 1;
    private static int yPosition = 1;
    private static final Set<String> visitedPoints = new HashSet<>();

    private String arrow;
    private boolean running = true;

    public EtchSketch(String arrow) {
        this.arrow = arrow;
    }

    public void stopArrow() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            clearConsole();
            visitedPoints.add(yPosition + "," + xPosition);
            printBoard();
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
                if (xPosition < WIDTH) {
                    xPosition++;
                    break;
                }
            case "<":
                if (xPosition > 0) {
                    xPosition--;
                    break;
                }
            case "v":
                if (yPosition < HEIGHT) {
                    yPosition++;
                    break;
                }
            case "^":
                if (yPosition > 0) {
                    yPosition--;
                    break;
                }
        }
    }

    private void printBoard() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (x == xPosition && y == yPosition) {
                    System.out.print(arrow);
                } else if (visitedPoints.contains(y + "," + x)) {
                    System.out.print(".");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
    }

    public static void main(String[] args) throws InterruptedException {
        EtchSketch currentArrow = null;

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
                currentArrow.join(); // Wait for thread to finish
            }

            if (ans.equals("x")) {
                System.out.println("Exiting...");
                break;
            }

            switch (ans) {
                case "r":
                    currentArrow = new EtchSketch(">");
                    break;
                case "l":
                    currentArrow = new EtchSketch("<");
                    break;
                case "d":
                    currentArrow = new EtchSketch("v");
                    break;
                case "u":
                    currentArrow = new EtchSketch("^");
                    break;
                default:
                    System.out.println("Invalid input!");
                    continue;
            }

            currentArrow.start();
        }
    }
}
