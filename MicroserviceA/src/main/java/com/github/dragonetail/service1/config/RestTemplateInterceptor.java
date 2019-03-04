package com.github.dragonetail.service1.config;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
            final ClientHttpRequestExecution execution) throws IOException {

        final String token = RequestContext.getContext().getToken();

        request.getHeaders().add(RequestContext.JWT_AUTH_HEADER_KEY, token);

        return execution.execute(request, body);
    }
}