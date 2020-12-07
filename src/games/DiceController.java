package games;

import bot.User;

import java.util.ArrayDeque;

public class DiceController {
    public static ArrayDeque<User> userQueue = new ArrayDeque<>();

    public static String tryPlay(User user) {
        if (canJoin() & !isSameUser(user)) {
            addUser(user);

            if (userQueue.size() == 2){
                var diceMachine = new DiceMachine();
                var user1 = userQueue.peekFirst();
                var user2 = userQueue.peekLast();
                userQueue.clear();
                return diceMachine.makeThrow(user1, user2);
            }
            return "Ожидайте соперника";
        }
        else
            return "Соперник ещё не найден";
    }

    public static boolean canJoin() {
        return userQueue.size() < 2;
    }

    public static boolean isSameUser(User user) {
        if (userQueue.size() > 0) {
            assert userQueue.peekFirst() != null;
            return user.getUserId().equals(userQueue.peekFirst().getUserId());
        }
        return false;
    }

    public static void addUser(User user) {
        userQueue.add(user);
    }
}