import java.util.ArrayList;
import java.util.Scanner;

public class StoredMessages {

    private final ArrayList<String[]> messages = new ArrayList<>();
    private final Scanner input = new Scanner(System.in);

    // Add a stored message
    public void addMessage(String id, String hash,
                           String recipient, String message) {
        messages.add(new String[]{id, hash, recipient, message});
        System.out.println("Message stored successfully.");
    }

    // 1. Display recipients of stored messages
    public void displaySendersAndRecipients() {
        if (messages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }

        for (String[] message : messages) {
            System.out.println("Recipient: " + message[2]);
        }
    }

    // 2. Display the longest stored message
    public void displayLongestMessage() {
        if (messages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }

        String[] longest = messages.get(0);

        for (String[] message : messages) {
            if (message[3].length() > longest[3].length()) {
                longest = message;
            }
        }

        System.out.println("Longest message: " + longest[3]);
    }

    // 3. Search by message ID
    public void searchByMessageID() {
        System.out.print("Enter message ID: ");
        String id = input.nextLine().trim();

        for (String[] message : messages) {
            if (message[0].equals(id)) {
                System.out.println("Recipient: " + message[2]);
                System.out.println("Message: " + message[3]);
                return;
            }
        }

        System.out.println("Message ID not found.");
    }

    // 4. Search by recipient
    public void searchByRecipient() {
        System.out.print("Enter recipient cellphone number: ");
        String recipient = input.nextLine().trim();
        boolean found = false;

        for (String[] message : messages) {
            if (message[2].equals(recipient)) {
                System.out.println("Message ID: " + message[0]);
                System.out.println("Message: " + message[3]);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No messages found for that recipient.");
        }
    }

    // 5. Delete a message using its hash
    public void deleteByHash() {
        System.out.print("Enter message hash: ");
        String hash = input.nextLine().trim();

        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i)[1].equals(hash)) {
                messages.remove(i);
                System.out.println("Message deleted successfully.");
                return;
            }
        }

        System.out.println("Message hash not found.");
    }

    // 6. Display a full report
    public void displayFullReport() {
        if (messages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }

        for (String[] message : messages) {
            System.out.println("Message ID: " + message[0]);
            System.out.println("Message Hash: " + message[1]);
            System.out.println("Recipient: " + message[2]);
            System.out.println("Message: " + message[3]);
            System.out.println("-------------------------");
        }
    }

    // Stored messages menu
    public void showMenu() {
        String choice;

        do {
            System.out.println("\n--- STORED MESSAGES ---");
            System.out.println("1. Display senders and recipients");
            System.out.println("2. Display longest message");
            System.out.println("3. Search by message ID");
            System.out.println("4. Search by recipient");
            System.out.println("5. Delete message by hash");
            System.out.println("6. Display full report");
            System.out.println("0. Return to main menu");
            System.out.print("Choose an option: ");

            choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> displaySendersAndRecipients();
                case "2" -> displayLongestMessage();
                case "3" -> searchByMessageID();
                case "4" -> searchByRecipient();
                case "5" -> deleteByHash();
                case "6" -> displayFullReport();
                case "0" -> System.out.println("Returning to main menu.");
                default -> System.out.println("Invalid option. Try again.");
            }
        } while (!choice.equals("0"));
    }
}
