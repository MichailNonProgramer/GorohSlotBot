package games;

import bot.SQLHandler;
import bot.User;

import java.util.ArrayList;

public class DiceMachine {
    public String makeThrow(User user1, User user2) {
        var user1Values = generateValues();
        var user1Sum = solveSum(user1Values);
        var user2Values = generateValues();
        var user2Sum = solveSum(user2Values);

        user1.TakeOffMoney(user1.getBet());
        user2.TakeOffMoney(user2.getBet());

        var isFirstWinner = user1Sum > user2Sum;

        var winnerPrefix = String.format("%sТы выиграл %s%s!%s\n",
                Emoji.sign.getEmojiCode(),
                isFirstWinner ? user1.getBet() * 2 : user2.getBet() * 2,
                Emoji.dollar.getEmojiCode(),
                Emoji.sign.getEmojiCode());
        var looserPrefix = String.format("%sТы проиграл %s%s!%s\n",
                Emoji.cross.getEmojiCode(),
                isFirstWinner ? user2.getBet() : user1.getBet(),
                Emoji.dollar.getEmojiCode(),
                Emoji.cross.getEmojiCode());

        var finalMessage = String.format("%s: %s %s (%s, %s, %s)",
                user1.getUserName(),  user1Sum, pluralize(user1Sum),
                user1Values.get(0), user1Values.get(1), user1Values.get(2))
                + "\n\n" + String.format("%s: %s %s (%s, %s, %s)",
                user2.getUserName(), user2Sum, pluralize(user2Sum),
                user2Values.get(0), user2Values.get(1), user2Values.get(2));

        var count = 12;
        var finalWinnerMessage = Utils.repeat(Emoji.tilda.getEmojiCode(), count)
                + winnerPrefix + "\n" + finalMessage
                + "\n" + Utils.repeat(Emoji.tilda.getEmojiCode(), count);
        var finalLooserMessage = Utils.repeat(Emoji.tilda.getEmojiCode(), count)
                + looserPrefix + "\n" + finalMessage
                + "\n" + Utils.repeat(Emoji.tilda.getEmojiCode(), count);

        if (user1Sum.equals(user2Sum)) {
            var mes = "Ничья: %s и %s оба проиграли";
            finalWinnerMessage = Utils.repeat(Emoji.tilda.getEmojiCode(), count)
                                + String.format(mes, user1.getUserName(), user2.getUserName())
                                + "\n" + Utils.repeat(Emoji.tilda.getEmojiCode(), count);
            finalLooserMessage = finalWinnerMessage;
        }
        else
            giveBenefit(isFirstWinner, user1, user2);
        // отправка сообщения первому юзеру
        Utils.sendEvent(user1.getUserId(), isFirstWinner ? finalWinnerMessage : finalLooserMessage);
        return isFirstWinner ? finalLooserMessage : finalWinnerMessage;

    }

    private ArrayList<Integer> generateValues() {
        var arrList = new ArrayList<Integer>();
        for (var i = 0; i < 3; i++)
            arrList.add(Dice.generateValue());
        return arrList;
    }

    private Integer solveSum(ArrayList<Integer> arrList) {
        var sum = 0;
        for(Integer d : arrList)
            sum += d;
        return sum;
    }

    private static String pluralize(int count)
    {
        if (count  == 1)
            return "очко";
        if (count > 1 & count < 5)
            return "очка";
        return "очков";
    }

    private void giveBenefit(boolean isFirstWinner, User user1, User user2) {
        if (isFirstWinner) {
            user1.AddMoney(user1.getBet() * 2);
        } else {
            user2.AddMoney(user2.getBet() * 2);
        }
        SQLHandler.update(user1);
    }
}