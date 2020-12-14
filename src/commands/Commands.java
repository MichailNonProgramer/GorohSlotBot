package commands;

import bot.User;
import games.*;

public class Commands {
    private final String mode3x3 = "Автомат 3x3";
    private final String mode5x4 = "Автомат 5x4";
    private final String dice = "Игра Кости";
    private final String smoke = "Режим Курилка";

    public String Command(String msg, User user) {
        user.keyBoardUpdate();
        if(msg.equals("Покинуть курилку"))
            return smokeExitCommand(user);
        if(msg.equals("Кто онлайн?"))
            return smokeStatusCommand();
        if(msg.equals("/start") || msg.equals("Назад"))
            return startCommand(user);
        if(msg.equals("Выбор ставки"))
            return chooseBetCommand(user);
        if(msg.equals("Выбор режима"))
            return chooseModeCommand(user);
        if(msg.equals(mode3x3)
                || msg.equals(mode5x4)
                || msg.equals(dice)
                || msg.equals(smoke))
            return setModeCommand(msg, user);
        if(Utils.isNumber(msg))
            return setBetCommand(user, msg);
        if(msg.equals("Пополнить счет"))
            return addBalanceCommand(user);
        if(msg.equals("Баланс"))
            return getBalanceCommand(user);
        if(msg.equals("Крути"))
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
        var message = String.format("%s приземляется в курилке!", user.getUserName());
        ChatController.sendAllUsers(message);
        user.getKeyboardFirstRow().add("Покинуть курилку");
        user.getKeyboardFirstRow().add("Кто онлайн?");
        user.getKeyboard().add(user.getKeyboardFirstRow());
        user.getReplyKeyboardMarkup().setKeyboard(user.getKeyboard());
        return "Вы вошли в курилку";
    }

    private String smokeExitCommand(User user) {
        ChatController.deleteUser(user);
        var message = String.format("%s покинул нас...", user.getUserName());
        ChatController.sendAllUsers(message);
        startCommand(user);
        return "Вы вышли из курилки";
    }

    private String smokeStatusCommand() {
        return ChatController.getChatUsers();
    }

    private String chooseModeCommand(User user) {
        user.getKeyboardFirstRow().add(mode3x3);
        user.getKeyboardFirstRow().add(smoke);
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
