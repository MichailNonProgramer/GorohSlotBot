package com.urfu.GorohSlot.games.slots;

import com.urfu.GorohSlot.bot.User;

public class SlotsMachine4x5 extends SlotsMachine3x3{
    public final int rows = 4;
    public final int columns = 5;
    public final Slot[] slotsArr = SlotsConfig.slotsArr4x5;

    @Override
    public String makeSpin(User user) {
        var result = new StringBuilder();
        var table = createTable(rows, columns, slotsArr);
        var slotsPatterns = new SlotsPatterns(table);

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
