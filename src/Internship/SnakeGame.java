package Internship;

import java.util.*;

public class SnakeGame extends Thread {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private Scanner scanner = new Scanner(System.in);

    private static int xPosition = 1;
    private static int yPosition = 1;
    private static final List<Point> snakeBody = new ArrayList<>();
    private static int length = 1;
    private static int fruitX;
    private static int fruitY;

    private final String arrow;
    private boolean running = true;
    private static final Random random = new Random();

    public SnakeGame(String arrow) {
        this.arrow = arrow;
    }

    public void stopArrow() {
        running = false;
    }

    public static void placeFruit() {
        while (true) {
            fruitX = random.nextInt(WIDTH - 2) + 1;  // avoid borders
            fruitY = random.nextInt(HEIGHT - 2) + 1;

            boolean isOnSnake = false;
            for (Point p : snakeBody) {
                if (p.x == fruitX && p.y == fruitY) {
                    isOnSnake = true;
                    break;
                }
            }

            if (!isOnSnake) {
                break;
            }
        }
    }

    @Override
    public void run() {
        while (running) {

            clearConsole();
            Point newBody = new Point(xPosition, yPosition);
            snakeBody.add(newBody);

            if (xPosition == fruitX && yPosition == fruitY) {
                length++;
                placeFruit();
            }

            while (snakeBody.size() > length) {
                snakeBody.remove(0);
            }

            drawBoard();
            movePosition();

            if (isSelfCollision() || isWallCollision()) {
                stopArrow();
                System.out.println("Game Over!");
                System.out.println("Restart? y/n");
                String ans = scanner.nextLine();
                if (ans.equalsIgnoreCase("y")) {
                    xPosition = 1;
                    yPosition = 1;
                    length = 1;
                    snakeBody.clear();
                    placeFruit();
                    running = true;
                    run(); // restart game loop
                    return;
                } else {
                    running = false;
                    return;
                }
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isSelfCollision() {
        for (int i = 0; i < snakeBody.size() - 1; i++) {
            Point point = snakeBody.get(i);
            if (point.x == xPosition && point.y == yPosition) {
                return true;
            }
        }
        return false;
    }

    private boolean isWallCollision() {
        return (xPosition <= 0 || xPosition >= WIDTH-1 || yPosition <= 0 || yPosition >= HEIGHT-1);
    }

    private void movePosition() {
        switch (arrow) {
            case ">":
                    xPosition++;
                    break;
            case "<":
                    xPosition--;
                    break;
            case "v":
                    yPosition++;
                    break;
            case "^":
                    yPosition--;
                    break;
        }

        //restart
        // bug(in list)
        // design implementation  event loop

    }

    private void drawBoard() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (y == 0 || y == HEIGHT - 1 || x == 0 || x == WIDTH - 1) {
                    System.out.print("#");
                }
                else if (x == fruitX && y == fruitY) {
                    System.out.print("F");
                }
                else if (x == xPosition && y == yPosition) {
                    System.out.print(arrow);
                }
                else if (isSnakeBody(x, y)) {
                    System.out.print(".");
                }
                else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        System.out.println("Length: " + length);
    }

    private boolean isSnakeBody(int x, int y) {
        for(Point p: snakeBody){
            if (p.equals(x, y)) {
                return true;
            }
        }
        return false;
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        placeFruit();
        SnakeGame currentArrow = null;

        while (true) {
            System.out.println("\n> : d" +
                    "\n< : a" +
                    "\nv : s" +
                    "\n^ : w" +
                    "\nexit : x");
            System.out.print("Enter a key: ");
            String ans = scanner.nextLine();

            if (currentArrow != null && currentArrow.isAlive()) {
                currentArrow.stopArrow();
                currentArrow.join();
            }

            if (ans.equals("x")) {
                System.out.println("Exiting...");
                break;
            }

            switch (ans) {
                case "d":
                    currentArrow = new SnakeGame(">");
                    break;
                case "a":
                    currentArrow = new SnakeGame("<");
                    break;
                case "s":
                    currentArrow = new SnakeGame("v");
                    break;
                case "w":
                    currentArrow = new SnakeGame("^");
                    break;
                default:
                    System.out.println("Invalid input!");
                    continue;
            }

            currentArrow.start();
        }
    }
}

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }
}
