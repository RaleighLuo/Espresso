package com.gkzxhn.autoespresso.build.impl;

import com.gkzxhn.autoespresso.build.IBuildTestClass;
import com.gkzxhn.autoespresso.build.IReadSheets;
import com.gkzxhn.autoespresso.config.ClassConfig;
import com.gkzxhn.autoespresso.config.Config;
import com.gkzxhn.autoespresso.config.TransformLanguage;
import com.gkzxhn.autoespresso.entity.DriverEntity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import jxl.Sheet;
import jxl.Workbook;

/**读取excel文本中的sheet表
 * Created by Raleigh.Luo on 18/3/7.
 */

public class ReadSheets implements IReadSheets {
    private IBuildTestClass mBuildTestClass=new BuildTestClass();
    @Override
    public void setTransformLanguage(TransformLanguage language) {
        switch (language){
            case JAVA:
                Config.TEST_CLASS_SUFFIX= Config.TEST_CLASS_SUFFIX_JAVA;
                Config.END_LINE= Config.END_LINE_JAVA;
                break;
            case KOTLIN:
                Config.TEST_CLASS_SUFFIX= Config.TEST_CLASS_SUFFIX_KOTLIN;
                Config.END_LINE= Config.END_LINE_KOTLIN;
                break;
        }
    }

    /**
     * 读取驱动列表并创建单元测试用例代码
     * @param driver
     */
    @Override
    public void read(DriverEntity driver,boolean isClearUnitClassNames) {
        File file =new  File(driver.getDriverFilePath());
        try {
            FileInputStream fis = new FileInputStream(file);
            StringBuilder sb = new StringBuilder();
            jxl.Workbook rwb = Workbook.getWorkbook(fis);
            Sheet[] sheets = rwb.getSheets();
            for (int sheetIndex = 0; sheetIndex < sheets.length; sheetIndex++) {
                Sheet sheet = rwb.getSheet(sheetIndex);
                String sheetname=sheet.getName();
                //过滤sheet配置文件
                if(!Arrays.asList(Config.FILTER_SHEET_NAMES).contains(sheetname)){
                    System.out.println("创建 sheet表 "+sheetname);
                    mBuildTestClass.init(driver.getDriverFilePath(),driver.getPackageName(),driver.getUnitPath(),sheet);
                    mBuildTestClass.build();
                }
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**创建集成测试并执行
     * @param packagename
     * @param testClassDir
     */
    @Override
    public void executeAllTest(String packagename,String testClassDir) {
        System.out.println("创建 集成测试类");
        String className="AllTestSuite";
        //所有集成测试文件
        String suiteFilePath=testClassDir+"/"+className+ Config.TEST_CLASS_SUFFIX;
        String suiteContent=ClassConfig.getSuiteClass(packagename,className, Config.MODULE_NAMES);
        File file = new File(suiteFilePath);
        if(file.exists())file.delete();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            writer.write(suiteContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String suitesFileClass=packagename+ Config.UNIT_PACKAGE_NAME.replace("/",".")+"."+className;
        executeTest(packagename,suitesFileClass);
    }


    /**执行测试用例
     * @param packagename 如：com.gkzxhn.wisdom
     * @param suitesFileClass  如：com.gkzxhn.wisdom.LoginActivityTest
     */
    private void executeTest(String packagename,String suitesFileClass){
        System.out.println("执行 集成测试类");
        String stop_app=String.format("adb shell am force-stop %s",packagename);
        String stop_test=String.format("adb shell am force-stop %s.test",packagename);
        //执行测试
        String excute_test=String.format("adb shell am instrument -w -r   -e debug false -e class %s " +
                "%s.test/android.support.test.runner.AndroidJUnitRunner",suitesFileClass,packagename);
        try {
            Runtime.getRuntime().exec(stop_app);
            Runtime.getRuntime().exec(stop_test);
            Runtime.getRuntime().exec(excute_test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
