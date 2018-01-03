package com.mahindra.common.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mahindra.common.constants.CommonConstants;
import com.mahindra.common.utils.CommonUtilities;

/**
 * @author Jonathan Almeida
 */

public class AuthenticationFilter implements Filter {

	private ServletContext context;

	public void init(FilterConfig fConfig) throws ServletException {
		
		this.context = fConfig.getServletContext();
		CommonUtilities.SOP("------ AuthenticationFilter initialized -----");
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		HttpSession session = req.getSession(false);
		String loginURL = req.getContextPath() + "/login";
		String loginControllerURL = req.getContextPath() + "/LoginController";
		
		System.out.println("------->"+uri);
		boolean loginRequest = req.getRequestURI().startsWith(loginURL);
		boolean loginControllerRequest = req.getRequestURI().startsWith(loginControllerURL);
		
		boolean resourceRequest =  (req.getRequestURI().startsWith(req.getContextPath() + "/css") || req.getRequestURI().startsWith(req.getContextPath() + "/js")
				|| req.getRequestURI().startsWith(req.getContextPath() + "/fonts") || req.getRequestURI().startsWith(req.getContextPath() + "/images")) ? true : false;
		
		
		if ( (loginRequest || loginControllerRequest  || uri.equals("/360DegreeFeedback/")) && null != session) {
			
			System.out.println("AuthenticationFilter: SESSION NOT NULL :: LOGGED IN USER IS -- "+session.getAttribute(CommonConstants.SESSION_USERNAME)+
					"  INVALIDATING SESSION... ");			
			session.invalidate();
			session = null;
			System.out.println("AuthenticationFilter: SESSION DESTROYED---"+session);
			
		}

		String userName = null;
		if (null != session && null != session.getAttribute(CommonConstants.SESSION_USERNAME)) {
			
			String sessionId=session.getId();
			String ipAddress = req.getHeader("X-FORWARDED-FOR");
	        if(ipAddress==null)
	            ipAddress = request.getRemoteAddr();
	        String prevsIp = CommonUtilities.sessionValue.get(sessionId);
	        
	        if(prevsIp.equals(ipAddress) || prevsIp==null)
	        	userName = (String) session.getAttribute(CommonConstants.SESSION_USERNAME);
	        else
	        	session.invalidate();
	        
		}

		boolean loggedIn = (null != userName && !"".equalsIgnoreCase(userName)) ? true : false;
		
		if(loggedIn){
			
			if(uri.endsWith("user.html")){
				
				if(session.getAttribute(CommonConstants.SESSION_ROLE).equals("SA") || 
							session.getAttribute(CommonConstants.SESSION_ROLE).equals("SEA") || 
							session.getAttribute(CommonConstants.SESSION_ROLE).equals("COA")){
					
						res.sendRedirect(req.getContextPath()+"/index.html");
						
				}
				
			}else if(uri.endsWith("index.html")){
					if(!(session.getAttribute(CommonConstants.SESSION_ROLE).equals("SA") || 
							session.getAttribute(CommonConstants.SESSION_ROLE).equals("SEA") || 
							session.getAttribute(CommonConstants.SESSION_ROLE).equals("COA"))){
						
						res.sendRedirect(req.getContextPath()+"/user.html");
						
					}
				}
		}
		
		if (loginRequest || loginControllerRequest || loggedIn || resourceRequest ) {
			
			if (!resourceRequest) { // Prevent restricted pages from being cached.
				res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
				res.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				res.setDateHeader("Expires", 0); // Proxies.
			}
			
			chain.doFilter(request, response);
			
		} else {
			
			res.sendRedirect("login.html");
			
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


}
