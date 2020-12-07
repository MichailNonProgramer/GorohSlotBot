package Tests;

import bot.SQLHandler;
import bot.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

public class DBTests {
    private String testUserId = "56465";

    @Test
    public void DBUpdate() throws SQLException {
        User userBegin = SQLHandler.getUser(testUserId, "Стивоша", "Гоша", "Садовских");
        var balance = userBegin.getBalance();
        var bet = userBegin.getBet();
        userBegin.setBet(500);
        userBegin.AddMoney(200);
        SQLHandler.update(userBegin);
        User userEnd = SQLHandler.getUser(testUserId, "Стивоша", "Гоша", "Садовских");
        assertEquals(balance + 200, userEnd.getBalance());
        assertEquals(bet, userEnd.getBet());
    }

    @Test
    public void DBGetUser() throws SQLException {
        User userBegin = SQLHandler.getUser(testUserId, "Стивоша", "Гоша", "Садовских");
        assertEquals(testUserId, userBegin.getUserId());
    }

}
