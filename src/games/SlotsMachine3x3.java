package games;

import bot.User;

import java.util.ArrayList;
import java.util.HashMap;


public class SlotsMachine3x3{
    public final int rows = 3;
    public final int columns = 3;
    public final Slot[] slotsArr = SlotsConfig.slotsArr3x3;
    private final ArrayList<String> winSlotsArr = new ArrayList<>();

    Slot[][] createTable(int rows, int columns, Slot[] slots) {
        var gameTable = new Slot[rows][columns];
        for (var i = 0; i < gameTable.length; i++) {
            for (var j = 0; j < gameTable[i].length; j++) {
                // Выбирается рандомное значение из массива
                gameTable[i][j] = slots[(int)Math.floor(Math.random() * slots.length)];
            }
        }
        return gameTable;
    }

    public String makeSpin(User user) {
        var table = createTable(rows, columns, slotsArr);

        if (user.getBalance() < user.getBet()) {
            return String.format("Ваш баланс ниже суммы ставки, пожалуйста, пополните баланс.\nБаланс: %s%s",
                    user.getBalance(),
                    Emoji.dollar.getEmojiCode());
        }

        var result = new StringBuilder();
        var benefit = 0;
        // С баланса снимается ставка
        user.TakeOffMoney(user.getBet());

        for (Slot[] slots : table) {
            result.append(slots[0].getCode());
            result.append(slots[1].getCode());
            result.append(slots[2].getCode());
            result.append("\n");
            if(slots[0] == slots[1] && slots[1] == slots[2]) {
                benefit = (int) (user.getBet() * slots[0].getMultiplier());
                winSlotsArr.add(slots[0].getCode());
                user.AddMoney(benefit);
            }
        }
        var winSlots = getWinSlots(benefit, winSlotsArr);

        return printFinalMessage(result, benefit, user, 3, winSlots);
    }

    String getWinSlots(int benefit, ArrayList<String> arr) {
        StringBuilder winSlots = new StringBuilder();
        for (String s : arr)
        {
            winSlots.append(s);
        }
        return (benefit == 0) ? Emoji.cross.getEmojiCode() : winSlots.toString();
    }

    String printFinalMessage(StringBuilder result, int benefit, User user, int count, String winSlot) {
        return repeat(Emoji.tilda.getEmojiCode(), count)
                + result
                + repeat(Emoji.tilda.getEmojiCode(), count)
                + String.format("\nВыигрыш: %s%s\nСтавка: %s%s\nБаланс: %s%s",
                benefit,
                Emoji.dollar.getEmojiCode(),
                user.getBet(),
                Emoji.dollar.getEmojiCode(),
                user.getBalance(),
                Emoji.dollar.getEmojiCode())
                + String.format("\nВыигрышные слоты: %s", winSlot);
    }

    String repeat(String s, int count) {
        return count > 0 ? s + repeat(s, --count) : "\n";
    }
}