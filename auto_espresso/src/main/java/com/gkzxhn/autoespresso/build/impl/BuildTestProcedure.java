package com.gkzxhn.autoespresso.build.impl;

import com.gkzxhn.autoespresso.build.IBuildTestProcedure;
import com.gkzxhn.autoespresso.code.DrawerCode;
import com.gkzxhn.autoespresso.code.IntentCode;
import com.gkzxhn.autoespresso.code.ListViewCode;
import com.gkzxhn.autoespresso.code.RecyclerViewCode;
import com.gkzxhn.autoespresso.code.SystemCode;
import com.gkzxhn.autoespresso.code.ViewCode;
import com.gkzxhn.autoespresso.code.ViewPagerCode;
import com.gkzxhn.autoespresso.code.WebViewCode;
import com.gkzxhn.autoespresso.code.WindowCode;
import com.gkzxhn.autoespresso.config.TableConfig;
import com.gkzxhn.autoespresso.entity.MergedRegionEntity;
import com.gkzxhn.autoespresso.entity.ProcedureEntity;
import com.gkzxhn.autoespresso.util.ExcelUtil;
import com.gkzxhn.autoespresso.util.TUtils;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;

/**
 * Created by Raleigh.Luo on 18/3/7.
 */

public class BuildTestProcedure implements IBuildTestProcedure {
    private Sheet mSheet;
    private Map<String,Integer> mColNameMaps =null;
    private int mRow;
    private int mMaxRow;
    private ProcedureEntity mProcedureEntity=new ProcedureEntity();
    @Override
    public void init(Sheet sheet, Map<String,Integer> mColNameMaps,
                     int firstRow, int lastRow) {
        this.mSheet=sheet;
        this.mRow=firstRow;
        this.mMaxRow=lastRow;
        this.mColNameMaps=mColNameMaps;
    }

    @Override
    public String build() {
        StringBuffer sb=new StringBuffer();
        while (mRow<=mMaxRow){
            MergedRegionEntity module = ExcelUtil.isMergedRegion(mSheet, mRow, mColNameMaps.get(TableConfig.TESTING_PROCEDURE));
            int firstRow=mRow;
            int lastRow=mRow;
            if(module!=null&&module.isMergedRegion()){
                lastRow=module.getLastRow();
            }
            ArrayList<String> params=new ArrayList<>();
            int paramIndex=mColNameMaps.get(TableConfig.ACTION)+1;
            String paramName=mSheet.getRow(mRow)[paramIndex].getContents();//参数名
            while(paramIndex<=mColNameMaps.get(TableConfig.PARAM) ){
                if(isNotEmptyParam(paramName)) {
                    params.add(mSheet.getRow(mRow+1)[paramIndex].getContents());//参数值

                }
                paramIndex++;
                paramName =mSheet.getRow(mRow)[paramIndex].getContents();//参数名
            }
            mProcedureEntity.setTestingProcedure(getValue(TableConfig.TESTING_PROCEDURE));
            mProcedureEntity.setViewType( getValue(TableConfig.VIEW_TYPE));
            mProcedureEntity.setAction( getValue(TableConfig.ACTION));
            mProcedureEntity.setParams(params);
            sb.append(getProcedureCode());
            mRow=lastRow+1;
        }
        return sb.toString();
    }
    private String getProcedureCode(){
        String result = "";
            String name = String.format("%sCode", mProcedureEntity.getViewType());
            if (name.equals(ViewCode.class.getSimpleName())) {//View Code
                result = excuteMethod(ViewCode.class.getDeclaredMethods());
            } else if (name.equals(IntentCode.class.getSimpleName())) {//IntentCode
                result = excuteMethod(IntentCode.class.getDeclaredMethods());
            } else if (name.equals(ListViewCode.class.getSimpleName())) {
                result = excuteMethod(ListViewCode.class.getDeclaredMethods());
            } else if (name.equals(SystemCode.class.getSimpleName())) {
                result = excuteMethod(SystemCode.class.getDeclaredMethods());
            } else if (name.equals(RecyclerViewCode.class.getSimpleName())) {
                result = excuteMethod(RecyclerViewCode.class.getDeclaredMethods());
            } else if (name.equals(ViewPagerCode.class.getSimpleName())) {
                result = excuteMethod(ViewPagerCode.class.getDeclaredMethods());
            } else if (name.equals(WindowCode.class.getSimpleName())) {
                result = excuteMethod(WindowCode.class.getDeclaredMethods());
            }else if (name.equals(DrawerCode.class.getSimpleName())) {
                result = excuteMethod(DrawerCode.class.getDeclaredMethods());
            }else if (name.equals(WebViewCode.class.getSimpleName())) {
                result = excuteMethod(WebViewCode.class.getDeclaredMethods());
            }
        return result;
    }

    private String excuteMethod(Method[] methods){
        String result="";
        try {
            for(Method method: methods){
                if(method.getName().equals(mProcedureEntity.getAction())){
                    ArrayList<String> params=mProcedureEntity.getParams();
                    Class<?>[] types=method.getParameterTypes();
                    Object[] paramsArray=new Object[types.length];
                    //第一个参数是注释 procedurename
                    if(types.length==params.size()+1){
                        paramsArray[0]=mProcedureEntity.getTestingProcedure();
                        for(int i=0;i<params.size();i++){
                            paramsArray[i+1]=getParams(params.get(i),types[i+1]);
                        }
                        result= (String) method.invoke(null,paramsArray);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private Object getParams(String paramsStr,Class type){
        Object param=null;
        if(type.getName().equals(Integer.class.getName())){
            param= TUtils.valueToInt(paramsStr);
        }else if(type.getName().equals(Long.class.getName())){
            param= TUtils.valueToLong(paramsStr);
        }else if(type.getName().equals(Double.class.getName())){
            param= TUtils.valueToDouble(paramsStr);
        }else if(type.getName().equals(Float.class.getName())){
            param= TUtils.valueFloat(paramsStr);
        }else if(type.getName().equals(Boolean.class.getName())){//1:0
            if(paramsStr.equals("true")){
                param=true;
            }else if(paramsStr.equals("false")){
                param=false;
            }else{
                param = Boolean.valueOf(paramsStr);
            }
        }else{
            param=paramsStr;
        }
        return param;
    }

    private String getValue(String headername){
        Cell[] cells = mSheet.getRow(mRow);
        return cells[mColNameMaps.get(headername)].getContents();
    }
    /**是否不为空
     * @param charaString
     * @return
     */
    public boolean isNotEmptyParam(String charaString){
        if(charaString==null||charaString.length()==0||charaString.equals("#")||charaString.equals("0"))return false;
        else return true;
    }
}
