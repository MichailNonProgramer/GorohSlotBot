package com.urfu.GorohSlot.chat;

import com.urfu.GorohSlot.bot.User;
import com.urfu.GorohSlot.sender.SendAllThread;

import java.util.ArrayList;

public class ChatController {
    // Главный список всех юзеров в чате
    public static ArrayList<User> chatUsers = new ArrayList<>();

    public static String  writeMessage(String msg, User user) {
        var message = String.format("*%s*: %s", user.getUserName(), msg);
        var thread = new SendAllThread(message, user, chatUsers);
        thread.start();
        return "Загадочно молчит";
    }

    public static boolean isWriteMessage(String msg, User user) {
        return isUserInChat(user) & !isExitCommand(msg) & !isStatusCommand(msg);
    }

    public static void addUser(User user) {
        chatUsers.add(user);
    }

    public static void deleteUser(User user) {
        chatUsers.remove(user);
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

    private static boolean isUserInChat(User user) {
        return chatUsers.contains(user);
    }

    private static boolean isExitCommand(String msg) {
        return msg.equals("Покинуть курилку");
    }

    private static boolean isStatusCommand(String msg) {
        return msg.equals("Кто в курилке?");
    }
}
