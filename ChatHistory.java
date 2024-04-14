import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatHistory implements IterableByUser {
    private List<Message> messages = new ArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }

    public Message getLastMessage() {
        if (messages.isEmpty()) {
            return null;
        }
        return messages.get(messages.size() - 1);
    }

    @Override
    public Iterator<Message> iterator(User user) {
        return new ChatHistoryIterator(messages, user);
    }

    private class ChatHistoryIterator implements Iterator<Message> {
        private int currentIndex = 0;
        private final List<Message> messages;
        private final User user;

        public ChatHistoryIterator(List<Message> messages, User user) {
            this.messages = messages;
            this.user = user;
        }

        @Override
        public boolean hasNext() {
            while (currentIndex < messages.size()) {
                Message message = messages.get(currentIndex);
                if (message.getSender().equals(user.getName()) || message.getRecipient().equals(user.getName())) {
                    return true;
                }
                currentIndex++;
            }
            return false;
        }

        @Override
        public Message next() {
            return messages.get(currentIndex++);
        }
    }
}