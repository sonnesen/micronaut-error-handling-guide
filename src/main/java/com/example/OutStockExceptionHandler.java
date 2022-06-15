package com.example;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {OutStockExceptionHandler.class, ExceptionHandler.class })
public class OutStockExceptionHandler implements ExceptionHandler<OUtOfStockException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, OUtOfStockException exception) {
        return HttpResponse.ok(0);
    }
}
