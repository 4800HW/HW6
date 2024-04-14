import java.util.Iterator;

public class searchMessagesByUser implements Iterator<Message> {
    private Iterator<Message> iterator;
    private User user;
    private String username;

    public searchMessagesByUser(ChatHistory chatHistory, User user, String username) {
        this.iterator = chatHistory.iterator(user);
        this.user = user;
        this.username = username;
    }

    @Override
    public boolean hasNext() {
        while (iterator.hasNext()) {
            Message message = iterator.next();
            if (message.getSender().equals(username) || message.getRecipient().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Message next() {
        while (iterator.hasNext()) {
            Message message = iterator.next();
            if (message.getSender().equals(username) || message.getRecipient().equals(username)) {
                return message;
            }
        }
        return null;
    }
}