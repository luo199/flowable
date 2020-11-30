/*package com.huasisoft.flow.common.util;

import com.huasisoft.easyoffice.constant.DeptConst;
import com.huasisoft.h1.model.ORGDepartment;

import net.sf.json.JSONObject;

*//**
 * 部门工具类
 *
 *//*
public class DeptUtils {

    
     * 判断是否是文件法规科
     *
     
    public static boolean isOrgDept(ORGDepartment department) {
		String deptProperties = department.getProperties();
		
		try {
			if(deptProperties!=null) {
				JSONObject jsonObject = ObjectMapperSingleton.objectMapperSingleton.readValue(deptProperties, JSONObject.class);
				
				if(jsonObject!=null&&!jsonObject.isNullObject()&&jsonObject.has(DeptConst.ISORGDEPT_KEY)) {
					 return jsonObject.getBoolean(DeptConst.ISORGDEPT_KEY);
				} else {
					return false;
				}
			}else {
				return false;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
    }
    
   
}
*/