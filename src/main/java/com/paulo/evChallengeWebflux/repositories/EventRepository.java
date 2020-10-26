package com.paulo.evChallengeWebflux.repositories;

import com.paulo.evChallengeWebflux.entities.Event;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends ReactiveMongoRepository<Event, String> {
}
