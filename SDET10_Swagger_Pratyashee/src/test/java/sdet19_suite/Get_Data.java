package sdet19_suite;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

public class Get_Data {
	
	public static String[][] getDataFromExcel(String sheetName) {
		String[][] resultArray = null;
		try {
		FileInputStream ExcelFile = new FileInputStream("C:\\Users\\PRATYASHEECHANGMAI\\Desktop\\Rest API Project\\Datasheet.xls");
		HSSFWorkbook WBook = new HSSFWorkbook(ExcelFile);
		HSSFSheet WSheet = WBook.getSheet(sheetName);
		HSSFRow row;
		HSSFCell cell;
		
		int totalRows = WSheet.getLastRowNum();
		int totalColumns =0;
		int varCol = 0;
		//Counting the total number of columns
        for(int i = 0; i <= totalRows; i++) {
        	row = WSheet.getRow(i);
            if(row != null) {
            	varCol = WSheet.getRow(i).getPhysicalNumberOfCells();
            	if(varCol > totalColumns) {
            		totalColumns = varCol;
            	}
            }
        }
        
        resultArray = new String[totalRows+1][totalColumns+1];
        for(int i = 1; i <= totalRows; i++) {
            row = WSheet.getRow(i);
                for(int j = 0; j < totalColumns; j++) {
                	
                    cell = row.getCell(j);
                    cell.setCellType(CellType.STRING);
                    resultArray[i][j] = cell.getStringCellValue();
                }
        }
		} catch(IOException e) {
			System.out.println("Unable to read the Excel Sheet");
			e.printStackTrace();
		}
        return resultArray;
	}

}
