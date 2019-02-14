package org.abner.samples.musicgathering.infrastructure.rest;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;

public class UserAgentInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(HttpHeaders.USER_AGENT,"Music Gathering/0.0.1 ( bajalahara@gmail.com )");
    }
}