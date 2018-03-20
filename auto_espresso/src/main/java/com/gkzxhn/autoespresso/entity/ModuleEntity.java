package com.gkzxhn.autoespresso.entity;

/**模块
 * Created by Raleigh.Luo on 18/2/24.
 */

public class ModuleEntity {
    private String moduleName;//模块名称
    private String moduleNumber;//模块编号
    private String className;//测试类名
    private String classPackageName;//所在包名
    private String premissions;//所需权限
    private String intentExtra;//intent extra

    public ModuleEntity() {
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleNumber() {
        return moduleNumber;
    }

    public void setModuleNumber(String moduleNumber) {
        this.moduleNumber = moduleNumber;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassPackageName() {
        return classPackageName;
    }

    public void setClassPackageName(String classPackageName) {
        this.classPackageName = classPackageName;
    }

    public String getPremissions() {
        return premissions;
    }

    public void setPremissions(String premissions) {
        this.premissions = premissions;
    }

    public String getIntentExtra() {
        return intentExtra;
    }

    public void setIntentExtra(String intentExtra) {
        this.intentExtra = intentExtra;
    }
}
