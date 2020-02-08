package com.consumer.projectconsumer.wenServerTests.unit;
import com.consumer.projectconsumer.db.PublisherEvent;
import com.consumer.projectconsumer.webserver.ServicePublissher;
import com.consumer.projectconsumer.wenServerTests.AbstractTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.transaction.Transactional;



@Transactional
@Slf4j
public class ServiceUnitTest extends AbstractTest {

    @Autowired
    private ServicePublissher servicePublissher;

    @Before
    public void setup(){ }

    @Test
    public void shouldCreate(){
        PublisherEvent p = new PublisherEvent();
        p.setPublisherID(266);
        p.setReadings(1000);
        p.setTime("12:11");

        ResponseEntity<?> savedBookResponse = servicePublissher.save(p);
        Assert.assertEquals(HttpStatus.OK, savedBookResponse.getStatusCode());

        PublisherEvent publisherEntity = (PublisherEvent) savedBookResponse.getBody();
        Assert.assertEquals(1000, publisherEntity.getReadings().intValue());
        Assert.assertEquals("12:11", publisherEntity.getTime());
    }


    @Test
    @Timeout(8)
    public void shouldUpdate(){
        PublisherEvent p = new PublisherEvent();
        p.setPublisherID(266);
        p.setReadings(9999);
        p.setTime("12:11");

        ResponseEntity<?> savedResponse = servicePublissher.save(p);
        Assert.assertEquals(HttpStatus.OK, savedResponse.getStatusCode());

        PublisherEvent publisherEntity = (PublisherEvent) savedResponse.getBody();
        Assert.assertEquals(9999, publisherEntity.getReadings().intValue());
        Assert.assertEquals("12:11", publisherEntity.getTime());
    }

    @Test
    @Timeout(8)
    public void shouldGet(){

        ResponseEntity<?> savedResponse = servicePublissher.findOne(266);
        Assert.assertEquals(HttpStatus.OK, savedResponse.getStatusCode());

        PublisherEvent publisherEntity = (PublisherEvent) savedResponse.getBody();
        Assert.assertNotNull(publisherEntity);
    }

    @Test
    @Timeout(8)
    public void shouldDelete(){
        ResponseEntity<?> deletedResponse = servicePublissher.delete(266);
        Assert.assertEquals(HttpStatus.NO_CONTENT, deletedResponse.getStatusCode());
    }
}
