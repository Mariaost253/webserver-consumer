package com.consumer.projectconsumer.services;
import com.consumer.projectconsumer.db.PublisherEvent;
import com.consumer.projectconsumer.jpa.PublisherEventsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@SuppressWarnings("SpringJavaAutowiringInspection")
public class PublisherEventService {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate<Integer,String> kafkaTemplate;

    @Autowired
    private PublisherEventsRepository publisherEventsRepository;


    public void processPublisherEvent(ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {
        PublisherEvent publisherEvent = objectMapper.readValue(consumerRecord.value(), PublisherEvent.class);

        log.info("libraryEvent : {} ", publisherEvent);

        if(publisherEvent.getPublisherID()==null || publisherEvent.getPublisherID()==000){
            throw new RecoverableDataAccessException("Event Issues");
        }

        Optional<PublisherEvent> eventOptional = publisherEventsRepository.findById(publisherEvent.getPublisherID());
        if(!eventOptional.isPresent()){
            publisherEventsRepository.save(publisherEvent);
            log.info("Successfully Persisted the libary Event {} ", publisherEvent);
        }

    }


}
