package commands;

import bot.User;
import games.*;


public class Commands {
    private final String mode3x3 = "Автомат 3x3";
    private final String mode5x4 = "Автомат 5x4";
    private final String dice = "Игра Кости";

    public String Command(String msg, User user, DiceMachine diceMachine) {
        user.keyBoardUpdate();
        if(msg.equals("/start") || msg.equals("Назад"))
            return startCommand(user);
        if(msg.equals("Выбор ставки"))
            return chooseBetCommand(user);
        if(msg.equals("Выбор режима"))
            return chooseModeCommand(user);
        if(msg.equals(mode3x3)
                || msg.equals(mode5x4)
                || msg.equals(dice))
            return setModeCommand(msg, user);
        if(Utils.isNumber(msg))
            return setBetCommand(user, msg);
        if(msg.equals("Пополнить счет"))
            return addBalanceCommand(user);
        if(msg.equals("Баланс"))
            return getBalanceCommand(user);
        if(msg.equals("Крути"))
            return spinCommand(user, diceMachine);
        else return "Дядя, ты дурак?";
    }

    private String spinCommand(User user, DiceMachine diceMachine) {
        if(user.getBalance() <= 0)
            return String.format("Пополните баланс.\nБаланс: %s%s",
                    user.getBalance(),
                    Emoji.dollar.getEmojiCode());

        var machine3x3 = new SlotsMachine3x3();
        var machine5x4 = new SlotsMachine4x5();
        var mode = user.getMode();

        try {
            return switch (mode) {
                case "3x3" -> machine3x3.makeSpin(user);
                case "5x4" -> machine5x4.makeSpin(user);
                case "Кости" -> diceMachine.makeThrow(user);
                default -> "Выберите режим";
            };
        }
        catch (Throwable e){
            return String.format("Ошибка %s", e);
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
        user.setMode(msg.split(" ")[1]);
        startCommand(user);
        return String.format("Выбран режим: %s.", msg);
    }

    private String chooseModeCommand(User user) {
        user.getKeyboardFirstRow().add(mode3x3);
        user.getKeyboardSecondRow().add(mode5x4);
        user.getKeyboardSecondRow().add(dice);
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