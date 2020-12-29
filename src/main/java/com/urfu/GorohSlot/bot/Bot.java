package com.urfu.GorohSlot.bot;

import com.urfu.GorohSlot.chat.ChatController;
import com.urfu.GorohSlot.commands.Commands;
import com.urfu.GorohSlot.database.SQLHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class Bot extends TelegramLongPollingBot {
    public static HashMap<String, User> userData = new HashMap<>();
    User user;

    private final String botToken = System.getenv("TOKEN");

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chat = update.getMessage().getChat();
            var message = update.getMessage();
            var userFirstName = chat.getFirstName();
            var userLastName = chat.getLastName();
            var chatId = message.getChatId();
            var userName = chat.getUserName().isEmpty() ? getUsername(chatId) : chat.getUserName();
            var messageText = message.getText();
            var userId = chat.getId().toString();

            if (userData.containsKey(userId)) {
                user = userData.get(userId);
            }
            else {
                try {
                    user = SQLHandler.getUser(userId, userName, userFirstName, userLastName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            var sendMessageText = commandsHandler(messageText);
            if (!sendMessageText.equals("Загадочно молчит")) {
                SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(sendMessageText);
                sendMessage.setReplyMarkup(user.getKeyboard().getReplyKeyboardMarkup());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            Logger.log(userFirstName, userLastName, userName, userId, messageText, sendMessageText);
            SQLHandler.update(user);
            userData.put(userId, user);

        }
    }

    @Override
    public String getBotUsername() {
        return "@GorohSlotBot";
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private String commandsHandler(String msg) {
        return Commands.ExecuteCommand(msg, user);
    }

    private String getUsername(Long userId) {
        return "Аноним" + userId.toString().substring(0, 2);
    }
}