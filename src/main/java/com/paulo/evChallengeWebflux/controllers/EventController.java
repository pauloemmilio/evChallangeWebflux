package com.paulo.evChallengeWebflux.controllers;

import com.paulo.evChallengeWebflux.entities.Event;
import com.paulo.evChallengeWebflux.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired private EventService eventService;

    @GetMapping
    public Flux<Event> findAll(){
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Event> findById(@PathVariable String id) {
        return eventService.findById(id);
    }

    @PostMapping
    public Mono<Event> save(@RequestBody Event event) {
        return eventService.save(event);
    }

    @PutMapping("/{id}")
    public Mono<Event> update(@PathVariable String id, @RequestBody Event event){
        return eventService.update(id, event);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return eventService.deleteById(id);
    }

    @DeleteMapping
    public Mono<Void> deleteAll() {
        return eventService.deleteAll();
    }
}
