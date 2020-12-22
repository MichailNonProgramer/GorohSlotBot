package com.urfu.GorohSlot.sender;

import com.urfu.GorohSlot.bot.Bot;
import com.urfu.GorohSlot.bot.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class Sender {
    public static void sendAllUsers(String msg, User user, ArrayList<User> list) {
        var bot = new Bot();
        for (var listUser : list) {
            if (!listUser.getUserId().equals(user.getUserId())) {
                SendMessage sendMessage = new SendMessage().setChatId(listUser.getUserId()).setText(msg).enableMarkdown(true);
                sendMessage.setReplyMarkup(listUser.getKeyboard().getReplyKeyboardMarkup());
                try {
                    bot.execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
