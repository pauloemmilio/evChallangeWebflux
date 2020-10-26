package com.paulo.evChallengeWebflux.routers;

import com.paulo.evChallengeWebflux.handlers.EventHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class EventRouter {

    private final String ENDPOINT = "/api/events";

    @RouterOperations({
            @RouterOperation(path = ENDPOINT, beanClass = EventHandler.class, beanMethod = "findAll", method = RequestMethod.GET),
            @RouterOperation(path = ENDPOINT + "/{id}", beanClass = EventHandler.class, beanMethod = "findById", method = RequestMethod.GET),
            @RouterOperation(path = ENDPOINT, beanClass = EventHandler.class, beanMethod = "save", method = RequestMethod.POST),
            @RouterOperation(path = ENDPOINT + "/{id}", beanClass = EventHandler.class, beanMethod = "update", method = RequestMethod.PUT),
            @RouterOperation(path = ENDPOINT, beanClass = EventHandler.class, beanMethod = "deleteAll", method = RequestMethod.DELETE),
            @RouterOperation(path = ENDPOINT + "/{id}", beanClass = EventHandler.class, beanMethod = "deleteById", method = RequestMethod.DELETE)
    })
    @Bean
    public RouterFunction<ServerResponse> route(EventHandler handler) {
        return RouterFunctions
                .route(GET(ENDPOINT).and(accept(MediaType.APPLICATION_JSON)), handler::findAll)
                .andRoute(GET(ENDPOINT + "/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::findById)
                .andRoute(POST(ENDPOINT).and(accept(MediaType.APPLICATION_JSON)), handler::save)
                .andRoute(PUT(ENDPOINT + "/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::update)
                .andRoute(DELETE(ENDPOINT + "/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::deleteById)
                .andRoute(DELETE(ENDPOINT).and(accept(MediaType.APPLICATION_JSON)), handler::deleteAll);
    }
}
