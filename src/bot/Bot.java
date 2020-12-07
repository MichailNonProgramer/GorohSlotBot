package bot;

import commands.Commands;
import  games.DiceMachine;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;
import java.util.HashMap;

public class Bot extends TelegramLongPollingBot {
    DiceMachine diceMachine = new DiceMachine();
    HashMap<String, User> userData = new HashMap<>();
    String userId;
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
            userId = chat.getId().toString();

            if (userData.containsKey(userId)) {
                user = userData.get(userId);
            }
            else {
                try {
                    user = SQLHandler.getUser(userId, userName, userFirstName, userLastName);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

            String sendMessageText = commandsHandler(messageText);
            SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(sendMessageText);
            sendMessage.setReplyMarkup(user.getKeyboard().getReplyKeyboardMarkup());

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
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
        return new Commands().Command(msg, user, diceMachine);
    }
}