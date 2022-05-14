package ru.gb.gbchat2.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcApp {

    private Connection connection;

    public static void main(String[] args) {
        try(Connection  connection = DriverManager.getConnection("jdbc:sqlite:javadb.db")) {
            Statement statement = connection.createStatement();
            createTable(statement);
            insert(statement,"login1","pass1","nick1");
            insert(statement,"login2","pass2","nick2");
            insert(statement,"login3","pass3","nick3");
            insert(statement,"login4","pass4","nick4");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insert(Statement statement, String login,String password, String nick) throws SQLException {
        statement.executeUpdate("INSERT INTO clients (login,password,nick) VALUES('"+ login+"', '"+ password +"','"+nick+"')");
    }

    private static void createTable(Statement statement) throws SQLException {
        statement.executeUpdate("" +
                "CREATE TABLE IF NOT EXISTS clients (" +
                    "login TEXT ," +
                    "password TEXT," +
                    "nick TEXT" +
                ")");
    }

}
