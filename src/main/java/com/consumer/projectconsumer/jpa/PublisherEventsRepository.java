package com.consumer.projectconsumer.jpa;

import com.consumer.projectconsumer.db.PublisherEvent;
import org.springframework.data.repository.CrudRepository;

public interface PublisherEventsRepository extends CrudRepository<PublisherEvent,Integer> {
}
