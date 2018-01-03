package com.mahindra.common.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrossScriptingFilter implements Filter {

	private String mode = "DENY";
	
    private FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) throws ServletException {
		
        this.filterConfig = filterConfig;
        String configMode = filterConfig.getInitParameter("mode");
        
        if ( configMode != null ) {
            mode = configMode;
        }

    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
    	
    	HttpServletResponse res = (HttpServletResponse)response;
    	res.addHeader("X-FRAME-OPTIONS", mode );
    	chain.doFilter(new RequestWrapper((HttpServletRequest) request), response);
        
    }

}