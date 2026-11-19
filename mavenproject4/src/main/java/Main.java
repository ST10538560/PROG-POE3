import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// ==========================================
// 1. LOGIN CLASS (Part 1)
// ==========================================
class Login {
    String username;
    String password;
    String cellPhoneNumber;
    String firstName;
    String lastName;

    // Checks if username contains '_' and is <= 5 characters
    public boolean checkUserName() {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // Checks password complexity requirements
    public boolean checkPasswordComplexity() {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) hasCapital = true;
            if (Character.isDigit(ch)) hasNumber = true;
            if (!Character.isLetterOrDigit(ch)) hasSpecial = true;
        }

        return hasCapital && hasNumber && hasSpecial;
    }

    // Checks if cellphone number starts with international code (+27) and has correct length
    public boolean checkCellPhoneNumber() {
        return cellPhoneNumber != null && cellPhoneNumber.startsWith("+27") && cellPhoneNumber.length() == 12;
    }

    // Handles registration status messages
    public String registerUser() {
        String userMsg = checkUserName()
                ? "Username successfully captured."
                : "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

        String passMsg = checkPasswordComplexity()
                ? "Password successfully captured."
                : "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

        String cellMsg = checkCellPhoneNumber()
                ? "Cell phone number successfully added."
                : "Cell phone number incorrectly formatted or does not contain international code.";

        if (checkUserName() && checkPasswordComplexity() && checkCellPhoneNumber()) {
            return userMsg + "\n" + passMsg + "\n" + cellMsg;
        } else {
            return (!checkUserName() ? userMsg : (!checkPasswordComplexity() ? passMsg : cellMsg));
        }
    }

    // Verifies user login credentials
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username != null && this.password != null
                && this.username.equals(enteredUsername)
                && this.password.equals(enteredPassword);
    }

    // Returns formatted login welcome message
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}

// ==========================================
// 2. MESSAGE CLASS (Part 2 & Part 3)
// ==========================================
final class Message {

    private final String messageID;
    private final int numMessagesSent;
    private final String sender;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private String messageStatus;

    // Standard constructor (generates random 10-digit ID)
    public Message(int messageNumber, String sender,
                   String recipient, String messageText) {
        this.numMessagesSent = messageNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
        this.messageStatus = "";
    }

    // Overloaded constructor for test data / unit testing with explicit ID & status
    public Message(String messageID, int messageNumber, String sender,
                   String recipient, String messageText, String messageStatus) {
        this.messageID = messageID;
        this.numMessagesSent = messageNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
        this.messageStatus = messageStatus;
    }

    // Generate random 10-digit Message ID
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            sb.append(rand.nextInt(10));
        }

        return sb.toString();
    }

    // Check Message ID
    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    // Check recipient cellphone number
    public String checkRecipientCell() {
        if (recipient != null &&
                recipient.startsWith("+") &&
                recipient.length() <= 12) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted.";
    }

    // Check message length
    public String checkMessageLength() {
        if (messageText != null && messageText.length() <= 250) {
            return "Message ready to send.";
        }

        int excess = messageText == null ? 0 : messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    // Create Message Hash
    public String createMessageHash() {
        if (messageID == null || messageText == null ||
                messageText.trim().isEmpty()) {
            return "";
        }

        String firstTwoID = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String combinedWords = (firstWord + lastWord).toUpperCase();

        return firstTwoID + ":" + numMessagesSent + ":" + combinedWords;
    }

    // Send, disregard or store message
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                messageStatus = "Sent";
                return "Message successfully sent.";
            case 2:
                messageStatus = "Disregarded";
                return "Message successfully disregarded.";
            case 3:
                messageStatus = "Stored";
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    // Print message details
    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nSender: " + sender
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText
                + "\nStatus: " + messageStatus;
    }

    // Getters
    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageStatus() {
        return messageStatus;
    }
}

// ==========================================
// 3. MAIN APPLICATION CLASS (Part 2 & Part 3)
// ==========================================
public class Main {

    // PART 3: ARRAYS / LISTS (no hard-coding)
    static ArrayList<Message> sentMessages = new ArrayList<>();
    static ArrayList<Message> disregardedMessages = new ArrayList<>();
    static ArrayList<Message> storedMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIDs = new ArrayList<>();

    private static final String FILE_NAME = "stored_messages.json";

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Login user = new Login();

            // --- PART 1: User Registration ---
            System.out.println("==================================");
            System.out.println("       USER REGISTRATION");
            System.out.println("==================================");
            System.out.print("Enter first name: ");
            user.firstName = sc.nextLine();

            System.out.print("Enter last name: ");
            user.lastName = sc.nextLine();

            System.out.print("Enter username: ");
            user.username = sc.nextLine();

