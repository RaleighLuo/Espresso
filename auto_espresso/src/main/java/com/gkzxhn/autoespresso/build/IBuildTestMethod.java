package com.gkzxhn.autoespresso.build;


import jxl.Sheet;

/**
 * Created by Raleigh.Luo on 18/3/7.
 */

public interface IBuildTestMethod {
    void init(Sheet sheet, String moduleNumber, int firstRow, int lastRow);
    String build();
    String writeMethod(String caseNumber, String caseName, String precondition);
    String writeMethodEnd();
    void readHeaderNames(int row);
}
