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

@WebFilter(urlPatterns = "/*")
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest) {
            StringBuffer url = httpServletRequest.getRequestURL();

            System.out.println(url);
        }

        if (response instanceof HttpServletResponse httpServletResponse) {
            int status = httpServletResponse.getStatus();
            System.out.println("MyFilter1 status: " + status);
        }

        chain.doFilter(request, response);
    }
}
