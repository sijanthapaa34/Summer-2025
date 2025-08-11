//package ServerClient;
//
//import java.io.IOException;
//
//public class KeyCommandListener extends Thread {
//    private int starting = 0;
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                int ch = System.in.read();
//                if (ch == 's') {
//                    enterScrollMode();
//                }
//                // Optional: you can handle other main-mode keys here if needed
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void enterScrollMode() {
//        try {
//            // Switch to raw mode
//            Runtime.getRuntime()
//                    .exec(new String[]{"/bin/sh", "-c", "stty raw </dev/tty"})
//                    .waitFor();
//
//            while (true) {
//                int ch = System.in.read();
//                if (ch == 'u') {
//                    starting = Math.max(0, starting - 1);
//                    printMessageBox(starting);
//                } else if (ch == 'd') {
//                    starting = Math.min(ServerClientHandler.serverHistory.size() - 1, starting + 1);
//                    printMessageBox(starting);
//                } else if (ch == 'x') {
//                    break;
//                }
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                // Return to cooked mode
//                Runtime.getRuntime()
//                        .exec(new String[]{"/bin/sh", "-c", "stty cooked </dev/tty"})
//                        .waitFor();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void printMessageBox(int starting) {
//        System.out.print("\033[H\033[2J"); // clear terminal
//        System.out.println("+------------------------------------------------+");
//
//        int end = Math.min(ServerClientHandler.serverHistory.size(), starting + 5);
//        for (int i = starting; i < end; i++) {
//            System.out.println("| " + ServerClientHandler.serverHistory.get(i));
//        }
//
//        System.out.println("|------------------------------------------------|");
//    }
//}
