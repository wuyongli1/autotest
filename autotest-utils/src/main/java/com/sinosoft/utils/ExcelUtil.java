package com.sinosoft.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
* @author ����
* @version ����ʱ�䣺2018��3��8�� ����3:07:22
* @Description ����excel�Ĺ�����
*/
public class ExcelUtil {
	public static void initModel(Object o) throws FileNotFoundException, IOException {
		File file = new File(ExcelUtil.class.getClassLoader().getResource("excel/WeiBaoldXBTest.xls").getPath());
		//1.�õ�Excel���ö���    
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));   
		//2.�õ�Excel����������    
		HSSFWorkbook wb = new HSSFWorkbook(fs);    
        //3.�õ�Excel���������    
        HSSFSheet sheet = wb.getSheetAt(0);    
        //������    
        int trLength = sheet.getLastRowNum();  
        //4.�õ�Excel���������    
        HSSFRow row = sheet.getRow(0);   
        //������    
        int tdLength = row.getLastCellNum();   
        //5.�õ�Excel������ָ���еĵ�Ԫ��    
        HSSFCell cell = row.getCell((short)1);    
        //6.�õ���Ԫ����ʽ    
        CellStyle cellStyle = cell.getCellStyle();    
        for(int i=0;i<trLength;i++){    
            //�õ�Excel���������    
            HSSFRow row1 = sheet.getRow(i);    
            for(int j=0;j<tdLength;j++){    
                    
            //�õ�Excel������ָ���еĵ�Ԫ��    
            HSSFCell cell1 = row1.getCell(j);    
                
            /**  
             * Ϊ�˴���Excel�쳣Cannot get a text value from a numeric cell  
             * ���������е����ݶ����ó�String���͸�ʽ  
             */    
            if(cell1!=null){    
                  cell1.setCellType(Cell.CELL_TYPE_STRING);    
             }    
                
            //���ÿһ���е�ֵ    
            System.out.print(cell1.getStringCellValue()+"\t\t\t");    
            }    
            System.out.println();    
        }    
        wb.close();
        fs.close();
	}
}
