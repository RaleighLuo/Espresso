package com.gkzxhn.autoespresso.build;

import com.gkzxhn.autoespresso.config.TransformLanguage;
import com.gkzxhn.autoespresso.entity.DriverEntity;

/**
 * Created by Raleigh.Luo on 18/3/7.
 */

public interface IReadSheets {
     void setTransformLanguage(TransformLanguage language);
     void read(DriverEntity driver, boolean isClearUnitClassNames);
     void executeAllTest(String packagename, String testClassDir);
}
