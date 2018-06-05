package com.gkzxhn.autoespresso.code;

/**
 * Created by Raleigh.Luo on 18/6/5.
 */

public class WebViewCode extends BaseCode {
    /**
     * 验证Dialog中是否有指定文字
     * dialog显示遮住后，底部的页面控件不能检测到
     * @param procedureName
     * @param text
     * @return
     */
    public static String click(String procedureName,String text) {
        String format=TABS_LINE+"//%s"+END_LINE
                +TABS_LINE+"TWindow.dialog_check_text(\"%s\")"+END_LINE;
        return String.format(format,procedureName,text);
    }

}
