package com.consumer.projectconsumer.webserver;

import com.consumer.projectconsumer.db.PublisherEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface PublisherRepository extends JpaRepository<PublisherEvent, Integer> {

    @Query("SELECT e from PublisherEvent e where e.readings >=100 ")
    List<PublisherEvent> findByReading();
}



