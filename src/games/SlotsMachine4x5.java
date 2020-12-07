package games;

import bot.User;

import java.util.HashMap;

public class SlotsMachine4x5 extends SlotsMachine3x3{
    public final int rows = 4;
    public final int columns = 5;
    public final Slot[] slotsArr = SlotsConfig.slotsArr4x5;

    @Override
    public String makeSpin(User user) {
        var result = new StringBuilder();
        var table = createTable(rows, columns, slotsArr);
//        table = new Slot[][]{
//                {slotsArr[0], slotsArr[0], slotsArr[5], slotsArr[4], slotsArr[0]},
//                {slotsArr[4], slotsArr[5], slotsArr[1], slotsArr[5], slotsArr[1]},
//                {slotsArr[5], slotsArr[1], slotsArr[1], slotsArr[1], slotsArr[5]},
//                {slotsArr[0], slotsArr[1], slotsArr[1], slotsArr[1], slotsArr[5]}};
        var slotsPatterns = new SlotsPatterns(table);

        if (user.getBalance() < user.getBet()) {
            return String.format("Ваш баланс ниже суммы ставки, пожалуйста, пополните баланс.\nБаланс: %s%s",
                    user.getBalance(),
                    Emoji.dollar.getEmojiCode());
        }
        // С баланса снимается ставка
        user.TakeOffMoney(user.getBet());

        for (Slot[] slots : table) {
            result.append(slots[0].getCode());
            result.append(slots[1].getCode());
            result.append(slots[2].getCode());
            result.append(slots[3].getCode());
            result.append(slots[4].getCode());
            result.append("\n");
        }
        // проверка совпадений с паттернами и начисление денег за них
        slotsPatterns.CheckCollisionsAndAddMoney(user);

        var benefit = slotsPatterns.getBenefit();
        var winSlots = getWinSlots(benefit, slotsPatterns.getWinSlotsArr());

        return printFinalMessage(result, benefit, user, 5, winSlots);
    }
}