package com.kb.selenium.clippercard.dataProvider.sql;

import com.kb.selenium.clippercard.model.testcase.LoginTestCase;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by kbozoglu on 10.07.2015.
 */
public class LoginTestDao {
    private static Logger logger = Logger.getLogger(LoginTestDao.class);

    private static List<LoginTestCase> allLoginTestCases = new ArrayList<LoginTestCase>();

    public static List<LoginTestCase> getAllTestCases(){
        LoginTestDao.allLoginTestCases.clear();
        selectAllLoginTestCases();
        return LoginTestDao.allLoginTestCases;
    }

    public static long insertInitialTestResult(LoginTestCase loginTestCase){
        Long testResultDbId = null;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = MysqlUtils.getDbConnection();

            String sql = "INSERT INTO TEST_RESULT_LOGIN (CASE_ID, START_TIME) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setLong(1, loginTestCase.getId());

            Date now = new Date();
            pstmt.setTimestamp(2, new Timestamp(now.getTime()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("inserting test case result failed, no rows affected.");
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();

            if (generatedKeys.next()) {
                testResultDbId = generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
        catch(SQLException se){
            logger.error("Sql Exception", se);
        }
        catch (ClassNotFoundException e) {
            logger.error("Mysql driver not found", e);
        }
        finally{
            MysqlUtils.closeStatement(pstmt);
            MysqlUtils.closeConnection(conn);
        }

        return testResultDbId;
    }

    public static void updateTestResult(Long testResultId, boolean status){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = MysqlUtils.getDbConnection();

            String sql = "UPDATE test_result_login SET END_TIME = ?, SUCCESS = ? WHERE ID = ?";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            Date now = new Date();

            pstmt.setTimestamp(1, new Timestamp(now.getTime()));
            pstmt.setBoolean(2, status);
            pstmt.setLong(3, testResultId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("updating test case result failed, no rows affected.");
            }
        }
        catch(SQLException se){
            logger.error("Sql Exception", se);
        }
        catch (ClassNotFoundException e) {
            logger.error("Mysql driver not found", e);
        }
        finally{
            MysqlUtils.closeStatement(pstmt);
            MysqlUtils.closeConnection(conn);
        }
    }

    private static void selectAllLoginTestCases(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = MysqlUtils.getDbConnection();

            String sql = "SELECT * FROM TEST_CASE_LOGIN";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                long id  = rs.getInt("id");
                long userId = rs.getInt("user_id");
                boolean success = rs.getBoolean("success");

                LoginTestCase loginTestCase = new LoginTestCase();
                loginTestCase.setId(id);
                loginTestCase.setUser(UserDao.findUserById(userId));
                loginTestCase.setSuccess(success);

                allLoginTestCases.add(loginTestCase);
            }
        }
        catch(SQLException se){
            logger.error("Sql Exception", se);
        }
        catch (ClassNotFoundException e) {
            logger.error("Mysql driver not found", e);
        }
        finally{
            MysqlUtils.closeResultset(rs);
            MysqlUtils.closeStatement(stmt);
            MysqlUtils.closeConnection(conn);
        }
    }

    public static void main(String args[]){
        selectAllLoginTestCases();
    }

}
