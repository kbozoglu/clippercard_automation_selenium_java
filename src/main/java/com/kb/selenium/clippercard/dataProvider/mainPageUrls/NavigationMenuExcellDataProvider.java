package com.kb.selenium.clippercard.dataProvider.mainPageUrls;

import com.kb.selenium.clippercard.model.NavigationMenuItem;
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
 * Created by kbozoglu on 08.07.2015.
 */
public class NavigationMenuExcellDataProvider {

    private static Logger logger = Logger.getLogger(NavigationMenuExcellDataProvider.class);

    public static List<NavigationMenuItem> getTopNavigationMenu() {
        List<NavigationMenuItem> navigationMenuItemList = new ArrayList<NavigationMenuItem>();
        InputStream in = null;

        try {
            in = NavigationMenuItem.class.getResourceAsStream("/TestData.xls");

            HSSFWorkbook myWorkBook = new HSSFWorkbook(in);
            HSSFSheet sheet = myWorkBook.getSheet("TOP_NAVIGATION_MENU");

            Iterator<Row> rowIterator = sheet.iterator();

            //Skipping header
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                NavigationMenuItem navigationMenuItem = new NavigationMenuItem();
                navigationMenuItem.setId(cellIterator.next().getStringCellValue());
                navigationMenuItem.setTargetPageTitle(cellIterator.next().getStringCellValue());

                navigationMenuItemList.add(navigationMenuItem);
            }
        }
        catch (FileNotFoundException e) {
            logger.error("Error while reading test data for navigation menu item", e);
        } catch (IOException e) {
            logger.error("Error while reading test data for navigation menu item", e);
        }
        finally {
            IOUtils.closeQuietly(in);
        }

        return navigationMenuItemList;
    }
}
