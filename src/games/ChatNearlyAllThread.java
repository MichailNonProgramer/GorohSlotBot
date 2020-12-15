package games;

import bot.User;

public class ChatNearlyAllThread extends Thread {
    public String message;
    public User user;

    public ChatNearlyAllThread(String message, User user) {
        this.message = message;
        this.user = user;
    }

    @Override
    public void run() {
        ChatController.handleMessage(message, user);
    }
}
