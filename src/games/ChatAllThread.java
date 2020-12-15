package games;

public class ChatAllThread extends Thread {
    public String message;

    public ChatAllThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        ChatController.sendAllUsers(message);
    }
}
