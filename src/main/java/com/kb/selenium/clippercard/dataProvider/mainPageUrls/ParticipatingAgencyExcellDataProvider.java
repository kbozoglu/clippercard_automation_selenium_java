package com.kb.selenium.clippercard.dataProvider.mainPageUrls;

import com.kb.selenium.clippercard.model.NavigationMenuItem;
import com.kb.selenium.clippercard.model.ParticipatingAgency;
import org.apache.commons.io.IOUtils;
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
public class ParticipatingAgencyExcellDataProvider {
    public static List<ParticipatingAgency> getFromExcel(){
        List<ParticipatingAgency> participatingAgencyList = new ArrayList<ParticipatingAgency>();
        InputStream in = null;

        try {
            in = NavigationMenuItem.class.getResourceAsStream("/TestData.xls");

            HSSFWorkbook myWorkBook = new HSSFWorkbook(in);
            HSSFSheet sheet = myWorkBook.getSheet("PARTICIPATING_AGENCIES");

            Iterator<Row> rowIterator = sheet.iterator();

            //Skipping header
            rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Iterator<Cell> cellIterator = row.cellIterator();

                ParticipatingAgency participatingAgency = new ParticipatingAgency();
                participatingAgency.setIconAltText(cellIterator.next().getStringCellValue());
                participatingAgency.setTargetPageTitle(cellIterator.next().getStringCellValue());

                participatingAgencyList.add(participatingAgency);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            IOUtils.closeQuietly(in);
        }

        return participatingAgencyList;
    }
}
