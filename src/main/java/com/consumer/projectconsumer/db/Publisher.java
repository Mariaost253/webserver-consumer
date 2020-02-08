package com.consumer.projectconsumer.db;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;




@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Builder
@Entity
public class Publisher {

    @Id
    private Integer publisherID;
    private String time; //date
    private String readings; //array
    @OneToOne
    @JoinColumn(name = "eventID")
    private PublisherEvent publisherEvent;

}