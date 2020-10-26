package com.paulo.evChallengeWebflux.services;

import com.paulo.evChallengeWebflux.entities.Event;
import com.paulo.evChallengeWebflux.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventService {

    @Autowired private EventRepository repository;

    public Flux<Event> findAll() {
        return repository.findAll();
    }

    public Mono<Event> findById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public Mono<Event> save(Event event){
        return repository.save(event);
    }

    public Mono<Event> update(String id, Event event) {
        return findById(id)
                .flatMap(existingEvent -> {
                    existingEvent.setName(event.getName());
                    return save(existingEvent);
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public Mono<Void> deleteById(String id) {
        return findById(id)
                .flatMap(existingEvent -> repository.deleteById(id));
    }

    public Mono<Void> deleteAll() {
        return repository.deleteAll();
    }
}
