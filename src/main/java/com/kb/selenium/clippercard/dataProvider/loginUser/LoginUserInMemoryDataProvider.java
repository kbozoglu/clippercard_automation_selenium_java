package com.kb.selenium.clippercard.dataProvider.loginUser;

import com.kb.selenium.clippercard.model.LoginUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbozoglu on 07.07.2015.
 */

public class LoginUserInMemoryDataProvider implements LoginUserDataProvider{

    @Override
    public List<LoginUser> getTestLoginUsers() {
        LoginUser user1 = new LoginUser("kevser@gmail.com", "1234", false);
        LoginUser user2 = new LoginUser("kevser.testk@gmail.com", "TestClipper123", true);

        List<LoginUser> userList = new ArrayList<LoginUser>();
        userList.add(user1);
        userList.add(user2);

        return userList;
    }

}
