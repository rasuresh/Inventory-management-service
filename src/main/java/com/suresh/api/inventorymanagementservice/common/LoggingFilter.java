package com.suresh.api.inventorymanagementservice.common;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class LoggingFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);
        CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse((HttpServletResponse) response);

        // Log Request
        String requestBody = wrappedRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        log.debug("[REQUEST] " + wrappedRequest.getMethod() + " " + wrappedRequest.getRequestURI());
        log.debug("[REQUEST BODY] " + requestBody);

        chain.doFilter(wrappedRequest, wrappedResponse);

        // Log Response
        String responseBody = new String(wrappedResponse.getCopy(), StandardCharsets.UTF_8);
        log.debug("[RESPONSE] Status: " + wrappedResponse.getStatus());
        log.debug("[RESPONSE BODY] " + responseBody);

        // Write back to actual response
        response.getOutputStream().write(wrappedResponse.getCopy());
    }
}
