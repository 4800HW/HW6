import java.util.*;

public class ChatServer {
    private Map<String, User> users = new HashMap<>();
    private Map<User, ChatHistory> chatHistories = new HashMap<>();
    private Map<User, MessageMemento> lastMessages = new HashMap<>();
    private Map<User, Set<String>> blockedUsers = new HashMap<>();

    public void registerUser(User user) {
        users.put(user.getName(), user);
        chatHistories.put(user, new ChatHistory());
    }

    public void unregisterUser(User user) {
        users.remove(user.getName());
        chatHistories.remove(user);
    }

    public void sendMessage(User sender, String recipient, String content) {
        if (!users.containsKey(recipient)) {
            System.out.println("Message could not be sent. Recipient not found or blocked by sender.");
            return;
        }

        User recipientUser = users.get(recipient);
        if (isBlocked(recipientUser, sender.getName()))
        {
            return;
        }

        Message message = new Message(sender.getName(), recipient, content);
        users.get(recipient).receiveMessage(message);
        chatHistories.get(sender).addMessage(message);
        lastMessages.put(sender, new MessageMemento(message.getContent(), message.getTimestamp()));
    }

    public void undoLastMessage(User user) {
        if (lastMessages.containsKey(user)) {
            MessageMemento memento = lastMessages.get(user);
            System.out.println("Undoing last message sent by " + user.getName());
            System.out.println("Message content: " + memento.getContent());
            System.out.println("Message timestamp: " + memento.getTimestamp());
            lastMessages.remove(user);
        } else {
            System.out.println("No message to undo for " + user.getName());
        }
    }

    public void blockUser(User user, String userToBlock) {
        if (!users.containsKey(userToBlock)) {
            System.out.println("User to block not found.");
            return;
        }

        blockedUsers.computeIfAbsent(user, k -> new HashSet<>()).add(userToBlock);
        System.out.println(user.getName() + " blocked messages from " + userToBlock);
    }

    private boolean isBlocked(User sender, String recipient) {
        return blockedUsers.containsKey(sender) && blockedUsers.get(sender).contains(recipient);
    }

    public Iterator<Message> getChatHistory(User user) {
        ChatHistory chatHistory = chatHistories.get(user);
        if (chatHistory == null) {
            System.out.println("User " + user.getName() + " has no chat history.");
            return null;
        }
        return chatHistory.iterator(user);
    }
}