package com.huasisoft.flow.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			//if(StringUtils.isNotEmpty(type) && (type.contains("application/x-www-form-urlencoded") || type.contains("application/json"))){
				HttpSession session = httpRequest.getSession(false);
				Assertion assertion = session != null ? (Assertion) session.getAttribute("_const_cas_assertion_") : null;
				if(assertion==null){
					JsonResult<Object> jsonResult = new JsonResult<Object>(ExcepCodeConst.USER_LOGIN_EXPIRED_EXCEP.getMessage(), ExcepCodeConst.USER_LOGIN_EXPIRED_EXCEP.getState());
					httpResponse.setStatus(400);
					httpResponse.setContentType("application/json; charset=utf-8");
					httpResponse.getWriter().print(jacksonObjectMapper.writeValueAsString(jsonResult));
					return;
				}
		}
		chain.doFilter(request, response);
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
