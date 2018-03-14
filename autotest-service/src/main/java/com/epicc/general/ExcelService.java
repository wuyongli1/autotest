package com.epicc.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.epicc.annotation.ExcelColName;
import com.epicc.annotation.ExcelSheetName;
import com.epicc.utils.ExcelUtil;

/**
* @author 刘飞
* @version 创建时间：2018年3月14日 上午10:54:08
* @Description 类描述
*/
@Service
public class ExcelService {
	public void writeResult(List<AssertResult> assertResultList, Object object, File file) throws Exception {
		for (int i = 0;i<assertResultList.size();i++) {
			//得到sheetName
			String sheetName = object.getClass().getAnnotation(ExcelSheetName.class).value();
			// 获取文件后缀
			String fileExt = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			// 创建流
			InputStream input = new FileInputStream(file);
			// 创建Workbook
			Workbook wb = null;
			// 创建sheet
			Sheet sheet = null;
			// 根据后缀判断excel 2003 or 2007+
			if (fileExt.equals("xls")) {
				wb = (HSSFWorkbook) WorkbookFactory.create(input);
			} else {
				wb = new XSSFWorkbook(input);
			}
			// 获取表
			sheet = wb.getSheet(sheetName);
			Map<String, Integer> cellNameMap = ExcelUtil.getCellNameMap(sheet);
			// 获取行数
			int rowNum = sheet.getLastRowNum();
			String actual = assertResultList.get(i).getClass().getDeclaredField("actual").getAnnotation(ExcelColName.class).value();
			String result = assertResultList.get(i).getClass().getDeclaredField("result").getAnnotation(ExcelColName.class).value();
			// 从第二行开始读取,并设置进实例
			for (int j = 1; j <= rowNum; j++) {
				Row row = sheet.getRow(j);
				// 获取sheet类的属性对应的表中的列的cell对象
				try {
					Cell cell_actual = row.createCell(cellNameMap.get(actual));
					cell_actual.setCellValue(assertResultList.get(j-1).getActual());
					Cell cell_result = row.createCell(cellNameMap.get(result));
					cell_result.setCellValue(assertResultList.get(j-1).getResult());
				} catch (IndexOutOfBoundsException e) {
					System.err.println("下标越界，不影响结果");
					break;
				}
				FileOutputStream os =  new FileOutputStream(file);
				wb.write(os);
				os.close();
			}
			wb.close();
			input.close();
		}
	}
}
