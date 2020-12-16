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

    private final String botToken = "1386672879:AAGgWbEIPyxfd1e8ObdU7AHo1Xc_o7piass";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var chat = update.getMessage().getChat();
            var message = update.getMessage();
            var userFirstName = chat.getFirstName();
            var userLastName = chat.getLastName();
            var userName = chat.getUserName();
            var chatId = message.getChatId();
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

            String sendMessageText;
            if (ChatController.isWriteMessage(messageText, user)) {
                sendMessageText = "Загадочно молчит";
                ChatController.writeMessage(messageText, user);
            }
            else {
                sendMessageText = commandsHandler(messageText);
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
        return new Commands().Command(msg, user);
    }
}