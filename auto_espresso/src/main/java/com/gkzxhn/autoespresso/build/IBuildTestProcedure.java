package com.gkzxhn.autoespresso.build;


import java.util.Map;

import jxl.Sheet;

/**
 * Created by Raleigh.Luo on 18/3/7.
 */

public interface IBuildTestProcedure {
    public void init(Sheet sheet, Map<String, Integer> mColNameMaps,
                     int firstRow, int lastRow) ;
    public String build();
}
