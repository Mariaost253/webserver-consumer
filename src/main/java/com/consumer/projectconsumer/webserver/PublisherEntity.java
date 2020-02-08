package com.consumer.projectconsumer.webserver;
import com.consumer.projectconsumer.db.PublisherEvent;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Entity
@Table
//@EntityListeners(AuditingEntityListener.class)
public class PublisherEntity {

    @Id
    private Integer publisherID;
    @Column
    private String time; //date
    @Column
    private Integer readings; //array
    public PublisherEntity(){}
    public PublisherEntity(Integer p, String t,Integer r){
        this.publisherID =p;
        this.time=t;
        this.readings=r;
    }

    public Integer getPublisherID() {
        return publisherID;
    }

    public Integer getReadings() {
        return readings;
    }

    public String getTime() {
        return time;
    }

    public void setPublisherID(Integer publisherID) {
        this.publisherID = publisherID;
    }

    public void setReadings(Integer readings) {
        this.readings = readings;
    }

    public void setTime(String time) {
        this.time = time;
    }





}
