package org.example.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@WebFilter(urlPatterns = "/*")
public class MyFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest) {
            StringBuffer url = httpServletRequest.getRequestURL();

            System.out.println(url);
        }

        if (response instanceof HttpServletResponse httpServletResponse) {
            int status = httpServletResponse.getStatus();
            System.out.println("MyFilter2 status: " + status);
        }
        Enumeration<String> attributeNames = request.getAttributeNames();
        String s = attributeNames.nextElement();
        System.out.println(s);

        chain.doFilter(request, response);
    }
}
