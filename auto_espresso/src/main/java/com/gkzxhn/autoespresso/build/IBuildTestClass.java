package com.gkzxhn.autoespresso.build;

import jxl.Sheet;

/**
 * Created by Raleigh.Luo on 18/3/7.
 */

public interface IBuildTestClass {
    void init(String driverFilePath, String packagename, String testClassPath, Sheet sheet);
    void readModuleNames();
    void build();
    void createFile();
    void write(String classContent);
}
