package com.gkzxhn.autoespresso.build;

import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

/**
 * Created by Raleigh.Luo on 18/3/7.
 */

public interface IBuildTestProcedure {
    public void init(Sheet sheet, Map<String, Integer> mColNameMaps,
                     int firstRow, int lastRow) ;
    public String build();
}
