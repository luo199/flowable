package com.huasisoft.flow.common.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.huasisoft.flow.common.exception.ExcepCodeConst;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.flow.platform.service.PlatformEngine;
import com.huasisoft.flow.platform.service.impl.CurrentUserHelper;
import com.huasisoft.flow.platform.vo.Resource;
import com.huasisoft.flow.platform.vo.RoleNode;

@Controller
@RequestMapping("/api/index")
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private PlatformEngine platformEngine;
	
	@Value("${defaultRelogUrl}")  
	String defaultRelogUrl; 
	
	@GetMapping(value ="/relogForward")
	public String relogForward(HttpSession session) {
		Object currentUrl = session.getAttribute("currentURL");
		if(currentUrl!=null && StringUtils.isNotEmpty((String)currentUrl)) {
		  return "redirect:"+currentUrl;
		}
		return defaultRelogUrl;
	}
	
	@GetMapping(value ="/gotoIndex")
	public String gotoIndex(HttpSession session) {
		Assertion assertion = session != null ? (Assertion) session.getAttribute("_const_cas_assertion_") : null;
		if(assertion!=null) {
			return "index";
		}else {
			return "404";
		}
		
	}

	@GetMapping(value ="/models")
	public String models(HttpSession session) {
		Assertion assertion = session != null ? (Assertion) session.getAttribute("_const_cas_assertion_") : null;
		if(assertion!=null) {
			return "model";
		}else {
			return "404";
		}
		
	}
	
	/**
	   *    根据登录人权限获取左侧资源菜单
	 * @return
	 */
	@GetMapping("/listResource")
	@ResponseBody
	public JsonResult<List<Resource>> listResource(HttpSession session) {
		String currentUserID = CurrentUserHelper.currentUserId();
		return new JsonResult<List<Resource>>(platformEngine.getResourceService().leftMenu(currentUserID));
	}

	/**
	 *    根据登录人权限获取左侧资源菜单
	 * @return
	 */
	@GetMapping("/listAllRoles")
	@ResponseBody
	public JsonResult<List<RoleNode>> listAllRoles() {
		JsonResult<List<RoleNode>> jsonResult = null;
		try {
			List<RoleNode> roles = platformEngine.getRoleService().listAllRole();
			jsonResult = new JsonResult<List<RoleNode>>(roles);
		} catch (Exception e) {
			logger.error("error", e);
			jsonResult = new JsonResult<List<RoleNode>>(e.getMessage(), ExcepCodeConst.EXCHANGE_SYS_EXCEP.getState());
		}
		return jsonResult;
	}
	/**
	 *  从门户获取常用应用
	 * @return
	 */
	@GetMapping("/listResourceFromCommonApp")
	@ResponseBody
	public JsonResult<Object> listResourceFromCommonApp(HttpSession session) {
		JsonResult<Object> jsonResult = new JsonResult<Object>("");
		/*try {
			Assertion assertion = session != null ? (Assertion) session.getAttribute("_const_cas_assertion_") : null;
			if(assertion!=null) {
				Object currentUserID = assertion.getPrincipal().getAttributes().get("ID");
				jsonResult = new JsonResult<Object>(dealCommonAppsService.getCommonApps((String)currentUserID, null));//设置导航栏允许最大值
			}
		} catch (Exception e) {
			logger.error("error", e);
			jsonResult = new JsonResult<Object>(e.getMessage(), ExcepCodeConst.EXCHANGE_SYS_EXCEP.getState());
		}*/
		return jsonResult;
	}
}
