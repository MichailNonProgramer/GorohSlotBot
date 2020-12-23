package com.urfu.GorohSlot.bonus;

import com.urfu.GorohSlot.bot.Bot;
import com.urfu.GorohSlot.bot.User;
import com.urfu.GorohSlot.database.SQLHandler;
import com.urfu.GorohSlot.sender.Sender;

import java.util.ArrayList;

public class Bonus {
    private static final String bonusMessage = "Вам выдано 40 гривен";
    private static final int sumBonus = 10000;

    public static void giveBonus(){
        var listUsers = SQLHandler.getAllUsers();
        var nullUser = new User("0","0","0","0",0, 0, "0");
        addMoney(listUsers);
        Sender.sendAllUsers(bonusMessage, nullUser, listUsers);
    }

    private static void addMoney(ArrayList<User> listUsers){
        for (var user: listUsers) {
            user.AddMoney(sumBonus);
            SQLHandler.update(user);
            if (Bot.userData.containsKey(user.getUserId())) {
                    Bot.userData.put(user.getUserId(), user);
            }
        }
    }
}
