package games;

import bot.User;

import java.util.ArrayList;

public class DiceMachine {
    private boolean isAllUsersReady = false;
    private boolean isFirstWinner = false;
    private User firstUser;
    private final ArrayList<Integer> firstUserData = new ArrayList<>();
    private User secondUser;
    private final ArrayList<Integer> secondUserData = new ArrayList<>();

    public String makeThrow(User user) {
        if (user.getBalance() < user.getBet()) {
            return String.format("Ваш баланс ниже суммы ставки, пожалуйста, пополните баланс.\nБаланс: %s%s",
                    user.getBalance(),
                    Emoji.dollar.getEmojiCode());
        }

        var number1 = Dice.generateValue();
        var number2 = Dice.generateValue();
        var number3 = Dice.generateValue();
        var sum = number1 + number2 + number3;
        // for first user
        if (!isAllUsersReady) {
            isAllUsersReady = true;
            firstUser = user;
            firstUserData.add(number1);
            firstUserData.add(number2);
            firstUserData.add(number3);
            firstUserData.add(sum);
            user.TakeOffMoney(user.getBet());
            return "Ожидание соперника";
        }
        else {
            if (firstUser.getUserId().equals(user.getUserId()))
                return "Ожидайте соперника";
            user.TakeOffMoney(user.getBet());
            secondUser = user;
            secondUserData.add(number1);
            secondUserData.add(number2);
            secondUserData.add(number3);
            secondUserData.add(sum);

            isFirstWinner = sum < firstUserData.get(3);

            var winnerPrefix = String.format("%sТы выиграл %s%s!%s\n",
                    Emoji.sign.getEmojiCode(),
                    isFirstWinner ? firstUser.getBet() * 2 : secondUser.getBet() * 2,
                    Emoji.dollar.getEmojiCode(),
                    Emoji.sign.getEmojiCode());
            var looserPrefix = String.format("%sТы проиграл %s%s!%s\n",
                    Emoji.cross.getEmojiCode(),
                    isFirstWinner ? secondUser.getBet() : firstUser.getBet(),
                    Emoji.dollar.getEmojiCode(),
                    Emoji.cross.getEmojiCode());

            var finalMessage = String.format("%s: %s %s (%s, %s, %s)",
                    firstUser.getUserName(),  firstUserData.get(3), pluralize(firstUserData.get(3)),
                    firstUserData.get(0), firstUserData.get(1), firstUserData.get(2))
                    + "\n\n" + String.format("%s: %s %s (%s, %s, %s)",
                    secondUser.getUserName(), secondUserData.get(3), pluralize(secondUserData.get(3)),
                    secondUserData.get(0), secondUserData.get(1), secondUserData.get(2));

            var count = 12;
            var finalWinnerMessage = Utils.repeat(Emoji.tilda.getEmojiCode(), count)
                    + winnerPrefix + "\n" + finalMessage
                    + "\n" + Utils.repeat(Emoji.tilda.getEmojiCode(), count);
            var finalLooserMessage = Utils.repeat(Emoji.tilda.getEmojiCode(), count)
                    + looserPrefix + "\n" + finalMessage
                    + "\n" + Utils.repeat(Emoji.tilda.getEmojiCode(), count);

            if (sum == firstUserData.get(3)) {
                var mes = "Ничья: %s и %s оба проиграли";
                finalWinnerMessage = String.format(mes,
                        firstUser.getUserName(),
                        secondUser.getUserName());
                finalLooserMessage = String.format(mes,
                        firstUser.getUserName(),
                        secondUser.getUserName());
            }
            giveBenefit();
            // отправка сообщения первому юзеру
            Utils.sendEvent(firstUser.getUserId(), isFirstWinner ? finalWinnerMessage : finalLooserMessage);
            clearFields();
            return isFirstWinner ? finalLooserMessage : finalWinnerMessage;
        }
    }

    private static String pluralize(int count)
    {
        if (count  == 1)
            return "очко";
        if (count > 1 & count < 5)
            return "очка";
        return "очков";
    }

    private void giveBenefit() {
        if (this.isFirstWinner) {
            this.firstUser.AddMoney(firstUser.getBet() * 2);
        } else {
            this.secondUser.AddMoney(secondUser.getBet() * 2);
        }
    }

    private void clearFields() {
        this.isAllUsersReady = false;
        this.firstUser = null;
        this.secondUser = null;
        this.firstUserData.clear();
        this.secondUserData.clear();
    }
}