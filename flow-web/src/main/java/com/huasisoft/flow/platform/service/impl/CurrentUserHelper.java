package com.huasisoft.flow.platform.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.huasisoft.flow.platform.vo.Person;
/**
 * 获取当前用户的工具类
 * @author Administrator
 *
 */
public class CurrentUserHelper {

	/**
	 * 获取当前用户ID
	 * @return
	 */
	public static String currentUserId() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpSession session = request.getSession(false);
        Assertion assertion = session != null ? (Assertion) session.getAttribute("_const_cas_assertion_") : null;
        String currentUserId = null;
        if(assertion!=null) {
        	currentUserId = String.valueOf(assertion.getPrincipal().getAttributes().get("ID"));
        }
        return currentUserId;
	}
	/**
	 * 获取当前用户
	 * @return
	 */
	public static Person currentUser() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpSession session = request.getSession(false);
        Assertion assertion = session != null ? (Assertion) session.getAttribute("_const_cas_assertion_") : null;
        Person person = new Person();
        if(assertion!=null) {
        	Map<String, Object> uerAttr= assertion.getPrincipal().getAttributes();
        	person.setId(String.valueOf(uerAttr.get("ID")));
        	person.setName(String.valueOf(uerAttr.get("name")));
        	/*for (Entry<String,Object> entry : uerAttr.entrySet()) {
				System.out.println("key:"+entry.getKey()+",value:"+entry.getValue());
			}*/
        	/*key:deptName,value:华思应用支撑平台
        	key:organizationName,value:华思应用支撑平台
        	key:bureauID,value:c70ca9b9-8f27-4aa6-ada1-4543e448138e
        	key:deptVersion,value:1
        	key:deptID,value:c70ca9b9-8f27-4aa6-ada1-4543e448138e
        	key:mobile,value:15071496382
        	key:loginDate,value:1598493091628
        	key:DN,value:cn=系统管理员,o=华思应用支撑平台
        	key:organizationID,value:c70ca9b9-8f27-4aa6-ada1-4543e448138e
        	key:bureauVersion,value:1
        	key:password,value:96e79218965eb72c92a549dd5a330112
        	key:officePhone,value:84622620
        	key:loginName,value:systemadmin
        	key:name,value:系统管理员
        	key:personVersion,value:39
        	key:ID,value:{08A8D6CF-76DD-B343-A5D5-5B2B6082425E}
        	key:bureauName,value:华思应用支撑平台*/

        }
        return person;
	}
}
