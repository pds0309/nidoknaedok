package com.filter;

import com.errors.ErrorURL;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "ViewFilter")
public class URLConfirmFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String url = ((HttpServletRequest) request).getServletPath();
        if (ErrorURL.exist(url)) {
            request.setAttribute("url", url);
        }
        chain.doFilter(request, response);
    }
}