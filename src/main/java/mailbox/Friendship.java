package mailbox;

public class Friendship {
    private int friendshipId;
    public Friendship(int friendshipId){
        this.friendshipId = friendshipId;
    }

    public int getFriendshipId() {
        return friendshipId;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "friendshipId=" + friendshipId +
                '}';
    }
}
