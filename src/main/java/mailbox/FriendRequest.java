package mailbox;

public class FriendRequest {
    private int fromId;
    private int toId;
    private int requestId;
    public FriendRequest(int requestId, int fromId, int toId){
        this.fromId = fromId;
        this.toId = toId;
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", requestId=" + requestId +
                '}';
    }

    public int getRequestId() {
        return requestId;
    }

    public int getFromId() {
        return fromId;
    }
    public int getToId() {
        return toId;
    }
}
