package com.github.dragonetail.service1.config;

public class RequestContext {
    public static final String JWT_AUTH_HEADER_KEY = "Authorization";

    private static final ThreadLocal<RequestContext> threadLocal = new ThreadLocal<>();

    private String token;

    public static RequestContext getContext() {
        RequestContext context = threadLocal.get();

        if (context == null) {
            context = new RequestContext();
            threadLocal.set(context);
        }

        return context;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
