package com.kb.selenium.clippercard.dataProvider.sql;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Created by kbozoglu on 10.07.2015.
 */
public class MysqlUtils {
    private static Logger logger = Logger.getLogger(MysqlUtils.class);

    private static final String DB_URL = "jdbc:mysql://localhost/clippercard";
    private static final String USER = "root";
    private static final String PASS = "admin";

    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;
    }

    public static void closeResultset(ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
            }
            catch (SQLException e) {
                logger.error("Could not close resultset", e);
            }
        }
    }

    public static void closeStatement(Statement statement){
        if (statement != null) {
            try {
                statement.close();
            }
            catch (SQLException e) {
                logger.error("Could not close statement", e);
            }
        }
    }

    public static void closeConnection(Connection conn){
        if (conn != null) {
            try {
                conn.close();
            }
            catch (SQLException e) {
                logger.error("Could not close connection", e);
            }
        }
    }
}

