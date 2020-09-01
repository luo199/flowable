package com.huasisoft.flow.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.security.DefaultPrivileges;
import org.flowable.ui.common.security.SecurityUtils;
import org.jasig.cas.client.authentication.RegexUrlPatternMatcherStrategy;
import org.jasig.cas.client.authentication.UrlPatternMatcherStrategy;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huasisoft.flow.common.vo.JsonResult;
import com.huasisoft.h1.exception.ExcepCodeConst;

@Component
public class CheckUserFilter implements Filter{
	
	private UrlPatternMatcherStrategy ignoreUrlPatternMatcherStrategyClass = new RegexUrlPatternMatcherStrategy();
	
	@Value("${checkUserFilter.ignorePattern}")
	String ignorePattern; 
	
	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Override	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (!isRequestUrlExcluded(httpRequest)) {
			String type = httpRequest.getHeader("Content-Type");
			if (StringUtils.isNotEmpty(type) && (type.contains("application/x-www-form-urlencoded") || type.contains("application/json"))) {
				HttpSession session = httpRequest.getSession(false);
				Assertion assertion = session != null ? (Assertion) session.getAttribute("_const_cas_assertion_") : null;
				if (assertion == null) {
					JsonResult<Object> jsonResult = new JsonResult<Object>(ExcepCodeConst.USER_LOGIN_EXPIRED_EXCEP.getMessage(), ExcepCodeConst.USER_LOGIN_EXPIRED_EXCEP.getState());
					httpResponse.setStatus(400);
					httpResponse.setContentType("application/json; charset=utf-8");
					httpResponse.getWriter().print(jacksonObjectMapper.writeValueAsString(jsonResult));
					return;
				}
				/*RemoteUser user = new RemoteUser();
				//user.setId("admin");
				//user.setDisplayName("Administrator");
				//user.setFirstName("Administrator");
				//user.setLastName("Administrator");
				user.setEmail("admin@flowable.com");
				user.setPassword("123456");
				Object currentUserID = assertion.getPrincipal().getAttributes().get("ID");
				user.setId(String.valueOf(currentUserID));
				Object name = assertion.getPrincipal().getAttributes().get("name");
				user.setEmail("");
				user.setDisplayName(String.valueOf(name));
				user.setFullName(String.valueOf(name));
				List<String> pris = new ArrayList<>();
				pris.add(DefaultPrivileges.ACCESS_MODELER);
				pris.add(DefaultPrivileges.ACCESS_IDM);
				pris.add(DefaultPrivileges.ACCESS_ADMIN);
				pris.add(DefaultPrivileges.ACCESS_TASK);
				pris.add(DefaultPrivileges.ACCESS_REST_API);
				user.setPrivileges(pris);
				SecurityUtils.assumeUser(user);
			//}*/
			}
			chain.doFilter(request, response);
		}
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ignoreUrlPatternMatcherStrategyClass.setPattern(ignorePattern);
	}
	
	private boolean isRequestUrlExcluded(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return this.ignoreUrlPatternMatcherStrategyClass.matches(requestUri);
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}
