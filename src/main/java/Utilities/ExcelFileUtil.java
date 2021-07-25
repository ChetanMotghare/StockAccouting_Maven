package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
Workbook wb;
//constructor for reading excel path
public ExcelFileUtil(String excelpath)throws Throwable
{
	FileInputStream fi = new FileInputStream(excelpath);
	wb= WorkbookFactory.create(fi);
}
//count no of rows in a sheet
public int rowCount(String sheetName)
{
	return wb.getSheet(sheetName).getLastRowNum();
}
//count no of cells in a row
public int cellCount(String sheetName)
{
	return wb.getSheet(sheetName).getRow(0).getLastCellNum();
}
//read data from cell
public String getCellData(String sheetname,int row,int column)
{
	String data="";
	if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
	{
		//get numeric type cell
	int celldata =(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
	//convert interger type into string type
	data= String.valueOf(celldata);
	}
	else
	{
	data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();	
	}
	return data;
}
//set status into cell
public void setCellData(String sheetName,int row,int column,String status,String writeexcel)
throws Throwable{
	//get sheet from wb
	Sheet ws = wb.getSheet(sheetName);
	//get row from sheet
	Row rowNum=ws.getRow(row);
	//create cell 
	Cell cell=rowNum.createCell(column);
	//write status into cell
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("Pass"))
	{
		CellStyle style =wb.createCellStyle();
		Font font= wb.createFont();
		//set colour for text
		font.setColor(IndexedColors.GREEN.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("Fail"))
	{
		CellStyle style =wb.createCellStyle();
		Font font= wb.createFont();
		//set colour for text
		font.setColor(IndexedColors.RED.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
	}
	else if(status.equalsIgnoreCase("Blocked"))
	{
		CellStyle style =wb.createCellStyle();
		Font font= wb.createFont();
		//set colour for text
		font.setColor(IndexedColors.BLUE.getIndex());
		font.setBold(true);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		rowNum.getCell(column).setCellStyle(style);
	}
	FileOutputStream fo = new FileOutputStream(writeexcel);
	wb.write(fo);
}
}








