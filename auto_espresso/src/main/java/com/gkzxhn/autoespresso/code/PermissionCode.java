package com.gkzxhn.autoespresso.code;


import com.gkzxhn.autoespresso.config.ClassConfig;
import com.gkzxhn.autoespresso.config.Config;
import com.gkzxhn.autoespresso.operate.TPermissions;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Raleigh.Luo on 18/3/14.
 */

public class PermissionCode extends BaseCode {
    /**
     * shell命令获取权限
     * @param permissions
     * @return
     */
    public static String get_permission_shell(String... permissions){
        StringBuffer sb=new StringBuffer();
        sb.append(Config.TABS_LINE+Config.TABS_LINE+"if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {"+Config.WRAP);
        String format1=TABS_LINE+"TPermissions.get_permission_shell(%s)"+END_LINE;
        String format2=TABS_LINE+"TPermissions.get_permission_shell(\"%s\")"+END_LINE;
        for(String permission : permissions) {
            if(permission.contains("Manifest.permission")){
                sb.append(String.format(format1,permission));
            }else{
                sb.append(String.format(format2,permission));
            }
        }
        sb.append(Config.TABS_LINE+Config.TABS_LINE+"}"+Config.WRAP);
        return sb.toString();
    }
}
