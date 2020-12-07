package games;

import bot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Utils {
    public static String repeat(String s, int count) {
        return count > 0 ? s + repeat(s, --count) : "\n";
    }

    public static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }

    public static void sendEvent(String chatId, String text){
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(Long.parseLong(chatId));
        try {
            new Bot().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}