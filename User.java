import java.util.Iterator;

public class User {
    private String name;
    private ChatServer chatServer;

    public User(String name, ChatServer chatServer) {
        this.name = name;
        this.chatServer = chatServer;
    }

    public void sendMessage(String recipient, String content) {
        chatServer.sendMessage(this, recipient, content);
    }

    public void receiveMessage(Message message) {
        System.out.println(name + " received a message from " + message.getSender() + ": " + message.getContent());
    }

    public void undoLastMessage() {
        chatServer.undoLastMessage(this);
    }

    public void blockUser(String userToBlock) {
        chatServer.blockUser(this, userToBlock);
    }

    public String getName() {
        return name;
    }

    public Iterator<Message> viewChatHistory() {
        return chatServer.getChatHistory(this);
    }
}