package com.epicc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.epicc.annotation.ExcelColName;
import com.epicc.annotation.ExcelSheetName;

/**
 * @author 刘飞
 * @version 创建时间：2018年3月8日 下午3:07:22
 * @Description 操作excel的工具类
 * 参考网址:http://www.bubuko.com/infodetail-2132259.html
 */
public class ExcelUtil {

	public static String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将excel表格中的信息设置进bean中
	 */
	public static <T> T setExcelInfo2Bean(File file, T t) {
		// 获取工作簿类下的子类(表类)
		Field[] declaredFields = t.getClass().getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			Field sheetFiled = declaredFields[i];
			sheetFiled.setAccessible(true);
			// 将子表的内容赋值到对象中
			try {
				sheetFiled.set(t, setSheetValue2Bean(sheetFiled, file));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	/**
	 * 校验参数的类中是否包含指定注解
	 */
	public static <T extends Annotation> Field[] matchDeclaredFields(Field[] declaredFields, Class T) {
		List<Field> matchedDeclaredFieldsList = new ArrayList<Field>();

		for (int i = 0; i < declaredFields.length; i++) {
			Field sheetFiled = declaredFields[i];
			sheetFiled.setAccessible(true);
			if (sheetFiled.getAnnotation(T) != null) {
				matchedDeclaredFieldsList.add(sheetFiled);
			}
		}
		Field[] matchedDeclaredFieldsArray = null;

		if (matchedDeclaredFieldsList.size() > 0) {
			matchedDeclaredFieldsArray = new Field[matchedDeclaredFieldsList.size()];

			for (int i = 0; i < matchedDeclaredFieldsArray.length; i++) {
				matchedDeclaredFieldsArray[i] = matchedDeclaredFieldsList.get(i);
			}
		}
		return matchedDeclaredFieldsArray;
	}

	/**
	 * 将子表的内容赋值到对象中
	 */
	private static <T> Object setSheetValue2Bean(Field sheetFiled, File file) throws Exception {
//		// 薄类中所有参数均为list类型,不进行校验
//		Class sheetListClass = sheetFiled.getType();
		// 获取参数的类型的参数化的类型
		Type type = sheetFiled.getGenericType();
		// 将参数化的类型强转,获得类型中的参数(泛型中的类)
		ParameterizedType pType = (ParameterizedType) type;
		// 泛型中的参数,如果是map,数组长度就为2
		Type[] listType = pType.getActualTypeArguments();
		// 获取list泛型中的子表class
		Class sheetClass = (Class) listType[0];

		// 获取子类对应的sheet名
		ExcelSheetName sheetNameAnno = (ExcelSheetName) sheetClass.getAnnotation(ExcelSheetName.class);

		String sheetName = sheetNameAnno.value();

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
		// 获取行数

		return getExcelInfo2Bean(sheetClass, sheet);
	}

	/**
	 * 将返回与sheet内容对应的class的实例的List集合
	 */
	// private <T extends ExcelCheckPropertie> List<T> getExcelInfo2Bean(Class T,
	// Sheet sheet) throws Exception {
	private static <T> List<T> getExcelInfo2Bean(Class T, Sheet sheet) throws Exception {
		Map<String, Integer> cellNameMap = getCellNameMap(sheet);

		// 获取行数
		int rowNum = sheet.getLastRowNum();
		if (rowNum == 0) {
			return new ArrayList<T>();
		}

		List<T> tList = new ArrayList<T>(rowNum - 1);

		// 获取子表类的属性(对应表中的列)
		Field[] colFields = T.getDeclaredFields();
		Field[] excelCheckPropertiesDeclaredFields = T.getSuperclass().getDeclaredFields();
		// (获取只包含自定义注解的属性)
		Field[] matchedColFields = matchDeclaredFields(colFields, ExcelColName.class);
		// 如果包含自定义注解的参数

		// 从第二行开始读取,并设置进实例
		for (int j = 1; j <= rowNum; j++) {
			Row row = sheet.getRow(j);
			if (row == null) {
				continue;
			}
			// 创建当前sheet类的实例
			T sheetBean = (T) T.newInstance();

			// 遍历包含自定义注解的参数
			if (matchedColFields != null && matchedColFields.length > 0) {
				for (int i = 0; i < matchedColFields.length; i++) {
					matchedColFields[i].setAccessible(true);
					Field colField = matchedColFields[i];

					ExcelColName excelColNameAnno = colField.getAnnotation(ExcelColName.class);
					String excelColName = excelColNameAnno.value().trim();
					// 判断该参数是否需要校验
					boolean isRequired = excelColNameAnno.IsRequired();
					// 如果为必填字段
					if (isRequired) {
						// 遍历每行的每个参数,设置进bean
						for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {
							// 获取sheet类的属性对应的表中的列的cell对象
							Cell cell = row.getCell(cellNameMap.get(excelColName));
							String cellValue = "";
							if (cell != null) {
								cellValue = getCellValue(cell);
								// 判断属性类型
								if (matchedColFields[i].getType().isAssignableFrom(Integer.class)) {
									matchedColFields[i].set(sheetBean, Integer.parseInt(getCellValue(cell)));

								} else if (matchedColFields[i].getType().isAssignableFrom(Date.class)) {
									matchedColFields[i].set(sheetBean, getDateCellValue(cell));

								} else if (matchedColFields[i].getType().isAssignableFrom(Double.class)) {
									matchedColFields[i].set(sheetBean, Double.parseDouble(getCellValue(cell)));

								} else if (matchedColFields[i].getType().isAssignableFrom(Float.class)) {
									matchedColFields[i].set(sheetBean, Float.parseFloat(getCellValue(cell)));

								} else {
									matchedColFields[i].set(sheetBean, getCellValue(cell));
								}
							}

							// 设置父类属性
							for (int l = 0; l < excelCheckPropertiesDeclaredFields.length; l++) {
								Field superField = excelCheckPropertiesDeclaredFields[l];
								superField.setAccessible(true);
								// 当前单元格所在表名
								if (superField.getName().equals("sheetName")) {
									superField.set(sheetBean, sheet.getSheetName());
									// 当前单元格所在行数
								} else if (superField.getName().equals("rowNum")) {
									superField.set(sheetBean, j);
									// 当前单元格所在列名
								} else if (superField.getName().equals("colName")) {
									superField.set(sheetBean, excelColName);
									// 非空校验结果
								} else if (superField.getName().equals("isChecked")) {
									if (cellValue == null || "".equals(cellValue.trim())) {
										superField.set(sheetBean, false);
									}
								}
							}

						}
					} else {
						// 遍历每行的每个参数,设置进bean
						// for (int k = 0; k < row.getPhysicalNumberOfCells(); k++) {
						// 获取sheet类的属性对应的表中的列的cell对象
						Integer integer = cellNameMap.get(excelColName);
						Cell cell = row.getCell(integer);
						if (cell != null) {
							// 设置父类属性
							for (int l = 0; l < excelCheckPropertiesDeclaredFields.length; l++) {
								Field superField = excelCheckPropertiesDeclaredFields[l];
								superField.setAccessible(true);
								// 当前单元格所在表名
								if (superField.getName().equals("sheetName")) {
									superField.set(sheetBean, sheet.getSheetName());
									// 当前单元格所在行数
								} else if (superField.getName().equals("rowNum")) {
									superField.set(sheetBean, j);
									// 当前单元格所在列名
								} else if (superField.getName().equals("colName")) {
									superField.set(sheetBean, excelColName);
								}
							}
							// 判断属性类型
							if (matchedColFields[i].getType().isAssignableFrom(Integer.class)) {
								matchedColFields[i].set(sheetBean, Integer.parseInt(getCellValue(cell)));

							} else if (matchedColFields[i].getType().isAssignableFrom(Date.class)) {
								matchedColFields[i].set(sheetBean, getDateCellValue(cell));

							} else if (matchedColFields[i].getType().isAssignableFrom(Double.class)) {
								matchedColFields[i].set(sheetBean, Double.parseDouble(getCellValue(cell)));

							} else if (matchedColFields[i].getType().isAssignableFrom(Float.class)) {
								matchedColFields[i].set(sheetBean, Float.parseFloat(getCellValue(cell)));

							} else {
								matchedColFields[i].set(sheetBean, getCellValue(cell));
							}
						}
						// }
					}
				}
			}
			tList.add(sheetBean);
		}

		// 校验空值
		ListIterator<T> listIterator = tList.listIterator();
		while (listIterator.hasNext()) {
			T next = listIterator.next();
			int nullNum = 0;
			for (int i = 0; i < matchedColFields.length; i++) {
				if (matchedColFields[i].get(next) == null || matchedColFields[i].get(next).toString().equals("")) {
					++nullNum;
				}
			}
			if (nullNum == matchedColFields.length) {
				listIterator.remove();
			}
		}
		return tList;
	}

	/**
	 * 获取时间类型数值 cell.getCellStyle().getDataFormat() 日期时间(yyyy-MM-dd HH:mm:ss) - 22，
	 * 日期(yyyy-MM-dd) - 14， 时间(HH:mm:ss) - 21， 年月(yyyy-MM) - 17， 时分(HH:mm) - 20，
	 * 月日(MM-dd) - 58
	 */
	private static Date getDateCellValue(Cell cell) {
		return cell.getDateCellValue();
	}

	/**
	 * 获取第一行做标题存入列名与对应的列值
	 */
	public static Map<String, Integer> getCellNameMap(Sheet sheet) {
		// 获取第一行列的列名及列数存入map
		Map<String, Integer> colNameMap = new HashMap<String, Integer>();
		Row firstRow = sheet.getRow(0);
		// 列数
		int cellNum = firstRow.getLastCellNum();
		// map赋值
		for (int i = 0; i < cellNum; i++) {
			colNameMap.put(getCellValue(firstRow.getCell(i)), i);
		}
		return colNameMap;
	}

	/**
	 * 对Excel的各个单元格的格式进行判断并转换
	 */
	private static String getCellValue(Cell cell) {
		String cellValue = "";
		DecimalFormat df = new DecimalFormat("####################.##########");
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				cellValue = new SimpleDateFormat(defaultDateFormat).format(date);
			} else {
				double dc = cell.getNumericCellValue();
				// cellValue = String.valueOf(dc);
				cellValue = df.format(dc);
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;

		default:
			cellValue = "";
		}
		return cellValue;
	}
}