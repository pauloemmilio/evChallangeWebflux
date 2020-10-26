package com.paulo.evChallengeWebflux.handlers;

import com.paulo.evChallengeWebflux.entities.Event;
import com.paulo.evChallengeWebflux.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class EventHandler {

    @Autowired
    private EventService eventService;

    private static final Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(eventService.findAll(), Event.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Event> eventMono = eventService.findById(id);
        return eventMono.flatMap(event ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(event))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Event> eventMono = request.bodyToMono(Event.class);
        return eventMono.flatMap(
                event -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(eventService.save(event), Event.class)
        );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Event> eventMono = request.bodyToMono(Event.class); //request
        Mono<Event> updatedEvent = eventMono.flatMap(
                event -> eventService.update(id, event));
        return updatedEvent.flatMap(event ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(event)
        .switchIfEmpty(notFound));
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        String id = request.pathVariable("id");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(eventService.deleteById(id), Void.class);
    }

    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(eventService.deleteAll(), Void.class);
    }
}
