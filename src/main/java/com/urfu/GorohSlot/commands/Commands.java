package com.urfu.GorohSlot.commands;


import com.urfu.GorohSlot.advertisement.AdvertHandler;
import com.urfu.GorohSlot.bot.User;
import com.urfu.GorohSlot.games.dice.DiceController;
import com.urfu.GorohSlot.games.slots.*;
import com.urfu.GorohSlot.games.tools.Emoji;
import com.urfu.GorohSlot.games.tools.Utils;

public class Commands {
    public static final String mode3x3 = "Автомат 3x3";
    public static final String mode5x4 = "Автомат 5x4";
    public static final String dice = "Игра Кости";
    public static final String smoke = "Режим Курилка";
    public static final String advert = "Режим реклама";
    public static final String exitAdvert = "Вернуться в меню";
    public static final String status = "Кто в курилке?";
    public static final String exitSmoke = "Покинуть курилку";
    public static final String chooseBet = "Выбор ставки";
    public static final String chooseMode = "Выбор режима";
    public static final String back = "Назад";
    public static final String addBalance = "Пополнить счет";
    public static final String balance = "Баланс";
    public static final String spin = "Крути";

    public static String ExecuteCommand(String msg, User user) {
        user.getKeyboard().AddSetting();
        if(msg.equals(exitAdvert))
            return CommandsAdvertisement.advertExitCommand(user);
        if(user.getMode().equals(advert))
            return AdvertHandler.handleAdvertMessage(msg, user);
        if(msg.equals(exitSmoke))
            return CommandsChat.smokeExitCommand(user);
        if(msg.equals(status))
            return CommandsChat.smokeStatusCommand();
        if(msg.equals("/start") || msg.equals(back))
            return startCommand(user);
        if(msg.equals(chooseMode))
            return chooseModeCommand(user);
        if(msg.equals(chooseBet))
            return chooseBetCommand(user);
        if(msg.equals(mode3x3)
                || msg.equals(mode5x4)
                || msg.equals(dice)
                || msg.equals(smoke)
                || msg.equals(advert))
            return setModeCommand(msg, user);
        if(Utils.isNumber(msg))
            return setBetCommand(user, msg);
        if(msg.equals(addBalance))
            return addBalanceCommand(user);
        if(msg.equals(balance))
            return getBalanceCommand(user);
        if(msg.equals(spin))
            return spinCommand(user);
        return "Д̶͛͌я̵̓͊д̴̇̓я̴̆̽,̶̽̌" +
                " т҈̾́͐ы̵̐̐ д̸̉̈́у̶̀͊р" +
                "̵͗̅а̵̿̃к̶͊̃?̵̄̄";
    }

    public static String chooseModeCommand(User user) {
        user.getKeyboard().AddButtonOneLine(mode3x3);
        user.getKeyboard().AddButtonOneLine(smoke);
        user.getKeyboard().AddButtonTwoLine(mode5x4);
        user.getKeyboard().AddButtonTwoLine(dice);
        user.getKeyboard().AddButtonThreeLine(advert);
        user.getKeyboard().AddButtonThreeLine(back);
        user.getKeyboard().SaveKeyboard();
        return "Выберите режим игры.";
    }

    private static String spinCommand(User user) {
        if(user.getBalance() <= 0)
            return String.format("Пополните баланс.\nБаланс: %s%s",
                    user.getBalance(),
                    Emoji.dollar.getEmojiCode());
        if (user.getBalance() < user.getBet()) {
            return String.format("Ваш баланс ниже суммы ставки, пожалуйста, пополните баланс.\nБаланс: %s%s",
                    user.getBalance(),
                    Emoji.dollar.getEmojiCode());
        }

        var mode = user.getMode();

        try {
            switch (mode) {
                case mode3x3:
                    var machine3x3 = new SlotsMachine3x3();
                    return machine3x3.makeSpin(user);
                case mode5x4:
                    var machine5x4 = new SlotsMachine4x5();
                    return machine5x4.makeSpin(user);
                case dice:
                    return DiceController.tryPlay(user);
                default:
                    return "Выберите режим";
            }
        }
        catch (Throwable e){
            return String.format("Ошибка %s", e);
        }

    }

    private static String getBalanceCommand(User user) {
        return String.format("%s%s",
                user.getBalance(),
                Emoji.dollar.getEmojiCode());
    }

    private static String addBalanceCommand(User user) {
        var rnd = (int) (Math.random() * 500);
        user.AddMoney(rnd);
        return String.format("Держи %s%s",
                rnd,
                Emoji.dollar.getEmojiCode());
    }

    private static String setModeCommand(String msg, User user) {
        user.setMode(msg);
        if (msg.equals(smoke))
            return CommandsChat.smokeEnterCommand(user);
        if (msg.equals(advert))
            return CommandsAdvertisement.advertEnterCommand(user);
        startCommand(user);
        return String.format("Выбран режим: %s.", msg);
    }

    private static String setBetCommand(User user, String msg) {
        try {
            user.setBet(Integer.parseInt(msg));
        }
        catch (Exception e) {
            return String.format("Максимальная ставка: %s%s", Integer.MAX_VALUE, Emoji.dollar.getEmojiCode());
        }

        return String.format("Выбрана ставка: %s%s",
                msg,
                Emoji.dollar.getEmojiCode());
    }

    private static String chooseBetCommand(User user) {
        user.getKeyboard().AddButtonOneLine("10");
        user.getKeyboard().AddButtonOneLine("20");
        user.getKeyboard().AddButtonOneLine("30");
        user.getKeyboard().AddButtonTwoLine("50");
        user.getKeyboard().AddButtonTwoLine("100");
        user.getKeyboard().AddButtonTwoLine("250");
        user.getKeyboard().AddButtonThreeLine("500");
        user.getKeyboard().AddButtonThreeLine("1000");
        user.getKeyboard().AddButtonThreeLine(back);
        user.getKeyboard().SaveKeyboard();
        return "Выберите сумму ставки.";
    }

    private static String startCommand(User user) {
        user.getKeyboard().AddButtonOneLine(spin);
        user.getKeyboard().AddButtonTwoLine(chooseBet);
        user.getKeyboard().AddButtonTwoLine(chooseMode);
        user.getKeyboard().AddButtonThreeLine(addBalance);
        user.getKeyboard().AddButtonThreeLine(balance);
        user.getKeyboard().SaveKeyboard();
        return "Начинаем крутить?";
    }
}
