package com.byteminds.utils;

import javax.servlet.annotation.WebFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebFilter(urlPatterns = {"/*"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        
        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (path.startsWith("/layout") || path.equals("/css")|| path.equals("/registroweb/registro.xhtml")|| path.startsWith("/rest")) {
            // No aplicamos el filtro a /layout o /login
            chain.doFilter(req, res); 
            return;
        }

        
        String loginURL = request.getContextPath() + "/login.xhtml";

        boolean loggedIn = (session != null) && (session.getAttribute("jwt") != null);
        boolean loginRequest = request.getRequestURI().equals(loginURL);

        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(loginURL);
        }
    }

}