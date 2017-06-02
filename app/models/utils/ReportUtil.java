package models.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import framework.utils.StringUtil;

public class ReportUtil {
	public static String formatPercent(Object obj){
		String value = StringUtil.trim(String.valueOf(obj),"0");
		double val = (new BigDecimal(value)).doubleValue();
		DecimalFormat ft = new DecimalFormat("0.00");
		return ft.format(val)+"%";
	}
	
	public static Workbook buildSummary(Workbook book,Map<String,String> dataMap,int rowIndex,int colspan){
		Sheet sheet = book.getSheetAt(0);
		sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(rowIndex, rowIndex, 0, colspan));
		Row row = sheet.createRow(rowIndex);
		row.createCell(0).setCellValue("合计");
		Set<String> keys = dataMap.keySet();
		String[] arr = keys.toArray(new String[0]);
		Cell cell =null;
		for(int i=colspan;i<arr.length+colspan;i++){
			cell = row.createCell(i+1);
			cell.setCellValue(dataMap.get(arr[i-colspan]));
		}
		return book;
	}
	
	/**
	 * 追加xlsx数据，并支持分页
	 * @param wb
	 * @param dataList
	 * @param flag
	 * @return
	 */
	public static Workbook appendXLSX(Workbook wb, List<List<String>> dataList, String[] title, boolean flag){
		int sheetNum = wb.getNumberOfSheets();
		Sheet sheet = wb.getSheetAt(sheetNum - 1);
		if (flag) {
			sheet = wb.createSheet();
			Row titleRow = sheet.createRow(0);
			for (int i = 0; i < title.length; i++) {
				titleRow.createCell(i).setCellValue(title[i]);
			}
		}
		int rowsLen = sheet.getLastRowNum()+1;
		if (rowsLen > 1048576) {
			sheet = wb.createSheet();
			Row titleRow = sheet.createRow(0);
			for (int i = 0; i < title.length; i++) {
				titleRow.createCell(i).setCellValue(title[i]);
			}
		}
		Row row = null;
		for(List<String> line : dataList){
			row = sheet.createRow(rowsLen);
			for(int j=0;j<line.size();j++){
				row.createCell(j).setCellValue(line.get(j));
			}
			rowsLen = rowsLen+1;
		}
		return wb;
	}
}
