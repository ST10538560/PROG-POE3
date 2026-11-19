import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @BeforeEach
    public void setUp() {
        Main.sentMessages.clear();
        Main.disregardedMessages.clear();
        Main.storedMessages.clear();
        Main.messageHashes.clear();
        Main.messageIDs.clear();

        Message msg1 = new Message(
            "1111111111",
            1,
            "John Smith",
            "+27821234567",
            "Hello World",
            "Stored"
        );

        Message msg2 = new Message(
            "2222222222",
            2,
            "Mary Jones",
            "+27829876543",
            "This is a longer test message",
            "Stored"
        );

        Main.storedMessages.add(msg1);
        Main.storedMessages.add(msg2);

        Main.messageIDs.add(msg1.getMessageID());
        Main.messageIDs.add(msg2.getMessageID());

        Main.messageHashes.add(msg1.getMessageHash());
        Main.messageHashes.add(msg2.getMessageHash());
    }

    @Test
    public void testShowSendersAndRecipients() {
        String result = Main.showSendersAndRecipients();

        assertTrue(result.contains("John Smith"));
        assertTrue(result.contains("+27821234567"));
        assertTrue(result.contains("Mary Jones"));
    }

    @Test
    public void testShowLongestStoredMessage() {
        String result = Main.showLongestStoredMessage();

        assertEquals(
            "This is a longer test message",
            result
        );
    }

    @Test
    public void testSearchByMessageID() {
        String result = Main.searchByMessageID("1111111111");

        assertTrue(result.contains("John Smith") == false);
        assertTrue(result.contains("+27821234567"));
        assertTrue(result.contains("Hello World"));
    }

    @Test
    public void testSearchByRecipient() {
        String result = Main.searchByRecipient("+27821234567");

        assertEquals("Hello World", result);
    }

    @Test
    public void testMessageIDNotFound() {
        String result = Main.searchByMessageID("9999999999");

        assertEquals("Message ID not found.", result);
    }

    @Test
    public void testRecipientNotFound() {
        String result = Main.searchByRecipient("+27000000000");

        assertEquals(
            "No messages found for this recipient.",
            result
        );
    }

    @Test
    public void testShowStoredReport() {
        String result = Main.showStoredReport();

        assertTrue(result.contains("Message ID:"));
        assertTrue(result.contains("Message Hash:"));
        assertTrue(result.contains("Hello World"));
        assertTrue(result.contains("Mary Jones"));
    }

    @Test
    public void testNoStoredMessages() {
        Main.storedMessages.clear();

        assertEquals(
            "No stored messages.",
            Main.showStoredReport()
        );
    }
}