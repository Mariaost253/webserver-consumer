package com.consumer.projectconsumer.db;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
@Entity
public class PublisherEvent {


    @Id
    private Integer publisherID;
    private String time; //date
    private Integer readings; //array

}
