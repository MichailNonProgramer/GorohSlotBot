package com.urfu.GorohSlot.commands;


import com.urfu.GorohSlot.advertisement.AdvertHandler;
import com.urfu.GorohSlot.bot.User;
import com.urfu.GorohSlot.bot.telegramBot.KeyboardStates;
import com.urfu.GorohSlot.bot.telegramBot.KeyboardsCommandTelegram;
import com.urfu.GorohSlot.chat.ChatController;
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
    public static final String start = "/start";
    public static final String defaultMsg = "Д̶͛͌я̵̓͊д̴̇̓я̴̆̽,̶̽̌" +
            " т҈̾́͐ы̵̐̐ д̸̉̈́у̶̀͊р" +
            "̵͗̅а̵̿̃к̶͊̃?̵̄̄";

    public static String ExecuteCommand(String msg, User user) {
        user.getKeyboard().AddSetting();
        if (user.getKeyboardState().equals(KeyboardStates.States.ADVERTMODE.toString())) {
            return switch (msg) {
                case exitAdvert -> CommandsAdvertisement.advertExitCommand(user);
                case start -> KeyboardsCommandTelegram.startCommand(user);
                default -> AdvertHandler.handleAdvertMessage(msg, user);
            };
        }
        if (user.getKeyboardState().equals(KeyboardStates.States.CHATMODE.toString())) {
            return switch (msg) {
                case exitSmoke -> CommandsChat.smokeExitCommand(user);
                case status -> CommandsChat.smokeStatusCommand();
                case start -> KeyboardsCommandTelegram.startCommand(user);
                default -> ChatController.writeMessage(msg, user);
            };
        }
        if (user.getKeyboardState().equals(KeyboardStates.States.CHOOSEMODE.toString())) {
            return switch (msg) {
                case mode3x3, mode5x4, dice, advert, smoke -> setModeCommand(msg, user);
                case back, start -> KeyboardsCommandTelegram.startCommand(user);
                default -> defaultMsg;
            };
        }
        if (user.getKeyboardState().equals(KeyboardStates.States.CHOOSEBET.toString())) {
            return switch (msg) {
                case back, start -> KeyboardsCommandTelegram.startCommand(user);
                default -> Utils.isNumber(msg) ? setBetCommand(user, msg) : defaultMsg;
            };
        }
        return switch (msg) {
            case chooseMode -> KeyboardsCommandTelegram.chooseModeCommand(user);
            case chooseBet -> KeyboardsCommandTelegram.chooseBetCommand(user);
            case addBalance -> addBalanceCommand(user);
            case balance -> getBalanceCommand(user);
            case spin -> spinCommand(user);
            case start -> KeyboardsCommandTelegram.startCommand(user);
            default -> defaultMsg;
        };
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
        if (msg.equals(smoke)) {
            user.setKeyboardState(KeyboardStates.States.CHATMODE.toString());
            return CommandsChat.smokeEnterCommand(user);
        }
        if (msg.equals(advert)) {
            user.setKeyboardState(KeyboardStates.States.ADVERTMODE.toString());
            return CommandsAdvertisement.advertEnterCommand(user);
        }
        KeyboardsCommandTelegram.startCommand(user);
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


}
