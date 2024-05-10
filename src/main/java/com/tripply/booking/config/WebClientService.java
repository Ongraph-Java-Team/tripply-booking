package com.tripply.booking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.tripply.booking.constants.BookingConstant.BEARER;

@Component
public class WebClientService {

    @Autowired
    private WebClient webClient;

    public <T> T post(String relativeUri, Object requestPayload, ParameterizedTypeReference<T> responseType) {
        return webClient
                .post()
                .uri(relativeUri)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestPayload)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
    public <T> T get(String relativeUri, Class<T> responseType) {
        return webClient
                .get()
                .uri(relativeUri)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
    public <T> T postWithParameterizedTypeReference(String relativeUri, Object requestPayload, ParameterizedTypeReference<T> responseType,String accessToken) {
        return webClient
                .post()
                .uri(relativeUri)
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestPayload)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
    public <T> T getWithParameterizedTypeReference(String relativeUri, ParameterizedTypeReference<T> responseType,String accessToken) {
        return webClient
                .get()
                .uri(relativeUri)
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
    public <T> T put(String baseUri, String relativeUri,String jwtAccessToken, Class<T> responseType) {
        return webClient.mutate().baseUrl(baseUri).build()
                .put()
                .uri(relativeUri).header(HttpHeaders.AUTHORIZATION, BEARER + jwtAccessToken)
                .retrieve().bodyToMono(responseType).block();
    }

}
