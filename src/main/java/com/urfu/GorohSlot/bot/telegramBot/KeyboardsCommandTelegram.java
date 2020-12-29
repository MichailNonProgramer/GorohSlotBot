package com.urfu.GorohSlot.bot.telegramBot;

import com.urfu.GorohSlot.bot.User;
import com.urfu.GorohSlot.chat.ChatController;
import com.urfu.GorohSlot.commands.Commands;
import com.urfu.GorohSlot.commands.CommandsChat;

public class KeyboardsCommandTelegram {

    public static String chooseModeCommand(User user) {
        user.setKeyboardState(KeyboardStates.States.CHOOSEMODE.toString());
        user.getKeyboard().AddButtonOneLine(Commands.mode3x3);
        user.getKeyboard().AddButtonOneLine(Commands.smoke);
        user.getKeyboard().AddButtonTwoLine(Commands.mode5x4);
        user.getKeyboard().AddButtonTwoLine(Commands.dice);
        user.getKeyboard().AddButtonThreeLine(Commands.advert);
        user.getKeyboard().AddButtonThreeLine(Commands.back);
        user.getKeyboard().SaveKeyboard();
        return "Выберите режим игры.";
    }

    public static String chooseBetCommand(User user) {
        user.setKeyboardState(KeyboardStates.States.CHOOSEBET.toString());
        user.getKeyboard().AddButtonOneLine("10");
        user.getKeyboard().AddButtonOneLine("20");
        user.getKeyboard().AddButtonOneLine("30");
        user.getKeyboard().AddButtonTwoLine("50");
        user.getKeyboard().AddButtonTwoLine("100");
        user.getKeyboard().AddButtonTwoLine("250");
        user.getKeyboard().AddButtonThreeLine("500");
        user.getKeyboard().AddButtonThreeLine("1000");
        user.getKeyboard().AddButtonThreeLine(Commands.back);
        user.getKeyboard().SaveKeyboard();
        return "Выберите сумму ставки.";
    }

    public static String startCommand(User user) {
        clearModes(user);
        user.setKeyboardState(KeyboardStates.States.NONE.toString());
        user.getKeyboard().AddButtonOneLine(Commands.spin);
        user.getKeyboard().AddButtonTwoLine(Commands.chooseBet);
        user.getKeyboard().AddButtonTwoLine(Commands.chooseMode);
        user.getKeyboard().AddButtonThreeLine(Commands.addBalance);
        user.getKeyboard().AddButtonThreeLine(Commands.balance);
        user.getKeyboard().SaveKeyboard();
        return "Начинаем крутить?";
    }

    private static void clearModes(User user){
        checkAdvert(user);
        checkSmoke(user);
    }

    private static void checkAdvert(User user){
        if(user.getMode().equals(Commands.advert))
            user.setMode(Commands.start);
    }

    private static void checkSmoke(User user) {
        if (user.getMode().equals(Commands.smoke)) {
            user.setMode(Commands.start);
            ChatController.chatUsers.remove(user);
        }
    }
}
