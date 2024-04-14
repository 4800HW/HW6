import java.util.Iterator;

public class driver {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();

        User user1 = new User("Alice", chatServer);
        User user2 = new User("Amr", chatServer);
        User user3 = new User("Lorenzo", chatServer);

        chatServer.registerUser(user1);
        chatServer.registerUser(user2);
        chatServer.registerUser(user3);

        //send msgs
        user1.sendMessage("Lorenzo", "Hey lorenzo can u send me my stuff");
        user3.sendMessage("Alice", "no");

        //undo msg
        user1.undoLastMessage();

        //Block
        user3.blockUser("Alice");

        //Blocked, message won't be sent
        user1.sendMessage("Lorenzo", "hey");

        user1.sendMessage("Amr", "yo amr");

        Iterator<Message> iterator = user1.viewChatHistory();
        System.out.println("---------------" + user1.getName() + " Chat History: ----------");
        while (iterator.hasNext()) {
            Message message = iterator.next();
            System.out.println("Sender: " + message.getSender());
            System.out.println("Recipient: " + message.getRecipient());
            System.out.println("Content: " + message.getContent());
            System.out.println("---------------------------");
        }
    }
}
