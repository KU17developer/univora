package com.univora.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class SessionFilter
 */
@WebFilter(urlPatterns = "/*" )
public class ASessionFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public ASessionFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		 HttpServletRequest httpRequest = (HttpServletRequest) request;
	     HttpServletResponse httpResponse = (HttpServletResponse) response;
	        
	     // 현재 요청 URI 가져오기
	     String uri = httpRequest.getRequestURI();
	     String contextPath = httpRequest.getContextPath(); // 컨텍스트 패스를 가져옴
	     String path = uri.substring(contextPath.length()); // 컨텍스트 패스 제거
	     
	     // ,"/login/findingpasswords.do"
	     
	     
  		// 로그인 페이지도 필터를 거치지 않도록 예외 처리@@#@#!
  		// index.jsp를 포함한 특정 경로 예외 처리 ( 현 로그인 페이지 )
 
// 		// 회원가입 페이지는 필터를 거치지 않도록 예외 처리
// 	    // 회원가입 페이지 : /login/signupagreement.do(로그인 페이지 회원가입 버튼), /login/signup.do (약관 동의 클릭), /login/signupend.do (회원가입 정보 입력 후 가입 버튼 클릭),
	     
	     List<String> permitPath=List.of("/","/index.jsp","/login/login.do",
	    		 "/login/signupagreement.do","/login/signup.do","/login/signupend.do",
	    		 "/login/findingpasswords.do","/resources","/admin/main.do");
	     			    
	     	   	      
	     
	     // 세션 확인
	        HttpSession session = httpRequest.getSession(false);
	        if (session == null || session.getAttribute("loginMember") == null) {
	            // 세션이 없으면 로그인 페이지로 리다이렉트
	        	if(permitPath.stream().anyMatch(pp->path.equals(pp)||(!pp.equals("/") && path.contains(pp)))) {
		        		chain.doFilter(request, response);
	        	}else {
	        		
	        		httpResponse.sendRedirect(httpRequest.getContextPath() + "/?error=1");
	        		return;
	        	}
	        }else {	        	
	        	// 세션이 있으면 요청을 계속 진행
	        	chain.doFilter(request, response);
	        }
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}


}

