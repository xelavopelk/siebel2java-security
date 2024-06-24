package ru.klepov.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    //сделано для эксперимента, чтобы попробовать другой способ возврата ошибки отличный от access-denied.html
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
        var res = String.format(jsonPayload, exception.getLocalizedMessage(), Calendar.getInstance().getTime());
        response.getOutputStream().write(res.getBytes(StandardCharsets.UTF_8));

    }
}