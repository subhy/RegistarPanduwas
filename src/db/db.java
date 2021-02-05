package db;

import java.sql.*;

public class db {

    private static db dbConnection;
    private Connection connection;

    private db() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBC", "root", "1234");
        }catch (SQLSyntaxErrorException e) {

        }
    }

    public static db getInstance() throws SQLException, ClassNotFoundException {
        return (dbConnection == null) ? (dbConnection = new db()) : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }

}