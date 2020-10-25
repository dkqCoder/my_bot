package com.tty.user.common.utils;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * @author ln
 * */
public class ExcelUtil {
    private static final Logger logger = Logger.getLogger(ExcelUtil.class);

    /**
     * 从excel获取用户账号
     * excel格式要求：用户账号放第一列；
     * getUserNams()主动去重
     * @param file
     * @return
     */
    public static List<String> getUserNams(MultipartFile file) {
        //userNameList
        List<String> ss = new ArrayList<>();
        try {
            String flieName = file.getOriginalFilename();
            String[] xx = flieName.split("\\.");
            InputStream in = file.getInputStream();
            Workbook wb = null;
            if (xx[(xx.length - 1)].equals("xls")) {
                wb = new HSSFWorkbook(in);
            } else if (xx[(xx.length - 1)].equals("xlsx")) {
                wb = new XSSFWorkbook(in);
            }
            Sheet sheet = wb.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();

            if(rows > 1000) {
                logger.warn("每次建议导入用户最大1000");
                return ss;
            }

            if (rows > 0) {
                sheet.getMargin((short) 2);
                int cells = sheet.getRow(0).getLastCellNum();
                for (int j = 0; j < rows; j++) {
                    Row row = sheet.getRow(j);
                    if (row != null) {
                        if (cells >= 0) {
                            for (short k = 0; k < cells; k = (short) (k + 1)) {
                                Cell cell = row.getCell(k);
                                if (cell != null) {
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    String userId = cell.getStringCellValue();
                                    if(ss.indexOf(userId) < 0) {
                                        ss.add(userId);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导入excel错误", e);
        }
        return ss;
    }

}
