package commands;

import bot.User;
import games.*;

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
        user.keyBoardUpdate();
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
        user.getKeyboardFirstRow().add("Стрельнуть");
        user.getKeyboardFirstRow().add(exit);
        user.getKeyboardFirstRow().add(status);
        user.getKeyboard().add(user.getKeyboardFirstRow());
        user.getReplyKeyboardMarkup().setKeyboard(user.getKeyboard());
        return "Вы вошли в курилку";
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

    private String chooseModeCommand(User user) {
        user.getKeyboardFirstRow().add(mode3x3);
        user.getKeyboardFirstRow().add(smoke);
        user.getKeyboardSecondRow().add(mode5x4);
        user.getKeyboardSecondRow().add(dice);
        user.getKeyboardThirdRow().add(back);
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
        user.getKeyboardThirdRow().add(back);
        user.getKeyboard().add(user.getKeyboardFirstRow());
        user.getKeyboard().add(user.getKeyboardSecondRow());
        user.getKeyboard().add(user.getKeyboardThirdRow());
        user.getReplyKeyboardMarkup().setKeyboard(user.getKeyboard());
        return "Выберите сумму ставки.";
    }

    private String startCommand(User user) {
        user.getKeyboardFirstRow().add(spin);
        user.getKeyboardSecondRow().add(chooseBet);
        user.getKeyboardSecondRow().add(chooseMode);
        user.getKeyboardThirdRow().add(addBalance);
        user.getKeyboardThirdRow().add(balance);
        user.getKeyboard().add(user.getKeyboardFirstRow());
        user.getKeyboard().add(user.getKeyboardSecondRow());
        user.getKeyboard().add(user.getKeyboardThirdRow());
        user.getReplyKeyboardMarkup().setKeyboard(user.getKeyboard());
        return "Начинаем крутить?";
    }
}
