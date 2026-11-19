import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testCheckMessageID() {
        Message msg = new Message(
            1, "John Smith", "+27821234567", "Hello World"
        );

        assertTrue(msg.checkMessageID());
        assertEquals(10, msg.getMessageID().length());
    }

    @Test
    public void testRecipientCell() {
        Message msg = new Message(
            1, "John Smith", "+27821234567", "Hello World"
        );

        assertEquals(
            "Cell phone number successfully captured.",
            msg.checkRecipientCell()
        );
    }

    @Test
    public void testInvalidRecipientCell() {
        Message msg = new Message(
            1, "John Smith", "0821234567", "Hello World"
        );

        assertEquals(
            "Cell phone number is incorrectly formatted.",
            msg.checkRecipientCell()
        );
    }

    @Test
    public void testMessageLength() {
        Message msg = new Message(
            1, "John Smith", "+27821234567", "Hello World"
        );

        assertEquals(
            "Message ready to send.",
            msg.checkMessageLength()
        );
    }

    @Test
    public void testMessageExceedsLength() {
        String longMessage = "A".repeat(251);

        Message msg = new Message(
            1, "John Smith", "+27821234567", longMessage
        );

        assertEquals(
            "Message exceeds 250 characters by 1; please reduce the size.",
            msg.checkMessageLength()
        );
    }

    @Test
    public void testMessageHash() {
        Message msg = new Message(
            "1234567890",
            1,
            "John Smith",
            "+27821234567",
            "Hello World",
            "Sent"
        );

        assertEquals("12:1:HELLOWORLD", msg.getMessageHash());
    }

    @Test
    public void testSendMessage() {
        Message msg = new Message(
            1, "John Smith", "+27821234567", "Hello World"
        );

        assertEquals(
            "Message successfully sent.",
            msg.SentMessage(1)
        );

        assertEquals("Sent", msg.getMessageStatus());
    }

    @Test
    public void testDisregardMessage() {
        Message msg = new Message(
            1, "John Smith", "+27821234567", "Hello World"
        );

        assertEquals(
            "Message successfully disregarded.",
            msg.SentMessage(2)
        );

        assertEquals("Disregarded", msg.getMessageStatus());
    }

    @Test
    public void testStoreMessage() {
        Message msg = new Message(
            1, "John Smith", "+27821234567", "Hello World"
        );

        assertEquals(
            "Message successfully stored.",
            msg.SentMessage(3)
        );

        assertEquals("Stored", msg.getMessageStatus());
    }

    @Test
    public void testInvalidSendOption() {
        Message msg = new Message(
            1, "John Smith", "+27821234567", "Hello World"
        );

        assertEquals(
            "Invalid option selected.",
            msg.SentMessage(4)
        );
    }
}