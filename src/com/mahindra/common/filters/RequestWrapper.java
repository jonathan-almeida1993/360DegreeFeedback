package com.mahindra.common.filters;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public final class RequestWrapper extends HttpServletRequestWrapper {
	//private static Logger logger = Logger.getLogger(RequestWrapper.class);
	
	public RequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		
		if (values == null) {
			return null;
		}
		
		int count = values.length;
		String[] encodedValues = new String[count];
		
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);
		}
		
		return encodedValues;
	}

	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
	
		if (value == null) {
			return null;
		}
	
		return cleanXSS(value);
	}

	public String getHeader(String name) {

		String value = super.getHeader(name);
		if (value == null)
			return null;
	
		return cleanXSS(value);
	}

	private String cleanXSS(String value) {
		  if (value != null) {

	            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to

	            // avoid encoded attacks.

	            // value = ESAPI.encoder().canonicalize(value);

	            // Avoid null characters

	            value = value.replaceAll("", "");
	 
	            // Avoid anything between script tags
	            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);

	            value = scriptPattern.matcher(value).replaceAll("");

	            scriptPattern = Pattern.compile("<(.*?)>", Pattern.CASE_INSENSITIVE);

	            value = scriptPattern.matcher(value).replaceAll("");

	            value = value.replaceAll("[+=%^*()]",""); 
	            // Avoid anything in a src='...' type of expression

	            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	            value = scriptPattern.matcher(value).replaceAll("");

	 

	            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	            value = scriptPattern.matcher(value).replaceAll("");

	 

	            // Remove any lonesome </script> tag

	            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);

	            value = scriptPattern.matcher(value).replaceAll("");
	            // Remove any lonesome <script ...> tag
	            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");
	 
	            // Avoid eval(...) expressions
	            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");
	 
	            // Avoid expression(...) expressions
	            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	            value = scriptPattern.matcher(value).replaceAll("");
	            // Avoid javascript:... expressions

	            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);

	            value = scriptPattern.matcher(value).replaceAll("");

	            // Avoid vbscript:... expressions
	            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
	            value = scriptPattern.matcher(value).replaceAll("");
	            // Avoid onload= expressions
	           scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	            value = scriptPattern.matcher(value).replaceAll("");

	        }

		return value;
	}
}