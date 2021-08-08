package mailbox;

import junit.framework.TestCase;
import org.junit.Test;

public class MailboxTest extends TestCase {
    FriendRequest newRequest = new FriendRequest(1,2,3);
    Friendship newShip = new Friendship(2);
    Message newMessage = new Message(1,2,3,"hello world");
    @Test
    public void testRequest(){
        assertEquals(newRequest.getRequestId(),1);
        assertEquals(newRequest.getFromId(),2);
        assertEquals(newRequest.getToId(),3);
        assertEquals(newRequest.toString(),"FriendRequest{fromId=2, toId=3, requestId=1}");
    }
    @Test
    public void testFriendship(){
        assertEquals(newShip.getFriendshipId(), 2);
        assertEquals(newShip.toString(), "Friendship{friendshipId=2}");
    }
    @Test
    public void testMessages(){
        assertEquals(newMessage.getMessageId(),1);
        assertEquals(newMessage.getFromId(),2);
        assertEquals(newMessage.getToId(), 3);
        assertEquals(newMessage.getMessage(),"hello world");
        assertEquals(newMessage.toString(),"Message{messageId=1, fromId=2, toId=3, Message='hello world'}");

    }
}
