package mailbox;



public class Message {
    private int messageId;
    private int fromId;
    private int toId;
    private String message;
    public Message(int messageId, int fromId, int toId, String message){
        this.messageId = messageId;
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getFromId(){
        return fromId;
    }
    public int getToId(){
        return toId;
    }
    public String getMessage(){
        return message;
    }

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
