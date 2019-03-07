package cn.neusoft.xuxiao.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil2 {
    private static Workbook readExcel(String fileName, InputStream inputStream) {
        Workbook wb = null;
        String extString = fileName.substring(fileName.lastIndexOf("."));
        try {
            if (".xls".equals(extString))
                return wb = new HSSFWorkbook(inputStream);
            if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(inputStream);
            }
            return wb = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    private static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case 0:
                    cellValue = String.valueOf((int) cell.getNumericCellValue());
                    break;
                case 2:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue();
                        break;
                    }

                    cellValue = String.valueOf((int) cell.getNumericCellValue());

                    break;
                case 1:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellValue = "";
                    break;
            }
        } else cellValue = "";

        return cellValue;
    }

    public static List<Map<String, String>>[] parseExcel(String fileName, InputStream inputStream, String[] columns) {

        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        String cellData = null;
        wb = readExcel(fileName, inputStream);
        int sheets = wb.getNumberOfSheets();
        List<Map<String, String>>[] list = new ArrayList[sheets];
        if (wb != null) {
            int end = 0;
            for (int t = 0; t < sheets; t++) {
                list[t] = new ArrayList();
                sheet = wb.getSheetAt(t);
                int rownum = sheet.getPhysicalNumberOfRows();

                row = sheet.getRow(0);
                int colnum = row.getPhysicalNumberOfCells();
                for (int i = 1; i < rownum; i++) {
                    Map<String, String> map = new LinkedHashMap<String, String>();
                    row = sheet.getRow(i);
                    if (row == null) break;
                    for (int j = 0; j < colnum; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        map.put(t==0?columns[j]:columns[end+j], cellData);
                    }
                    list[t].add(map);
                }
                end = colnum;
            }
        }
        return list;
    }
}