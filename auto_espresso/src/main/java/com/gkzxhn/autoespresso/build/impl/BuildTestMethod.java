package com.gkzxhn.autoespresso.build.impl;

import com.gkzxhn.autoespresso.build.IBuildTestMethod;
import com.gkzxhn.autoespresso.build.IBuildTestProcedure;
import com.gkzxhn.autoespresso.config.ClassConfig;
import com.gkzxhn.autoespresso.config.Config;
import com.gkzxhn.autoespresso.config.TableConfig;
import com.gkzxhn.autoespresso.entity.MergedRegionEntity;
import com.gkzxhn.autoespresso.util.ExcelUtil;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;

/**
 * Created by Raleigh.Luo on 18/3/7.
 */

public class BuildTestMethod implements IBuildTestMethod {
    private Sheet mSheet;
    private Map<String,Integer> mColNameMaps =new HashMap<>();
    private int mRow;
    private int mMaxRow;
    private String mModuleNumber;
    private IBuildTestProcedure mBuildTestProcedure=new BuildTestProcedure();

    @Override
    public void init(Sheet sheet,String mModuleNumber,int firstRow,int lastRow) {
        this.mSheet=sheet;
        this.mRow=firstRow;
        this.mMaxRow=lastRow;
        this.mModuleNumber=mModuleNumber;
        readHeaderNames(mRow);
        mRow++;//value行
    }

    @Override
    public String build() {
        StringBuffer sb=new StringBuffer();
        while (mRow<mMaxRow){
            MergedRegionEntity module = ExcelUtil.isMergedRegion(mSheet, mRow, mColNameMaps.get(TableConfig.CASE_NUMBER));
            int firstRow=mRow;
            int lastRow=mRow;
            if(module!=null&&module.isMergedRegion()){
                lastRow=module.getLastRow();
            }
            String isExcute=getValue(TableConfig.IS_EXECUTE);//是否执行
            String caseNumber=getValue(TableConfig.CASE_NUMBER);
            if(caseNumber.length()>0&&isExcute.equals("Y")){//用例编号必须有
                //方法名 组合形式
                String methodName=String.format("%s_%s",mModuleNumber,caseNumber);
                sb.append(writeMethod(methodName,getValue(TableConfig.CASE_NAME)
                        ,null));
                mBuildTestProcedure.init(mSheet,mColNameMaps,firstRow,lastRow);
                sb.append(mBuildTestProcedure.build());
                sb.append(writeMethodEnd());
            }
            mRow=lastRow+1;
        }
        return sb.toString();
    }

    @Override
    public String writeMethod(String caseNumber,String caseName,String precondition) {
        return ClassConfig.getTestMethod(caseName,caseNumber);
    }

    @Override
    public String writeMethodEnd() {
        if (Config.TEST_CLASS_SUFFIX.equals(Config.TEST_CLASS_SUFFIX_JAVA))
            return Config.TABS_LINE+"}"+Config.WRAP;
        else
            return Config.TABS_LINE+Config.TABS_LINE+"}"+Config.WRAP
                    + Config.TABS_LINE+"}"+Config.WRAP;
    }
    /**获取头部
     * @param row
     */
    @Override
    public void readHeaderNames(int row) {
        mColNameMaps.clear();
        List headers= Arrays.asList(TableConfig.CASE_HEADERS);
        int maxCol=mSheet.getRow(row).length-1;
        for(int col=0;col<maxCol;col++){
            String value=mSheet.getRow(row)[col].getContents();
            if (headers.contains(value)) {
                mColNameMaps.put(value, col);
            }
        }
    }
    private String getValue(String headername){
        Cell[] cells = mSheet.getRow(mRow);
        return cells[mColNameMaps.get(headername)].getContents();
    }


}
