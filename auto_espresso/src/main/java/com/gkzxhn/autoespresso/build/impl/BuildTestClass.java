package com.gkzxhn.autoespresso.build.impl;


import com.gkzxhn.autoespresso.build.IBuildTestClass;
import com.gkzxhn.autoespresso.build.IBuildTestMethod;
import com.gkzxhn.autoespresso.code.PermissionCode;
import com.gkzxhn.autoespresso.config.ClassConfig;
import com.gkzxhn.autoespresso.config.Config;
import com.gkzxhn.autoespresso.config.TableConfig;
import com.gkzxhn.autoespresso.entity.MergedRegionEntity;
import com.gkzxhn.autoespresso.entity.ModuleEntity;
import com.gkzxhn.autoespresso.util.ExcelUtil;
import com.gkzxhn.autoespresso.util.TUtils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;

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
    public void init(String driverFilePath,String packagename, String testClassDir,  Sheet sheet) {
        mPackageName=packagename;
        mTestClassPath=testClassDir;
        File file=new File(driverFilePath);
        mSheet=sheet;
        mRow= Config.MODULE_FIRST_ROW;
        mMaxRow=mSheet.getRows();
        //读取Module Name
        readModuleNames();
        mRow++;//Module Value行
    }

    @Override
    public void readModuleNames() {
        List headers=Arrays.asList(TableConfig.MODULE_HEADERS);
        Cell[] cells = mSheet.getRow(mRow);
        for (int col = 0; col < cells.length; col++) {
            String value = cells[col].getContents();
            if (headers.contains(value)) {
                mModuleColNames.put(value, col);
            }
        }
    }

    @Override
    public void build() {
        if(mModuleColNames.size()>0) {
            String classname=getValue(TableConfig.CLASS_NAME);
            boolean isCreate=TUtils.valueToBoolean(getValue(TableConfig.IS_CREATE));
            //测试类名不能为空
            if(isCreate&&classname.length()>0) {
                header = new ModuleEntity();
                //获取ModuleNumber 合并的单元格
                MergedRegionEntity moduleNumber = ExcelUtil.isMergedRegion(mSheet, mRow,mModuleColNames.get(TableConfig.MODULE_NUMBER));
                int firstRow=mRow+2;
                if(moduleNumber!=null&&moduleNumber.isMergedRegion()){
                    firstRow = moduleNumber.getLastRow()+2;
                    header.setModuleNumber(moduleNumber.getValue());
                    header.setFirstRow(moduleNumber.getFirstRow());
                    header.setLastRow(moduleNumber.getLastRow());
                }else{
                    header.setModuleNumber(getValue(TableConfig.MODULE_NUMBER));
                    header.setFirstRow(mRow);
                    header.setLastRow(mRow);
                }
                int lastRow = mMaxRow;

                header.setModuleName(getValue(TableConfig.MODULE_NAME));
                header.setClassName(classname);
                header.setClassPackageName(getValue(TableConfig.CLASS_PACKAGE_NAME));
                header.setSharedPreferencesName(getValue(TableConfig.SHAREDPREFERENCES_NAME));
                createFile();
                mBuildTestMethod.init(mSheet, header.getModuleNumber(),firstRow, lastRow);
                String classContent = mBuildTestMethod.build();
                write(classContent);
            }
        }
    }


    private String getValue(String headername){
        Cell[] cells = mSheet.getRow(mRow);
        return cells[mModuleColNames.get(headername)].getContents();
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
            sb.append(ClassConfig.getClassModule(mClassName,header.getClassName(),getIntent()));


            /***********************所需权限*********************************/

            /********************************************************/
            //before method
            sb.append(ClassConfig.getBeforeMethod(getPermissions(),header.getSharedPreferencesName(),getSharedPreferences()));
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
    /**所需权限
     * @return
     */
    private String getIntent(){
        StringBuffer extras=new StringBuffer();
        int col=mModuleColNames.get(TableConfig.INTENT_EXTRA);
        for(int row=header.getFirstRow();row<=header.getLastRow();row++){

            String putExtra= mSheet.getRow(row)[col].getContents();
            if(putExtra.length()>0){
                String key=mSheet.getRow(row)[col+1].getContents();
                String value=mSheet.getRow(row)[col+2].getContents();
                extras.append(PermissionCode.getIntentExtras(putExtra,key,value));
            }
        }
        return  extras.toString();
    }

    /**所需权限
     * @return
     */
    private String getSharedPreferences(){
        StringBuffer mSharedPreferences=new StringBuffer();
        int col=mModuleColNames.get(TableConfig.SHAREDPREFERENCES);
        for(int row=header.getFirstRow();row<=header.getLastRow();row++){
            String putExtra= mSheet.getRow(row)[col].getContents();
            if(putExtra.length()>0){
                String extra="";
                String key=mSheet.getRow(row)[col+1].getContents();
                String value=mSheet.getRow(row)[col+2].getContents();
                mSharedPreferences.append(PermissionCode.getSharedpreference(putExtra,key,value));
            }
        }
        return  mSharedPreferences.toString();
    }
    /**所需权限
     * @return
     */
    private String getPermissions(){
        String permissions="";
        List<String> permissionArray= new ArrayList();
        for(int row=header.getFirstRow();row<=header.getLastRow();row++){
            String permission= mSheet.getRow(row)[mModuleColNames.get(TableConfig.PREMISSIONS)].getContents();
            if(permission.length()>0)permissionArray.add(permission);
        }
        if(permissionArray.size()>0){
            String[] a=new String[permissionArray.size()];
            permissionArray.toArray(a);
            permissions= PermissionCode.getPermissionShell(a);
        }
        return  permissions;
    }

}
