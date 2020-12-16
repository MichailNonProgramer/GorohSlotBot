package com.urfu.GorohSlot.commands;


import com.urfu.GorohSlot.bot.User;
import com.urfu.GorohSlot.chat.ChatAllThread;
import com.urfu.GorohSlot.chat.ChatController;
import com.urfu.GorohSlot.games.dice.DiceController;
import com.urfu.GorohSlot.games.slots.*;
import com.urfu.GorohSlot.games.tools.Emoji;
import com.urfu.GorohSlot.games.tools.Utils;

public class Commands {
    private final String mode3x3 = "Автомат 3x3";
    private final String mode5x4 = "Автомат 5x4";
    private final String dice = "Игра Кости";
    private final String smoke = "Режим Курилка";
    private final String status = "Кто в курилке?";
    private final String exit = "Покинуть курилку";
    private final String chooseBet = "Выбор ставки";
    private final String chooseMode = "Выбор режима";
    private final String back = "Назад";
    private final String addBalance = "Пополнить счет";
    private final String balance = "Баланс";
    private final String spin = "Крути";

    public String Command(String msg, User user) {
        user.getKeyboard().AddSetting();
        if(msg.equals(exit))
            return smokeExitCommand(user);
        if(msg.equals(status))
            return smokeStatusCommand();
        if(msg.equals("/start") || msg.equals(back))
            return startCommand(user);
        if(msg.equals(chooseBet))
            return chooseBetCommand(user);
        if(msg.equals(chooseMode))
            return chooseModeCommand(user);
        if(msg.equals(mode3x3)
                || msg.equals(mode5x4)
                || msg.equals(dice)
                || msg.equals(smoke))
            return setModeCommand(msg, user);
        if(Utils.isNumber(msg))
            return setBetCommand(user, msg);
        if(msg.equals(addBalance))
            return addBalanceCommand(user);
        if(msg.equals(balance))
            return getBalanceCommand(user);
        if(msg.equals(spin))
            return spinCommand(user);
        return "Д̶̳͙̥̫͇̣͍͛͌̈́̅̂͆̂̅͊я̵̩͓̬͍̙̞̤̠͇͎̠̙̓͊̓̊͌̀̀д̴̟̰̲̠͙͉̇̓͛̌̎̄я̴̠̯͔͕̤̗̠̬̲̆̽̉̏̊̐̄͆,̶̯̩̤͖͍̽̌̇͌̇̍̀̌̆̈́͐̍ͅͅ т҈̰͍͍̲͔̬̗͔͕̟̾́͐͂̔̓̿́͆̓ͅͅы̵͔̭̣͓̞͕̝̙̖̐̐͆͐̌̽̇̏̎͊͗ д̸̮̪͕͓̩̉̈́̅̽͛̆̋̐̉̇͑у̶͇̰̜͖̤̀͊̌͌̅̈̊̑̒̔р̵̘̣̙̮̦͖͗̅͑̄̀̉а̵͖̖̜̖̝̳̗̿̃͋̈̾̈́̄͗̌ͅк̶̰̘̲̥͙͉͓͊̃́̅͛̓͊̉̈́̾̈?̵̳͓͚̪̤̠̳̲̞̄̄̆͑̿̏́͐́̋̚ͅ";
    }

    private String spinCommand(User user) {
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

    private String smokeExitCommand(User user) {
        if (ChatController.chatUsers.contains(user)) {
            ChatController.deleteUser(user);
            var message = String.format("*%s* покинул нас...", user.getUserName());
            var thread = new ChatAllThread(message);
            thread.start();
            chooseModeCommand(user);
            return "Вы вышли из курилки";
        }
        chooseModeCommand(user);
        return "Заходи по новой";
    }

    private String smokeStatusCommand() {
        return ChatController.getChatUsers();
    }

    private String setModeCommand(String msg, User user) {
        user.setMode(msg);
        if (msg.equals(smoke))
            return smokeEnterCommand(user);
        startCommand(user);
        return String.format("Выбран режим: %s.", msg);
    }

    private String smokeEnterCommand(User user) {
        ChatController.addUser(user);
        var message = String.format("*%s* приземляется в курилке!", user.getUserName());
        ChatController.sendAllUsers(message);
        user.getKeyboard().AddButtonOneLine("Стрельнуть");
        user.getKeyboard().AddButtonOneLine(exit);
        user.getKeyboard().AddButtonOneLine(status);
        user.getKeyboard().SaveKeyboard();
        return "Вы вошли в курилку";
    }

    private String chooseModeCommand(User user) {
        user.getKeyboard().AddButtonOneLine(mode3x3);
        user.getKeyboard().AddButtonOneLine(smoke);
        user.getKeyboard().AddButtonTwoLine(mode5x4);
        user.getKeyboard().AddButtonTwoLine(dice);
        user.getKeyboard().AddButtonThreeLine(back);
        user.getKeyboard().SaveKeyboard();
        return "Выберите режим игры.";
    }

    private String setBetCommand(User user, String msg) {
        user.setBet(Integer.parseInt(msg));
        return String.format("Выбрана ставка: %s%s",
                msg,
                Emoji.dollar.getEmojiCode());
    }

    private String chooseBetCommand(User user) {
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

    private String startCommand(User user) {
        user.getKeyboard().AddButtonOneLine(spin);
        user.getKeyboard().AddButtonTwoLine(chooseBet);
        user.getKeyboard().AddButtonTwoLine(chooseMode);
        user.getKeyboard().AddButtonThreeLine(addBalance);
        user.getKeyboard().AddButtonThreeLine(balance);
        user.getKeyboard().SaveKeyboard();
        return "Начинаем крутить?";
    }
}
