package com.gkzxhn.autoespresso.util;

import android.util.Log;

import com.gkzxhn.autoespresso.entity.MergedRegionEntity;

import java.io.File;
import java.io.FileInputStream;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelUtil {
    public static String xls2String(String filePath){
        String result = "";
        try{
            File file=new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            StringBuilder sb = new StringBuilder();
            jxl.Workbook rwb = Workbook.getWorkbook(fis);
            Sheet[] sheet = rwb.getSheets();
            for (int i = 0; i < sheet.length; i++) {
                Sheet rs = rwb.getSheet(i);
                Range[] rangeCells = rs.getMergedCells();// 返回sheet中合并的单元格数组
                for (int j = 0; j < rs.getRows(); j++) {
                    Cell[] cells = rs.getRow(j);
                    for(int k=0;k<cells.length;k++){

                        sb.append(cells[k].getContents());
                        System.out.print(j+"行"+k+"列＝"+cells[k].getContents());
                    }

                    System.out.print("\n");
                }
            }
            fis.close();
            result += sb.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    public static MergedRegionEntity isMergedRegion(Sheet sheet, int row , int column) {
        MergedRegionEntity mergedRegion=null;
        Range[] mergedCells=sheet.getMergedCells();
        Cell[] cells = sheet.getRow(row);
        for (Range r : mergedCells) {
            int firstColumn =r.getTopLeft().getColumn();
            int lastColumn = r.getBottomRight().getColumn();
            int firstRow = r.getTopLeft().getRow();
            int lastRow = r.getBottomRight().getRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    mergedRegion=new MergedRegionEntity();
                    mergedRegion.setMergedRegion(true);
                    mergedRegion.setFirstColumn(firstColumn);
                    mergedRegion.setFirstRow(firstRow);
                    mergedRegion.setLastColumn(lastColumn);
                    mergedRegion.setLastRow(lastRow);
                    mergedRegion.setValue(sheet.getCell(r.getTopLeft().getColumn(),
                            r.getTopLeft().getRow()).getContents());
                    break;
                }
            }
        }
        return mergedRegion;
    }
}
