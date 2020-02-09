package com.consumer.projectconsumer.webserver;

import com.consumer.projectconsumer.db.PublisherEvent;
import com.consumer.projectconsumer.webserver.errors.CustomRestErrorResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class ServicePublissher {

    @Autowired
    PublisherRepository publisherRepository;


    public ResponseEntity<?> findAll() {
        ResponseEntity<?> responseEntity;
        try{
            List<PublisherEvent> publisherEntities = publisherRepository.findAll();
            responseEntity = new ResponseEntity<>(publisherEntities, HttpStatus.OK);
        } catch (Exception e){
            return CustomRestErrorResponseHandler.handleInternalServerError(e);
        }
        return responseEntity;
    }

    public ResponseEntity<?> delete(Integer id){
        try{
            publisherRepository.deleteById(id);
        } catch (Exception e) {
            return CustomRestErrorResponseHandler.handleInternalServerError(e);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ResponseEntity<?> findOne(Integer id) {
        Optional<PublisherEvent> publisherEntity = publisherRepository.findById(id);
        if (!publisherEntity.isPresent()) {
            return CustomRestErrorResponseHandler.handleNotFoundException(id);
        } else
            return new ResponseEntity<>(publisherEntity.get(), HttpStatus.OK);
    }

    public ResponseEntity<?> save(PublisherEvent publisherEntity) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        if(publisherEntity.getPublisherID() != null) {
            httpStatus = HttpStatus.OK;
            try {
                publisherRepository.save(publisherEntity);
            } catch (DataIntegrityViolationException e) {
                return CustomRestErrorResponseHandler.handleDataIntegrityViolationException(e);
            } catch (Exception e) {
                return CustomRestErrorResponseHandler.handleInternalServerError(e);
            }
            return new ResponseEntity<PublisherEvent>(publisherEntity, httpStatus);
        }
        else
            return null;
    }

    public ResponseEntity<?> findAllByReading() { //all reading above 100
        ResponseEntity<?> responseEntity;
        try{
            List<PublisherEvent> publisherEntities = publisherRepository.findByReading();
            responseEntity = new ResponseEntity<>(publisherEntities, HttpStatus.OK);
        } catch (Exception e){
            return CustomRestErrorResponseHandler.handleInternalServerError(e);
        }
        return responseEntity;
    }


    public void getCount(){
        publisherRepository.count();
    }








}
