package com.kb.selenium.clippercard.dataProvider.sql;

import com.kb.selenium.clippercard.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbozoglu on 10.07.2015.
 */
public class UserDao {
    private static Logger logger = Logger.getLogger(UserDao.class);

    private static List<User> allUsers = new ArrayList<User>();

    public static List<User> findAllUsers(){
        allUsers.clear();
        UserDao.selectAllUsers();
        return allUsers;
    }

    public static User findUserById(Long id){
        for(User user : UserDao.allUsers){
            if(user.getId().equals(id)){
                return user;
            }
        }

        return null;
    }

    private static void selectAllUsers(){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = MysqlUtils.getDbConnection();

            String sql = "SELECT * FROM USERS";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                long id  = rs.getInt("id");
                String email = rs.getString("email");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String phone = rs.getString("phone");
                String password = rs.getString("password");

                User user = new User();
                user.setId(id);
                user.setEmail(email);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhone(phone);
                user.setPassword(password);

                UserDao.allUsers.add(user);
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

}
