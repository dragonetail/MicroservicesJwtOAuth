package com.github.dragonetail.oauth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.dragonetail.oauth.service.UserDetailsSecurityService;
import com.github.dragonetail.util.HttpUtils;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsSecurityService userDetailsSecurityService;

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        final String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/auth")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {
        final String requestURI = request.getRequestURI();
        final String clientIp = HttpUtils.getClientIP(request);
        final String jwt = this.tokenProvider.getJwtToken(request);
        final String method = request.getMethod();

        if (StringUtils.isEmpty(jwt)) {
            log.warn("[{}]JWT empty token: {} {}, {}", clientIp, method,
                    requestURI);

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED: empty token");
            return;
        }

        try {
            if (log.isDebugEnabled()) {
                log.debug("[{}]JWT authentication start: {} ", clientIp, requestURI);
            }

            final Claims claims = this.tokenProvider.validateToken(jwt, clientIp);
            if (claims != null) {
                final Long userId = Long.parseLong(claims.getSubject());

                final UserPrincipal userPrincipal = (UserPrincipal) this.userDetailsSecurityService
                        .loadUserById(userId);
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userPrincipal, null, userPrincipal.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Cache client IP to user principal
                userPrincipal.setClientIp(clientIp);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                if (log.isInfoEnabled()) {
                    log.info("[{}]JWT authentication success: {} {}, {}[{}]", clientIp, method,
                            requestURI, userPrincipal.getUsername(),
                            userId);
                }
            } else {
                log.error("[{}]JWT authentication failed: {} {}, {}, {}, {}", clientIp, method,
                        requestURI, jwt);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED: failed to parse JWT token");
                return;
            }
        } catch (final Exception ex) {
            log.error("[{}]JWT authentication failed: {} {}, {}, {}, {}", clientIp, method,
                    requestURI, jwt, ex.getMessage(), ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED: failed to parse JWT token");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
