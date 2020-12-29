package com.urfu.GorohSlot.advertisement;

import com.urfu.GorohSlot.bot.User;
import com.urfu.GorohSlot.database.SQLHandler;
import com.urfu.GorohSlot.games.tools.Emoji;
import com.urfu.GorohSlot.games.tools.Utils;
import com.urfu.GorohSlot.sender.SendAllThread;

public class AdvertHandler {
    public static final int costOneSymbol = 1000;
    public static final int symbolLimit = 3500;

    public static String handleAdvertMessage(String msg, User user) {
        if (checkLength(msg))
            return "Максимальная длина сообщения 3500 символов.";

        var cost = msg.length() * costOneSymbol;

        if (cost > user.getBalance()) {
            var userMaxSymbols = (int) (user.getBalance() / costOneSymbol);
            return String.format("С вашим балансом максимальная длина текста: %s",
                    Math.min(userMaxSymbols, symbolLimit));
        }
        var userList = SQLHandler.getAllUsers();
        var finalMessage = String.format("%sРЕКЛАМНАЯ ВЕСТЬ%s\n%s",
                Emoji.attention.getEmojiCode(), Emoji.attention.getEmojiCode(),
                Utils.repeat(Emoji.tilda.getEmojiCode(), 8))
                + msg;
        var thread = new SendAllThread(finalMessage, user, userList);
        thread.start();
        user.TakeOffMoney(cost);
        return String.format("Сообщение отправлено всем в казино за %s%s", cost, Emoji.dollar.getEmojiCode());
    }

    private static boolean checkLength(String msg) {
        return msg.length() > symbolLimit;
    }
}
