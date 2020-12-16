package com.urfu.GorohSlot.chat;

import com.urfu.GorohSlot.bot.Bot;
import com.urfu.GorohSlot.bot.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class ChatController {
    // Главный список всех юзеров в чате
    public static ArrayList<User> chatUsers = new ArrayList<>();

    public static void writeMessage(String msg, User user) {
        var thread = new ChatNearlyAllThread(msg, user);
        thread.start();
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

    public static void handleMessage(String msg, User user) {
        var message = String.format("*%s*: %s", user.getUserName(), msg);
        sendNearlyAllUsers(message, user);
    }

    public static void sendAllUsers(String msg) {
        var bot = new Bot();
        for (var chatUser : chatUsers) {
            SendMessage sendMessage = new SendMessage().setChatId(chatUser.getUserId()).setText(msg).enableMarkdown(true);
            sendMessage.setReplyMarkup(chatUser.getKeyboard().getReplyKeyboardMarkup());
            try {
                bot.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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

    private static boolean isUserInChat(User user) {
        return chatUsers.contains(user);
    }

    private static boolean isExitCommand(String msg) {
        return msg.equals("Покинуть курилку");
    }

    private static boolean isStatusCommand(String msg) {
        return msg.equals("Кто в курилке?");
    }

    private static void sendNearlyAllUsers(String msg, User user) {
        var bot = new Bot();
        for (var chatUser : chatUsers) {
            if (!chatUser.equals(user)) {
                SendMessage sendMessage = new SendMessage().setChatId(chatUser.getUserId()).setText(msg).enableMarkdown(true);
                sendMessage.setReplyMarkup(chatUser.getKeyboard().getReplyKeyboardMarkup());
                try {
                    bot.execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
