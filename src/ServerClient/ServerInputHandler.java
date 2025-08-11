//package ServerClient;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class ServerInputHandler extends Thread {
//    private int startingIndex = 0;
//
//    @Override
//    public void run() {
//        Scanner scanner = new Scanner(System.in);
//        boolean scrollMode = false;
//
//        while (true) {
//            printMessageBox();
//
//            System.out.print(scrollMode ? "(scroll mode) u/d/exit: " : "Type message or 'scroll': ");
//            String input = scanner.nextLine().trim();
//
//            if (!scrollMode) {
//                if(input.equalsIgnoreCase("bye")){
//                    System.out.println("bye");
//                    break;
//                }
//                else if(input.equalsIgnoreCase("scroll")){
//                    scrollMode = true;
//                    System.out.print("(scroll mode) u for up/d for down/exit: ");
//                    int totalChatSize = ServerClientHandler.serverHistory.size();
//                    if(totalChatSize>4){
//                        startingIndex = totalChatSize-4;
//                    }
//                    else{
//                        startingIndex = 0;
//                    }
//                }
//                else{
//                    Server.broadcastFromServer(input);
//                    System.out.print("Type message or 'scroll': ");
//                    int totalChatSize = ServerClientHandler.serverHistory.size();
//                    if(totalChatSize>4){
//                        startingIndex = totalChatSize-4;
//                    }
//                    else{
//                        startingIndex = 0;
//                    }
//                }
//            }else{
//                if(input.equals("u")){
//                    if (startingIndex > 0) {
//                        startingIndex = startingIndex - 1;
//                    }
//                }
//                else if(input.equals("d")){
//                    int totalChatSize = ServerClientHandler.serverHistory.size();
//                    int maxStartingIndex = totalChatSize-4;
//                    if(maxStartingIndex > 0){
//                        startingIndex++;
//                    }
//                    else{
//                        startingIndex = 0;
//                    }
//                }
//                else if (input.equals("exit")) {
//                    scrollMode = false;
//                    int totalChatSize = ServerClientHandler.serverHistory.size();
//                    if(totalChatSize>4){
//                        startingIndex = totalChatSize-4;
//                    }
//                    else{
//                        startingIndex = 0;
//                    }
//                }
//            }
//            }
//    }
//
//    private void printMessageBox() {
//        System.out.print("\033[H\033[2J");
//
//        List<String> history = ServerClientHandler.serverHistory;
//        int size = history.size();
//
//        System.out.println("+---------------- Chat History ------------------+");
//
//        int start = startingIndex;
//        int end = start + 4;
//
//        for (int i = start; i < end; i++) {
//            System.out.println("| " + history.get(i));
//        }
//        System.out.println("+------------------------------------------------+");
//    }
//}
