package com.urfu.GorohSlot.commands;

import com.urfu.GorohSlot.bot.User;
import com.urfu.GorohSlot.chat.ChatController;
import com.urfu.GorohSlot.sender.SendAllThread;
import com.urfu.GorohSlot.sender.Sender;

public class CommandsChat {
    public static String smokeEnterCommand(User user) {
        ChatController.addUser(user);
        var message = String.format("*%s* приземляется в курилке!", user.getUserName());
        Sender.sendAllUsers(message, user, ChatController.chatUsers);
        user.getKeyboard().AddButtonOneLine("Стрельнуть");
        user.getKeyboard().AddButtonOneLine(Commands.exitSmoke);
        user.getKeyboard().AddButtonOneLine(Commands.status);
        user.getKeyboard().SaveKeyboard();
        return "Вы вошли в курилку";
    }

    public static String smokeExitCommand(User user) {
        return "Вы не в курилке";
    }

    public static String smokeStatusCommand() {
        return ChatController.getChatUsers();
    }
}
