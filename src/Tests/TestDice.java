package Tests;

import bot.User;

import games.DiceController;
import games.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDice {

    @Test
    public void TestDiceController(){
        User user1 = new User("777", "Стивоша", "Гоша", "Садовских", 100, 25, "3x3");
        User user2 = new User("888", "Стивоша", "Гоша", "Садовских", 100, 25, "3x3");
        assertEquals(0, DiceController.userQueue.size());
        DiceController.addUser(user1);
        assertTrue(DiceController.canJoin());
        assertTrue(DiceController.isSameUser(user1));
        DiceController.addUser(user2);
        assertFalse(DiceController.canJoin());
    }

    @Test
    public void TestUtils(){
        assertTrue(Utils.isNumber("12"));
        assertFalse(Utils.isNumber("1asd2"));
    }
}
