package bot;

import commands.Commands;
import games.ChatController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Bot extends TelegramLongPollingBot {
    HashMap<String, User> userData = new HashMap<>();
    User user;

    private final String botToken = System.getenv("TOKEN");

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

            var savePath = String.format("%s!%s!%s", userName, userFirstName, userLastName);

            var file = new File(JsonHandler.getSavePath(savePath));
            if (userData.containsKey(userId)) {
                user = userData.get(userId);
            }
            else {
                if(file.exists()) {
                    try {
                        user = JsonHandler.readJSON(savePath);
                        userData.put(userId, user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    userData.putIfAbsent(userId, new User(userId, userFirstName, userLastName, userName, 100, 25));
                    user = userData.get(userId);
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
                sendMessage.setReplyMarkup(user.getReplyKeyboardMarkup());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            Logger.log(userFirstName, userLastName, userName, userId, messageText, sendMessageText);

            try {
                JsonHandler.writeJSON(user, savePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
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