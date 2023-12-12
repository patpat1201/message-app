import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MessageApp {
    private static final int MAX_MESSAGES = 10;
    private static final Message[] messageQueue = new Message[MAX_MESSAGES];
    private static int queueFront = 0;
    private static int queueRear = 0;

    private static final Message[] messageStack = new Message[MAX_MESSAGES];
    private static int stackTop = -1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            displayMenu();

            System.out.print("Choose an option (1/2/3/4): ");
            int option = getValidOption(scanner);
            switch (option) {
                case 1:
                    inputAndAddToQueue(scanner);
                    break;
                case 2:
                    sendMessages();
                    break;
                case 3:
                    viewLatestMessages();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting Message App. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("----- Message Menu -----");
        System.out.println("1. Input Message");
        System.out.println("2. Send Message");
        System.out.println("3. View Latest Messages");
        System.out.println("4. Exit");
    }
    private static int getValidOption(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid option number.");
            }
        }
    }


    private static void inputAndAddToQueue(Scanner scanner) {
        System.out.print("Enter the message (max 250 characters): ");
        String inputString = scanner.nextLine();

        if (queueRear < MAX_MESSAGES) {
            messageQueue[queueRear++] = new Message(inputString, new Date());
            System.out.println("Message added to the queue.");
        } else {
            System.out.println("Message queue is full. Cannot add more messages.");
        }
    }

    private static void sendMessages() {
        while (queueFront != queueRear && stackTop < MAX_MESSAGES) {
            messageStack[++stackTop] = messageQueue[queueFront++];
        }

        System.out.println("Messages sent and moved from Queue to Stack.");
    }

    private static void viewLatestMessages() {
        if (stackTop == -1) {
            System.out.println("No messages in the stack.");
            return;
        }

        System.out.println("Latest Messages:");

        for (int i = stackTop; i >= 0; i--) {
            Message message = messageStack[i];
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(message.getTimestamp());

            System.out.println("[" + formattedDate + "] " + message.getText());
        }
    }

    private static class Message {
        private final String text;
        private final Date timestamp;

        public Message(String text, Date timestamp) {
            this.text = text;
            this.timestamp = timestamp;
        }

        public String getText() {
            return text;
        }

        public Date getTimestamp() {
            return timestamp;
        }
    }
}