            System.out.print("Enter password: ");
            user.password = sc.nextLine();

            System.out.print("Enter cell phone number (+27...): ");
            user.cellPhoneNumber = sc.nextLine();

            System.out.println("\n" + user.registerUser());

            // --- PART 1: User Login ---
            System.out.println("\n==================================");
            System.out.println("           USER LOGIN");
            System.out.println("==================================");
            System.out.print("Enter username: ");
            String loginUser = sc.nextLine();

            System.out.print("Enter password: ");
            String loginPass = sc.nextLine();

            boolean isLoggedIn = user.loginUser(loginUser, loginPass);
            System.out.println("\n" + user.returnLoginStatus(isLoggedIn));

            // --- PART 2 & 3: QuickChat Application ---
            if (isLoggedIn) {
                loadStoredMessagesFromJson();

                System.out.println("\n==================================");
                System.out.println("     Welcome to QuickChat.");
                System.out.println("==================================");

                boolean exit = false;
                int totalSentCount = 0;
                int messageCounter = 0;

                while (!exit) {
                    System.out.println("\nPlease choose an option:");
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.println("4) Stored Messages");
                    System.out.print("Option: ");

                    int menuOption = sc.nextInt();
                    sc.nextLine(); // consume newline

                    switch (menuOption) {
                        case 1 -> {
                            System.out.print("\nHow many messages would you like to enter? ");
                            int numMessages = sc.nextInt();
                            sc.nextLine(); // consume newline

                            for (int i = 0; i < numMessages; i++) {
                                System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");

                                System.out.print("Enter Recipient Number: ");
                                String recipient = sc.nextLine();

                                System.out.print("Enter Message: ");
                                String msgText = sc.nextLine();

                                Message msg = new Message(
                                        ++messageCounter,
                                        user.firstName + " " + user.lastName,
                                        recipient,
                                        msgText
                                );

                                String lengthStatus = msg.checkMessageLength();
                                if (!lengthStatus.equals("Message ready to send.")) {
                                    System.out.println(lengthStatus);
                                    i--;
                                    continue;
                                }

                                System.out.println("\nChoose an action:");
                                System.out.println("1) Send Message");
                                System.out.println("2) Disregard Message");
                                System.out.println("3) Store Message to send later");
                                System.out.print("Selection: ");

                                int sendOption = sc.nextInt();
                                sc.nextLine();

                                messageHashes.add(msg.getMessageHash());
                                messageIDs.add(msg.getMessageID());

                                String resultMsg = msg.SentMessage(sendOption);
                                System.out.println("\n" + resultMsg);

                                switch (sendOption) {
                                    case 1 -> {
                                        sentMessages.add(msg);
                                        totalSentCount++;
                                        System.out.println("\n--- Message Details ---");
                                        System.out.println(msg.printMessages());
                                    }
                                    case 2 -> disregardedMessages.add(msg);
                                    case 3 -> {
                                        storedMessages.add(msg);
                                        saveStoredMessagesToJson();
                                    }
                                    default -> System.out.println("Invalid option.");
                                }
                            }

                            System.out.println("\nTotal messages sent in this session: " + totalSentCount);
                        }

                        case 2 -> System.out.println("Coming Soon.");

                        case 3 -> {
                            System.out.println("Exiting QuickChat. Goodbye!");
                            exit = true;
                        }

                        case 4 -> storedMessagesMenu(sc);

                        default -> System.out.println("Invalid option. Please try again.");
                    }
                }
            } else {
                System.out.println("Access denied. You must be logged in to access QuickChat.");
            }
        }
    }

    // Menu Option 4 sub-menu (2a to 2f)
    public static void storedMessagesMenu(Scanner sc) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- STORED MESSAGES ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a Message ID");
            System.out.println("d) Search stored messages by recipient");
            System.out.println("e) Delete a message using message hash");
            System.out.println("f) Display report of all stored messages");
            System.out.println("g) Back");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a" -> System.out.println("\n" + showSendersAndRecipients());
                case "b" -> System.out.println("\n" + showLongestStoredMessage());
                case "c" -> {
                    System.out.print("Enter Message ID: ");
                    System.out.println("\n" + searchByMessageID(sc.nextLine().trim()));
                }
                case "d" -> {
                    System.out.print("Enter Recipient: ");
                    System.out.println("\n" + searchByRecipient(sc.nextLine().trim()));
                }
                case "e" -> {
                    System.out.print("Enter Message Hash: ");
                    System.out.println("\n" + deleteByMessageHash(sc.nextLine().trim()));
                }
                case "f" -> System.out.println("\n" + showStoredReport());
                case "g" -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // 2a: Display sender and recipient of all stored messages
    public static String showSendersAndRecipients() {
        if (storedMessages.isEmpty()) return "No stored messages.";
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append("Sender: ").append(m.getSender())
              .append(" | Recipient: ").append(m.getRecipient())
              .append("\n");
        }
        return sb.toString().trim();
    }

    // 2b: Display the longest stored message
    public static String showLongestStoredMessage() {
        if (storedMessages.isEmpty()) return "No stored messages.";
        Message longest = storedMessages.get(0);
        for (Message m : storedMessages) {
            if (m.getMessageText().length() > longest.getMessageText().length()) {
                longest = m;
            }
        }
        return longest.getMessageText();
    }

    // 2c: Search for a message ID and display corresponding recipient and message
    public static String searchByMessageID(String id) {
        for (Message m : storedMessages) {
            if (m.getMessageID().equalsIgnoreCase(id)) {
                return "Recipient: " + m.getRecipient() + "\nMessage: " + m.getMessageText();
            }
        }
        return "Message ID not found.";
    }

    // 2d: Search for all messages stored for a particular recipient
    public static String searchByRecipient(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            if (m.getRecipient().equalsIgnoreCase(recipient)) {
                sb.append(m.getMessageText()).append("\n");
            }
        }
        return sb.length() == 0 ? "No messages found for this recipient." : sb.toString().trim();
    }

    // 2e: Delete a message using the message hash
    public static String deleteByMessageHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            if (m.getMessageHash().equalsIgnoreCase(hash)) {
                String text = m.getMessageText();
                storedMessages.remove(i);
                messageHashes.remove(m.getMessageHash());
                messageIDs.remove(m.getMessageID());
                saveStoredMessagesToJson();
                return "Message: \"" + text + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // 2f: Display a report that lists full details of all stored messages
    public static String showStoredReport() {
        if (storedMessages.isEmpty()) return "No stored messages.";
        StringBuilder report = new StringBuilder("=== STORED MESSAGES REPORT ===\n");
        for (Message m : storedMessages) {
            report.append(m.printMessages()).append("\n----------------------\n");
        }
        return report.toString().trim();
    }

    // Save storedMessages list to stored_messages.json
    public static void saveStoredMessagesToJson() {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            json.append("  {\n");
            json.append("    \"messageID\": \"").append(cleanJson(m.getMessageID())).append("\",\n");
            json.append("    \"sender\": \"").append(cleanJson(m.getSender())).append("\",\n");
            json.append("    \"recipient\": \"").append(cleanJson(m.getRecipient())).append("\",\n");
            json.append("    \"messageText\": \"").append(cleanJson(m.getMessageText())).append("\",\n");
            json.append("    \"messageStatus\": \"").append(cleanJson(m.getMessageStatus())).append("\"\n");
            json.append("  }").append(i < storedMessages.size() - 1 ? "," : "").append("\n");
        }
        json.append("]");

        try (FileWriter fw = new FileWriter(FILE_NAME)) {
            fw.write(json.toString());
        } catch (IOException e) {
            System.out.println("Could not save JSON: " + e.getMessage());
        }
    }

    // Read stored_messages.json into storedMessages list
    public static void loadStoredMessagesFromJson() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        storedMessages.clear();
        try {
            String content = Files.readString(file.toPath()).trim();
            if (content.length() <= 2) return;

            String inner = content.substring(1, content.length() - 1).trim();
            if (inner.isEmpty()) return;

            String[] items = inner.split("\\},\\s*\\{");
            int num = 1;

            for (String item : items) {
                String obj = item.trim();
                if (!obj.startsWith("{")) obj = "{" + obj;
                if (!obj.endsWith("}")) obj = obj + "}";

                String id = getValue(obj, "messageID");
                String sender = getValue(obj, "sender");
                String recipient = getValue(obj, "recipient");
                String text = getValue(obj, "messageText");
                String status = getValue(obj, "messageStatus");

                Message m = new Message(
                        id,
                        num++,
                        sender,
                        recipient,
                        text,
                        status.isEmpty() ? "Stored" : status
                );
                storedMessages.add(m);
                if (!messageIDs.contains(m.getMessageID())) {
                    messageIDs.add(m.getMessageID());
                }
                if (!messageHashes.contains(m.getMessageHash())) {
                    messageHashes.add(m.getMessageHash());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load JSON: " + e.getMessage());
        }
    }

    private static String cleanJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String getValue(String obj, String key) {
        String pattern = "\"" + key + "\"";
        int k = obj.indexOf(pattern);
        if (k == -1) return "";
        int colon = obj.indexOf(":", k + pattern.length());
        int start = obj.indexOf("\"", colon + 1) + 1;
        int end = obj.indexOf("\"", start);
        if (start <= 0 || end == -1) return "";
        return obj.substring(start, end).replace("\\\"", "\"").replace("\\\\", "\\");
    }
}