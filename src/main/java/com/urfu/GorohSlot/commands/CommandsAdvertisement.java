package com.urfu.GorohSlot.commands;

import com.urfu.GorohSlot.bot.User;
import com.urfu.GorohSlot.games.tools.Emoji;
import com.urfu.GorohSlot.games.tools.Utils;

public class CommandsAdvertisement {
    public static String advertEnterCommand(User user) {
        user.getKeyboard().AddButtonOneLine(Commands.exitAdvert);
        user.getKeyboard().SaveKeyboard();
        return String.format("""
                        %s      %sВы попали на одну из лучших рекламных площадок%s
                              Ваше сообщение могут увидеть миллионы
                              наших пользователей:
                                Цена - 1000%s за 1 символ
                                Отправляйте текст рекламы прямо сюда.
                                Баланс %s%s
                        %s""",
                Utils.repeat(Emoji.tilda.getEmojiCode(), 20),
                Emoji.machine.getEmojiCode(),
                Emoji.machine.getEmojiCode(),
                Emoji.dollar.getEmojiCode(),
                user.getBalance(),
                Emoji.dollar.getEmojiCode(),
                Utils.repeat(Emoji.tilda.getEmojiCode(), 20));
    }

    public static String advertExitCommand(User user) {
        user.setMode("");
        Commands.chooseModeCommand(user);
        return "Вы вышли из режима реклама";
    }
}
