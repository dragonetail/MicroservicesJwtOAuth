package com.github.dragonetail.service2.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.github.dragonetail.util.HttpUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse,
            final AuthenticationException e) throws IOException, ServletException {
        final String clientIp = HttpUtils.getClientIP(httpServletRequest);

        log.warn("[{}]Authentication failed: {} {}, {}", clientIp, httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI(), e.getMessage(), e);

        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
}
