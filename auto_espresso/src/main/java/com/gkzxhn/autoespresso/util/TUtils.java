package com.gkzxhn.autoespresso.util;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Raleigh.Luo on 18/3/13.
 */

public class TUtils {
    /**
     * 随机获取本地图片路径
     * @param contentResolver 路由查询
     * @param needImageCount 需要的图片张数
     */
    public static List<String> getRadomImagePaths(ContentResolver contentResolver , int needImageCount) {
        List<String> imagePaths =new ArrayList<>();
        if(needImageCount>0) {
            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            // 只查询jpeg jpg和png的图片
            Cursor mCursor = contentResolver.query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[]{"image/jpeg", "image/jpg", "image/png"},
                    MediaStore.Images.Media.DATE_MODIFIED);
            int  index = 0;
            while (mCursor.moveToNext() && index < needImageCount) {
                // 获取图片的路径
                String path = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                if (path == null) break;
                imagePaths.add(path);
                index++;
            }
        }
        return imagePaths;
    }
    /**
     *
     * @return 当前系统时间：格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTimeFormate() {
        SimpleDateFormat dataFormat = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss");
        return dataFormat.format(new Date());
    }
    /**字符串转换为整数
     * @param str
     * @return
     */
    public static int valueToInt(String str){
        int result = 0;
        try {
            if (isNum(str)) {
                result = Integer.valueOf(str);
            }
        }catch (Exception e){}
        return result;
    }

    /**字符串转换为double
     * @param str
     * @return
     */
    public static double valueToDouble(String str){
        double result=0.0;
        try {
            if(isNum(str))
                result=Double.valueOf(str);
        }catch (Exception e){}
        return result;
    }
    /**Float
     * @param str
     * @return
     */
    public static double valueFloat(String str){
        double result=0.0;
        try {
            if(isNum(str))
                result=Float.valueOf(str);
        }catch (Exception e){}
        return result;
    }
    /**字符串转换为Long
     * @param str
     * @return
     */
    public static double valueToLong(String str){
        double result=0.0;
        try {
            if(isNum(str))
                result=Long.valueOf(str);
        }catch (Exception e){}
        return result;
    }
    /**判断是否为数字 包括正数，负数，小数
     * @param str
     * @return 是纯数字则返回true
     */
    public  static boolean isNum(String str){
        boolean result=false;
        try{
            if(str!=null){
                Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*");
                Matcher isNum = pattern.matcher(str);
                result=isNum.matches();
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
    public static void createDir( String path) {
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
    }
    public static boolean valueToBoolean(String value) {
        boolean result=false;
        try{
            switch (value){
                case "1":
                case "true":
                case "Y":{
                    result=true;
                    break;
                }
                case "0":
                case "false":
                case "N":{
                    result=false;
                    break;
                }
                default:{
                    result =Boolean.valueOf(value);
                    break;
                }
            }
        }catch (Exception e){
            result=false;
        }
        return result;

    }

}
