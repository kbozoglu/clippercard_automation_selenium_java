package com.kb.selenium.clippercard.dataProvider.loginUser;

import com.kb.selenium.clippercard.model.LoginUser;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kbozoglu on 07.07.2015.
 */
public class LoginUserExcellDataProvider implements LoginUserDataProvider{

    private static Logger logger = Logger.getLogger(LoginUserExcellDataProvider.class);

    @Override
    public List<LoginUser> getTestLoginUsers() {
        List<LoginUser> loginUserList = new ArrayList<LoginUser>();
        InputStream in = null;

        try {
            in = LoginUser.class.getResourceAsStream("/TestData.xls");

            HSSFWorkbook myWorkBook = new HSSFWorkbook(in);
            HSSFSheet sheet = myWorkBook.getSheet("LOGIN");

            Iterator<Row> rowIterator = sheet.iterator();

            //Skipping header
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                String username = cellIterator.next().getStringCellValue();
                String password = cellIterator.next().getStringCellValue();
                boolean success = cellIterator.next().getBooleanCellValue();

                LoginUser loginUser = new LoginUser(username, password, success);

                loginUserList.add(loginUser);
            }
        }
        catch (FileNotFoundException e) {
            logger.error("Error while reading test data for login user", e);
        } catch (IOException e) {
            logger.error("Error while reading test data for login user", e);
        }
        finally {
            IOUtils.closeQuietly(in);
        }

        return loginUserList;
    }

}
