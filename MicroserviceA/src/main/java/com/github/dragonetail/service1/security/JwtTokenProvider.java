package com.github.dragonetail.service1.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.dragonetail.service1.config.RequestContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConfigurationProperties(prefix = "app")
@Setter
@Getter
public class JwtTokenProvider {
    private String jwtSecret;
    private int jwtExpirationInMs;

    public Claims validateToken(final String authToken, final String clientIp) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.jwtSecret)
                    .parseClaimsJws(authToken)
                    .getBody();
        } catch (final SignatureException ex) {
            log.warn("[{}]JWT Signature verification failed: {}", clientIp, authToken);
        } catch (final MalformedJwtException ex) {
            log.warn("[{}]JWT token malformed: {}。", clientIp, authToken);
        } catch (final ExpiredJwtException ex) {
            log.warn("[{}]JWT token expired: {}", clientIp, authToken);
        } catch (final UnsupportedJwtException ex) {
            log.warn("[{}]JWT unsupported method: {}", clientIp, authToken);
        } catch (final IllegalArgumentException ex) {
            log.warn("[{}]JWT illegal argument: {}", clientIp, authToken);
        }
        return claims;
    }

    public String getJwtToken(final HttpServletRequest request) {
        final String bearerToken = request.getHeader(RequestContext.JWT_AUTH_HEADER_KEY);

        RequestContext.getContext().setToken(bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
