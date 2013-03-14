package reportertest;
import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileOutputStream;
import java.util.Iterator;

public class javeread{
public static void main(String[] args) throws Exception
{
    String path="d:\\LabledResult_1.xls";
    int i = 1,b = 0;
    FileWriter fw = new FileWriter("f:/report.txt");
    BufferedWriter bw = new BufferedWriter(fw); 
    String myreadline;    
    long start = 0, end = 0,sum = 0;
    try{
        POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream(path));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow rowTitle = sheet.getRow(1);
        while( sheet.getRow(i+1)!= null)
        {           
            b = (int) rowTitle.getCell(14).getNumericCellValue();
            if(b==0 && (int)sheet.getRow(i+1).getCell(14).getNumericCellValue()==1)
            {
                 start = (long) rowTitle.getCell(0).getNumericCellValue();
            }
            else if(b == 1 && 0==(int)sheet.getRow(i+1).getCell(14).getNumericCellValue())
            {
                 end = (long) rowTitle.getCell(0).getNumericCellValue();  
                 myreadline = "From time"+start+" to time "+ end + " student have confusion";
                 bw.write(myreadline); 
                 bw.newLine();
                 bw.newLine();
                 System.out.println(myreadline);
                 sum += (end-start);
                 start = 0;                 
            }
            rowTitle = sheet.getRow(++i);
        }
        myreadline = "-------------------------------------------------------------------";
        bw.write(myreadline);
        bw.newLine();
        bw.newLine();
        myreadline = "The percentage of confusion part is " + (double)(sum)/(int)sheet.getRow(i).getCell(0).getNumericCellValue();
        bw.write(myreadline); 
        bw.flush();   
        bw.close();
        fw.close();
   }catch(Exception e){
      e.printStackTrace();
   }

}


public static String getCell(Cell cell) {  
  if (cell == null)  
    return "";  
  switch (cell.getCellType()) {  
    case Cell.CELL_TYPE_NUMERIC:  
      return cell.getNumericCellValue() + "";  
    case Cell.CELL_TYPE_STRING:  
      return cell.getStringCellValue();  
    case Cell.CELL_TYPE_FORMULA:  
      return cell.getCellFormula();  
    case Cell.CELL_TYPE_BLANK:  
      return "";  
    case Cell.CELL_TYPE_BOOLEAN:  
      return cell.getBooleanCellValue() + "";  
    case Cell.CELL_TYPE_ERROR:  
      return cell.getErrorCellValue() + "";  
  }  
  return "";  
}  
}
