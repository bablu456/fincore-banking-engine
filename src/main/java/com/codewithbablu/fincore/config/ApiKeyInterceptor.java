package com.codewithbablu.fincore.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    private static final String SECRET_API_KEY = "fincore-secret-123";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        String apiKey = request.getHeader("x-api-key");

        if(SECRET_API_KEY.equals(apiKey)){
            return true;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(" Access Denied: Invalid or Missing API Key");
        return false;
    }
}
