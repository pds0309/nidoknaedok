package com.filter;

import com.utils.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "SessoionConfirmFilter")
public class SessoionConfirmFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        //
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        if (request.getSession().getAttribute(Constants.CURRENT_MEMBER_SESSION_NAME) == null) {
            response.sendRedirect(request.getContextPath() + "/login?req="+request.getServletPath());
            return;
        }
        chain.doFilter(request, response);
    }
}
