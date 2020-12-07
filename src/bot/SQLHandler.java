package bot;

import java.sql.*;

public class SQLHandler {

    private static final String USERNAME = "root1";
    private static final String PASSWORD = "root1";
    private static final String URL = "jdbc:mysql://localhost:3306/playersdb";
    private static final String SELECT = "select * from slots where userId = ?";
    private static final String INSERT = "insert into slots values (?, ?, ?, ?)";
    private static final String UPDATE = "update slots " + "set balance = ?, " + "bet = ?, " +  "mode = ? " + "where userId = ?";

    public static User getUser(String userId,  String userName, String userFirstName, String userLastName) throws SQLException {
        Connection connection = null;
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e){
            e.printStackTrace();
        }

        if (existUser(userId, connection)) {
            assert connection != null;
            return getDBUser(userId, userName, userFirstName, userLastName);

        } else {
            User user = new User(userId, userFirstName, userLastName, userName, 100, 25, "3x3");
            create(user);
            return user;
        }
    }

    private static boolean existUser(String userId, Connection connection) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT)) {
            preparedStatement.setString(1, userId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static User getDBUser(String userId, String userName, String userFirstName, String userLastName) {
        PreparedStatement preparedStatement;
        Connection connection;
        User user = null;
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user =  new User(userId, userFirstName, userLastName, userName,
                        Long.parseLong(resultSet.getString("balance")),
                        Integer.parseInt(resultSet.getString("bet")),
                        resultSet.getString("mode"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

        public static void update(User user) {
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, Long.toString(user.getBalance()));
            preparedStatement.setString(2, Long.toString(user.getBet()));
            preparedStatement.setString(3, user.getMode());
            preparedStatement.setString(4, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void create(User user){
        Connection connection;
        PreparedStatement preparedStatement;
        try {
            Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, Long.toString(user.getBalance()));
            preparedStatement.setString(3, Long.toString(user.getBet()));
            preparedStatement.setString(4, user.getMode());
            preparedStatement.execute();
        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
