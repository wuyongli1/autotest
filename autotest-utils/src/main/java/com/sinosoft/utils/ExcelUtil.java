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
* @author 刘飞
* @version 创建时间：2018年3月8日 下午3:07:22
* @Description 操作excel的工具类
*/
public class ExcelUtil {
	public static void initModel(Object o) throws FileNotFoundException, IOException {
		File file = new File(ExcelUtil.class.getClassLoader().getResource("excel/WeiBaoldXBTest.xls").getPath());
		//1.得到Excel常用对象    
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));   
		//2.得到Excel工作簿对象    
		HSSFWorkbook wb = new HSSFWorkbook(fs);    
        //3.得到Excel工作表对象    
        HSSFSheet sheet = wb.getSheetAt(0);    
        //总行数    
        int trLength = sheet.getLastRowNum();  
        //4.得到Excel工作表的行    
        HSSFRow row = sheet.getRow(0);   
        //总列数    
        int tdLength = row.getLastCellNum();   
        //5.得到Excel工作表指定行的单元格    
        HSSFCell cell = row.getCell((short)1);    
        //6.得到单元格样式    
        CellStyle cellStyle = cell.getCellStyle();    
        for(int i=0;i<trLength;i++){    
            //得到Excel工作表的行    
            HSSFRow row1 = sheet.getRow(i);    
            for(int j=0;j<tdLength;j++){    
                    
            //得到Excel工作表指定行的单元格    
            HSSFCell cell1 = row1.getCell(j);    
                
            /**  
             * 为了处理：Excel异常Cannot get a text value from a numeric cell  
             * 将所有列中的内容都设置成String类型格式  
             */    
            if(cell1!=null){    
                  cell1.setCellType(Cell.CELL_TYPE_STRING);    
             }    
                
            //获得每一列中的值    
            System.out.print(cell1.getStringCellValue()+"\t\t\t");    
            }    
            System.out.println();    
        }    
        wb.close();
        fs.close();
	}
}
