package com.urfu.GorohSlot;

import com.urfu.GorohSlot.bonus.TimerBonus;
import com.urfu.GorohSlot.bot.Bot;
import com.urfu.GorohSlot.database.SQLHandler;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] arg){
        ApiContextInitializer.init();
        TelegramBotsApi telegram = new TelegramBotsApi();
        TimerBonus.start();
        Bot bot = new Bot();
        SQLHandler.createChatUsers();
        try {
            telegram.registerBot(bot);
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}