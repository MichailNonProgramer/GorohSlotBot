package commands;

import bot.User;
import slotsMachine.Emoji;
import slotsMachine.SlotsMachine3x3;
import slotsMachine.SlotsMachine4x5;

import java.util.HashMap;

public class Commands {
    public boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) return false;
        }
        return true;
    }

    public String Command(String msg, User user, HashMap<String, User> userData){
        user.keyBoardUpdate();
        if(msg.equals("/start") || msg.equals("Назад"))
            return startCommand(user);
        if(msg.equals("Выбор ставки"))
            return chooseBetCommand(user);
        if(msg.equals("Выбор режима"))
            return chooseModeCommand(user);
        if(msg.equals("Автомат 3x3") || msg.equals("Автомат 5x4"))
            return setModeCommand(msg, user);
        if(isNumber(msg))
            return setBetCommand(user, msg);
        if(msg.equals("Пополнить счет"))
            return addBalanceCommand(user);
        if(msg.equals("Баланс"))
            return getBalanceCommand(user);
        if(msg.equals("Крути"))
            return spinCommand(user, userData);
        else return "Дядя, ты дурак?";
    }

    private String spinCommand(User user, HashMap<String,User> userData) {
        if(user.getBalance() <= 0)
            return String.format("Пополните баланс.\nБаланс: %s%s",
                    user.getBalance(),
                    Emoji.dollar.getEmojiCode());

        var machine3x3 = new SlotsMachine3x3();
        var machine5x4 = new SlotsMachine4x5();
        var mode = user.getMode();

        try {
            if(mode.equals("3x3"))
                return machine3x3.makeSpin(user.getUserId(), userData);
            else if(mode.equals("5x4"))
                return machine5x4.makeSpin(user.getUserId(), userData);
            else
                return "Выберите режим";
        }
        catch (Throwable e){
            return "Выберите режим";
        }

    }

    private String getBalanceCommand(User user) {
        return String.format("%s%s",
                user.getBalance(),
                Emoji.dollar.getEmojiCode());
    }

    private String addBalanceCommand(User user) {
        var rnd = (int) (Math.random() * 500);
        user.AddMoney(rnd);
        return String.format("Держи %s%s",
                rnd,
                Emoji.dollar.getEmojiCode());
    }

    private String setModeCommand(String msg, User user) {
        user.getKeyboardFirstRow().add("Автомат 3x3");
        user.getKeyboardSecondRow().add("Автомат 5x4");
        user.getKeyboardThirdRow().add("Назад");
        user.getKeyboard().add(user.getKeyboardFirstRow());
        user.getKeyboard().add(user.getKeyboardSecondRow());
        user.getKeyboard().add(user.getKeyboardThirdRow());
        user.getReplyKeyboardMarkup().setKeyboard(user.getKeyboard());
        user.setMode(msg.split(" ")[1]);
        return String.format("Выбран режим: %s.", msg);
    }

    private String chooseModeCommand(User user) {
        user.getKeyboardFirstRow().add("Автомат 3x3");
        user.getKeyboardSecondRow().add("Автомат 5x4");
        user.getKeyboardThirdRow().add("Назад");
        user.getKeyboard().add(user.getKeyboardFirstRow());
        user.getKeyboard().add(user.getKeyboardSecondRow());
        user.getKeyboard().add(user.getKeyboardThirdRow());
        user.getReplyKeyboardMarkup().setKeyboard(user.getKeyboard());
        return "Выберите режим игры.";
    }

    private String setBetCommand(User user, String msg) {
        user.setBet(Integer.parseInt(msg));
        return String.format("Выбрана ставка: %s%s",
                msg,
                Emoji.dollar.getEmojiCode());
    }

    private String chooseBetCommand(User user) {
        user.getKeyboardFirstRow().add("10");
        user.getKeyboardFirstRow().add("20");
        user.getKeyboardFirstRow().add("30");
        user.getKeyboardSecondRow().add("50");
        user.getKeyboardSecondRow().add("100");
        user.getKeyboardSecondRow().add("250");
        user.getKeyboardThirdRow().add("500");
        user.getKeyboardThirdRow().add("1000");
        user.getKeyboardThirdRow().add("Назад");
        user.getKeyboard().add(user.getKeyboardFirstRow());
        user.getKeyboard().add(user.getKeyboardSecondRow());
        user.getKeyboard().add(user.getKeyboardThirdRow());
        user.getReplyKeyboardMarkup().setKeyboard(user.getKeyboard());
        return "Выберите сумму ставки.";
    }

    private String startCommand(User user) {
        user.getKeyboardFirstRow().add("Крути");
        user.getKeyboardSecondRow().add("Выбор ставки");
        user.getKeyboardSecondRow().add("Выбор режима");
        user.getKeyboardThirdRow().add("Пополнить счет");
        user.getKeyboardThirdRow().add("Баланс");
        user.getKeyboard().add(user.getKeyboardFirstRow());
        user.getKeyboard().add(user.getKeyboardSecondRow());
        user.getKeyboard().add(user.getKeyboardThirdRow());
        user.getReplyKeyboardMarkup().setKeyboard(user.getKeyboard());
        return "Начинаем крутить?";
    }
}
