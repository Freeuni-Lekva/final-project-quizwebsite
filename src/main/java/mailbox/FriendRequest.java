package mailbox;

public class FriendRequest {
    private long fromId;
    private long toId;
    private long requestId;

    public FriendRequest(long requestId, long fromId, long toId){
        this.fromId = fromId;
        this.toId = toId;
        this.requestId = requestId;
    }

    public FriendRequest(long fromId, long toId){
        this.fromId = fromId;
        this.toId = toId;
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", requestId=" + requestId +
                '}';
    }

    public long getRequestId() {
        return requestId;
    }
    public long getFromId() {
        return fromId;
    }
    public long getToId() {
        return toId;
    }
}
