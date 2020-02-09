package com.consumer.projectconsumer.wenServerTests.unit;
import com.consumer.projectconsumer.db.PublisherEvent;
import com.consumer.projectconsumer.webserver.Controller;
import com.consumer.projectconsumer.webserver.ServicePublissher;
import com.consumer.projectconsumer.wenServerTests.AbstractTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Transactional
@WebAppConfiguration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class ControllerUnitTest extends AbstractTest {
   // @Autowired
   protected MockMvc mvc;

   protected String uri= "/v1/publisherevent/{publisherID}";
   protected  String uri2= "/v1/publisherevent/";
    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Mock
    private ServicePublissher servicePublissher;

    @InjectMocks
    private Controller controller;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void testGetRequest() throws Exception {
        Integer id = 1;
        PublisherEvent data = getSetData();

        when(servicePublissher.findOne(id)).thenReturn(new ResponseEntity(data, HttpStatus.OK));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        PublisherEvent publisher = mapFromJson(content, PublisherEvent.class);
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertEquals("failure, Author did not match: ", data.getReadings(), publisher.getReadings());
    }

    @Test
    public void testNotFound() throws Exception {
        Integer id = 100;

        when(servicePublissher.findOne(id)).thenReturn(new ResponseEntity(null, HttpStatus.NOT_FOUND));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }

    @Test
    public void testCreateBook() throws Exception {

        PublisherEvent data = getSetData();
        when(servicePublissher.save(any(PublisherEvent.class))).thenReturn(new ResponseEntity(data, HttpStatus.CREATED));

        String inputJson = mapToJson(data);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(uri2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        PublisherEvent newEntity = mapFromJson(content, PublisherEvent.class);
        Assert.assertNotNull("failure - expected id attribute not null",
                newEntity.getPublisherID());
    }

    @Test
    public void testUpdate() throws Exception {
        PublisherEvent data = getSetData();
        Integer prevData = data.getReadings();
        data.setReadings(data.getReadings());
        Integer id = 1;

        when(servicePublissher.save(any(PublisherEvent.class))).thenReturn(new ResponseEntity(data, HttpStatus.OK));

        String inputJson = super.mapToJson(data);

        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.put(uri2, id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue(
                "failure - expected HTTP response body to have a value",
                content.trim().length() > 0);

        PublisherEvent updatedData = super.mapFromJson(content, PublisherEvent.class);
        Assert.assertNotNull("failure - expected entity not null",
                updatedData);
        Assert.assertNotEquals("failure - expected readings not to match",
                prevData, updatedData.getPublisherID());


    }

    @Test
    public void testDelete() throws Exception {
        int id=1;
        when(servicePublissher.delete(id)).thenReturn(new ResponseEntity(null, HttpStatus.NO_CONTENT));

        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id))
                .andReturn();

        // Extract the response status and body
        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty",
                content.trim().length() == 0);

    }



    private PublisherEvent getSetData() {
        PublisherEvent p = new PublisherEvent();
        p.setPublisherID(1);
        p.setTime("11:11");
        p.setReadings(700);
        return p;
    }


}
