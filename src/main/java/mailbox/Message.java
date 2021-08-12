package mailbox;

import java.sql.Timestamp;

public class Message {
    private long messageId;
    private long fromId;
    private long toId;
    private String message;
    private Timestamp sentTime;

    public Message(long messageId, long fromId, long toId, String message, Timestamp sentTime){
        this.messageId = messageId;
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.sentTime = sentTime;
    }

    public Message(long fromId, long toId, String message, Timestamp sentTime){
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
        this.sentTime = sentTime;
    }

    public long getMessageId() {
        return messageId;
    }

    public long getFromId(){
        return fromId;
    }
    public long getToId(){
        return toId;
    }
    public String getMessage(){
        return message;
    }
    public Timestamp getSentTime() { return sentTime; }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", Message='" + message + '\'' +
                '}';
    }
}
