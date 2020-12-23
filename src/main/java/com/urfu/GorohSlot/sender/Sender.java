package com.urfu.GorohSlot.sender;

import com.urfu.GorohSlot.bot.Bot;
import com.urfu.GorohSlot.bot.User;
import com.urfu.GorohSlot.database.SQLHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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
                } catch (Exception e){
                    SQLHandler.deleteUsers(listUser);
                    Bot.userData.remove(listUser);
                    System.out.println(listUser.getUserName() + listUser.getUserLastName() + listUser.getUserFirstname() + "-Заблокировал наше чадо");
                }
            }
        }
    }
}
