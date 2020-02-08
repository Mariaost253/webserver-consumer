package com.consumer.projectconsumer.consumer;
import com.consumer.projectconsumer.services.PublisherEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;



@Component
@Slf4j
public class PublisherEventsConsumer {

   @Autowired
    private PublisherEventService publisherEventService;

    @KafkaListener(topics = {"publisher-events"})
    public void onMessage(ConsumerRecord<Integer,String> consumerRecord) throws JsonProcessingException {

        log.info("ConsumerRecord : {} ", consumerRecord );
        publisherEventService.processPublisherEvent(consumerRecord);

    }
}
