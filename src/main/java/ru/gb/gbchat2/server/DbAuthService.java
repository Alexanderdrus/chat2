package ru.gb.gbchat2.server;

import java.io.IOException;
import java.sql.*;

public class DbAuthService implements AuthService {

    private Connection connection;

    public DbAuthService() { run();}

    @Override
    public String getNickByLoginAndPassword(String login, String password)  {
        try (final PreparedStatement statement = connection.prepareStatement("select nick from clients where login = ? and password =?")) {
            statement.setString(1,login);
            statement.setString(2,password);
            final ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getString("nick");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public void run() {
        try {
                connection = DriverManager.getConnection("jdbc:sqlite:javadb.db");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных");
        }
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка закрытия соединения к базе данных");
        }

    }

}