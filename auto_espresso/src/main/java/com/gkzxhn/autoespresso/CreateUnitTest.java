package com.gkzxhn.autoespresso;

import com.gkzxhn.autoespresso.build.IReadSheets;
import com.gkzxhn.autoespresso.build.impl.ReadSheets;
import com.gkzxhn.autoespresso.config.Config;
import com.gkzxhn.autoespresso.config.TransformLanguage;
import com.gkzxhn.autoespresso.entity.DriverEntity;

/**
 * Created by Raleigh.Luo on 18/3/13.
 */

public class CreateUnitTest {
    public static void main(String[] args) {
        /*************************配置*****************************/
        //模块名称
        String MODULE_NAME="app";
        //包名
        String PACKAGE_NAME="com.gkzxhn.app";
        //单元测试用例文件路径
        String PATH="./auto_espresso/src/main/assets/unit_test_case.xls";
        /*************************代码区*****************************/
        //包名路径 .替代/
        String packagePath=PACKAGE_NAME.replace(".","/");
        //相对路径  ./Moduel名/src/androidTest/java/包名/unit
        // 如: ./wisidom/src/androidTest/java/com.xx/unit
        String unitPath=String.format("./%s/src/androidTest/java/%s%s",
                MODULE_NAME,packagePath, Config.UNIT_PACKAGE_NAME);
        DriverEntity driver=new DriverEntity();
        driver.setDriverFilePath(PATH);
        driver.setPackageName(PACKAGE_NAME);
        driver.setUnitPath(unitPath);
//        ExcelUtil.xls2String(driver.getDriverFilePath());
        /*************************执行代码*****************************/
        //读取Sheet
        IReadSheets read=new ReadSheets();
        //设置转化语言，默认是java
        read.setTransformLanguage(TransformLanguage.KOTLIN);
        read.read(driver,true);
        System.out.print("执行完成！");
        //创建集成测试并执行
//		read.executeAllTest(PACKAGE_NAME,unitPath);
    }
}
