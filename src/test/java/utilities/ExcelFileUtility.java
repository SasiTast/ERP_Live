package utilities;

import java.awt.image.IndexColorModel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.Main;

public class ExcelFileUtility 
{
	Workbook wb;
	
	//constructor for reading excel path
	public ExcelFileUtility(String ExcelPath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(ExcelPath);
		wb = WorkbookFactory.create(fi);
	}
	
	
	//counting no of rows in sheet

	public int rowCount(String SheetName)
	{
		Sheet ws = wb.getSheet(SheetName);
		int count = ws.getLastRowNum();
		return count;
	//return wb.getSheet(SheetName).getLastRowNum();
	}
	
	
	//read cell data
	public String getCellData(String SheetName,int row,int column)
	{
    String data="";
	if(wb.getSheet(SheetName).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
	{
	//read integer type cell data
	int celldata =(int)wb.getSheet(SheetName).getRow(row).getCell(column).getNumericCellValue();
	data =String.valueOf(celldata);
	}
	else
	{
	data =wb.getSheet(SheetName).getRow(row).getCell(column).getStringCellValue();
	}
	return data;
	}
	
	
	// method for writing data into new output sheet
	public void setcelldata(String sheetname,int row,int column,String status,String outfile) throws Throwable
	{
		Sheet ws = wb.getSheet(sheetname);
		Row rownum = ws.getRow(row);
		Cell cell = rownum.createCell(column);
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("pass"))
				{
			CellStyle style = wb.createCellStyle();
			Font cfont = wb.createFont();
			cfont.setColor(IndexedColors.GREEN.getIndex());
			cfont.setBold(true);
			cfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(cfont);
			rownum.getCell(column).setCellStyle(style);
				}
		else if (status.equalsIgnoreCase("fail"))
				{
			CellStyle style = wb.createCellStyle();
			Font cfont = wb.createFont();
			cfont.setColor(IndexedColors.RED.getIndex());
			cfont.setBold(true);
			cfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(cfont);
			rownum.getCell(column).setCellStyle(style);
				}
		else if(status.equalsIgnoreCase("Blocked"))
		{
			CellStyle style = wb.createCellStyle();
			Font cfont = wb.createFont();
			cfont.setColor(IndexedColors.BLUE.getIndex());
			cfont.setBold(true);
			cfont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(cfont);
			rownum.getCell(column).setCellStyle(style);
		}
		FileOutputStream fo = new FileOutputStream(outfile);
		wb.write(fo);
		
	
	}
	public static void main(String[] args) throws Throwable 
	{
	   ExcelFileUtility xl = new ExcelFileUtility("C:\\selenium\\orangehrm_DDT\\testdatafiles\\Testdata.xlsx");
	  int rnum= xl.rowCount("adminloginvaliddata");
	  System.out.println(rnum);
	  
	  for(int i=1;i<=rnum;i++)
	  {
		 String Username = xl.getCellData("adminloginvaliddata",i, 0);
		 String Password = xl.getCellData("adminloginvaliddata",i, 1);
		 //String Result = xl.getCellData("adminloginvaliddata",i, 2);
		 System.out.println(Username+"      "+Password);
		 xl.setcelldata("adminloginvaliddata",i,2,"pass","C:\\temp\\result.xlsx");
		 //xl.setcelldata("adminloginvaliddata",i,2,"fail","C:\\temp\\result.xlsx");
		 //xl.setcelldata("adminloginvaliddata",i,2,"blocked","C:\\temp\\result.xlsx");
	  }
	  
	}
	
}
	