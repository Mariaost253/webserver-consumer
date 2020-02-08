package com.consumer.projectconsumer.webserver;


import com.consumer.projectconsumer.db.PublisherEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class Controller {

    @Autowired
    ServicePublissher servicePublissher;

    @GetMapping("/v1/publisherevent/all")
    public ResponseEntity<?> getAll() {
        return servicePublissher.findAll();
    }

    @GetMapping("/v1/publisherevent/{publisherID}")
    public ResponseEntity<?> getOne(@PathVariable(name="publisherID")Integer id) {
        return servicePublissher.findOne(id);
    }

    @PostMapping("/v1/publisherevent")
    public ResponseEntity<?> savePublisher(@RequestBody PublisherEvent publisherEntity){
        return servicePublissher.save(publisherEntity);
    }

    @DeleteMapping("/v1/publisherevent/{publisherID}")
    public ResponseEntity<?> deletePublisher(@PathVariable(name="publisherID")Integer id){
        return servicePublissher.delete(id);
    }

    @PutMapping("/v1/publisherevent")
    public ResponseEntity<?>  updatePublisher(@RequestBody PublisherEvent publisherEntity){
        return  servicePublissher.save(publisherEntity);
    }

    @GetMapping("/v1/publisherevent/reading")//reading which exceeded 100 - marked
    public ResponseEntity<?>  findAllByReading(){
        return  servicePublissher.findAllByReading();
    }

}
