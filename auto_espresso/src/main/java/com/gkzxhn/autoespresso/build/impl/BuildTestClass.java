package com.gkzxhn.autoespresso.build.impl;

import com.gkzxhn.autoespresso.build.IBuildTestClass;
import com.gkzxhn.autoespresso.build.IBuildTestMethod;
import com.gkzxhn.autoespresso.code.PermissionCode;
import com.gkzxhn.autoespresso.config.ClassConfig;
import com.gkzxhn.autoespresso.config.Config;
import com.gkzxhn.autoespresso.config.TableConfig;
import com.gkzxhn.autoespresso.entity.ModuleEntity;
import com.gkzxhn.autoespresso.util.ExcelUtil;
import com.gkzxhn.autoespresso.util.TUtils;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raleigh.Luo on 18/3/7.
 */

public class BuildTestClass implements IBuildTestClass {
    private int mRow= Config.MODULE_FIRST_ROW;
    private Map<String,Integer> mModuleColNames =new HashMap<>();
    private String mFilePath="";
    private ModuleEntity header;
    private String mClassName="";
    private String mPackageName;
    private String mTestClassPath;
    private IBuildTestMethod mBuildTestMethod= new BuildTestMethod();
    private  Sheet mSheet;
    private int mMaxRow=0;
    @Override
    public void init(String driverFilePath,String packagename, String testClassDir,  int sheetIndex) {
        mPackageName=packagename;
        mTestClassPath=testClassDir;
        File file=new File(driverFilePath);
        mSheet=ExcelUtil.getSheet(file,sheetIndex);
        mRow= Config.MODULE_FIRST_ROW;
        mMaxRow=mSheet.getPhysicalNumberOfRows();
        //读取Module Name
        readModuleNames();
        mRow++;//Module Value行
    }

    @Override
    public void readModuleNames() {
        List headers=Arrays.asList(TableConfig.MODULE_HEADERS);
        int maxCol=mSheet.getRow(mRow).getLastCellNum();
        for (int col = 0; col < maxCol; col++) {
            String value = ExcelUtil.getCellValue(mSheet, mRow, col);
            if (headers.contains(value)) {
                mModuleColNames.put(value, col);
            }
        }
    }

    @Override
    public void build() {
        if(mModuleColNames.size()>0) {
            int firstRow = Config.CASE_FIRST_ROW;
            int lastRow = mMaxRow;

            String classname=getValue(TableConfig.CLASS_NAME);
            boolean isCreate=TUtils.valueToBoolean(getValue(TableConfig.IS_CREATE));
            //测试类名不能为空
            if(isCreate&&classname.length()>0) {
                header = new ModuleEntity();
                header.setModuleNumber(getValue(TableConfig.MODULE_NUMBER));
                header.setModuleName(getValue(TableConfig.MODULE_NAME));
                header.setClassName(classname);
                header.setIntentExtra(getValue(TableConfig.INTENT_EXTRA));
                header.setClassPackageName(getValue(TableConfig.CLASS_PACKAGE_NAME));
                header.setPremissions(getValue(TableConfig.PREMISSIONS));
                createFile();
                mBuildTestMethod.init(mSheet, firstRow, lastRow);
                String classContent = mBuildTestMethod.build();
                write(classContent);
            }
        }
    }


    private String getValue(String headername){
        return ExcelUtil.getCellValue(mSheet,mRow,mModuleColNames.get(headername));
    }

    @Override
    public void createFile() {
        if(header!=null) {

            String fileDir = mTestClassPath;
            TUtils.createDir(fileDir);

            String name=String.format(ClassConfig.TEST_CLASS_NAME, header.getClassName());
            //已有重复名，则生成添加Module编号module
            if(Config.MODULE_NAMES.contains(name)){
                name=String.format(ClassConfig.TEST_CLASS_NAME, header.getClassName()+header.getModuleNumber());
            }
            mClassName=name;
            Config.MODULE_NAMES.add(mClassName);
            String fileName = mClassName+ Config.TEST_CLASS_SUFFIX;

            //创建文件
            mFilePath = fileDir + "/" + fileName;
            File file = new File(mFilePath);
            //如果有则删除
            if (file.exists()) file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void write(String classContent) {
        File file = new File(mFilePath);
        try {
            StringBuffer sb = new StringBuffer();
            //写入import
            sb.append(ClassConfig.getImports(mPackageName,
                    header.getClassPackageName(),header.getClassName()));
            //头部注视
            sb.append(ClassConfig.getHeaders(header.getModuleName()+" " +header.getModuleNumber()));
            //创建类
            sb.append(ClassConfig.getClassModule(mClassName,header.getClassName(),header.getIntentExtra()));

            String permissions="";
            //所需权限
            if(header.getPremissions()!=null&&!header.getPremissions().isEmpty()){
                String[] permissionArray=header.getPremissions().split(";");
                permissions= PermissionCode.get_permission_shell(permissionArray);
            }

            //before method
            sb.append(ClassConfig.getBeforeMethod(permissions));
            //after method
            sb.append(ClassConfig.getAfterMethod());
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            sb.append(classContent);
            sb.append("\n}");
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
