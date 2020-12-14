package games;

import bot.User;

import java.util.ArrayList;

public class ChatController {
    public static ArrayList<User> chatUsers = new ArrayList<>();

    public static boolean isUserInChat(User user) {
        return chatUsers.contains(user);
    }

    public static boolean isExitCommand(String msg) {
        return msg.equals("Покинуть курилку");
    }

    public static boolean isStatusCommand(String msg) {
        return msg.equals("Кто онлайн?");
    }

    public static void addUser(User user) {
        chatUsers.add(user);
    }

    public static void deleteUser(User user) {
        chatUsers.remove(user);
    }

    public static void handleMessage(String msg, User user) {
        var message = String.format("%s: %s", user.getUserName(), msg);
        sendNearlyAllUsers(message, user);
    }

    public static void sendAllUsers(String msg) {
        for (var chatUser : chatUsers) {
            Utils.sendEvent(chatUser.getUserId(), msg);
        }
    }

    public static String getChatUsers() {
        var sb = new StringBuilder();
        for (var chatUser : chatUsers) {
            sb.append(chatUser.getUserName());
            sb.append("\n");
        }
        var mes = sb.toString();
        return mes.equals("") ? "Перезайдите в курилку" : mes;
    }

    private static void sendNearlyAllUsers(String msg, User user) {
        for (var chatUser : chatUsers) {
            if (!chatUser.equals(user))
                Utils.sendEvent(chatUser.getUserId(), msg);
        }
    }
}
