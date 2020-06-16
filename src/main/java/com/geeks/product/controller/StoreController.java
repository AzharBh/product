package com.geeks.product.controller;

import com.geeks.product.beans.Store;
import com.geeks.product.repository.StoreRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private static final Logger logger = LogManager.getLogger(StoreController.class);

    @Autowired
    private StoreRepository storeRepository;

    @PostMapping
    public ResponseEntity saveOrUpdateStore(@RequestBody Store store){

        if(store.getId() == null){
            logger.info("Adding New Store");
        }else{
            logger.info("Updating Existing Store");
        }
        if(store == null){
            // logger message to show the empty store object
            logger.error("Empty Store Object");

            //error message through headers to show the empty object message
            return new ResponseEntity("Please Fill all the fields", HttpStatus.BAD_REQUEST);
        }
        else if(store.getName() == null){
            logger.error("PLEASE SPECIFY THE STORE NAME");
            return new ResponseEntity("Store Name is Null ", HttpStatus.BAD_REQUEST);
        }
        else if(store.getAddress() == null){
            logger.error("PLEASE SPECIFY THE STORE ADDRESS");
            return new ResponseEntity("Store Address is Null", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(storeRepository.save(store));
    }

    @GetMapping("{id}")
    public ResponseEntity getSingleStore(@PathVariable Integer id){
        logger.info("Getting Single Store with ID : "+id);

        if(!storeRepository.existsById(id)){
            logger.error("STORE NOT FOUND");
            return new ResponseEntity("Store Not Found", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(storeRepository.getOne(id));
    }

    @GetMapping
    public ResponseEntity getAllStores() {

        logger.info("Getting All stores");

        return ResponseEntity.ok(storeRepository.findAll());

    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteSingleStore(@PathVariable Integer id){

        logger.info("Deleting The Store with ID :  "+id);
        if(!storeRepository.existsById(id)){
            logger.error("STORE NOT FOUND");
            return new ResponseEntity("Store Not Found", HttpStatus.BAD_REQUEST);
        }
        storeRepository.deleteById(id);

        return new ResponseEntity("Deleted Successfully", HttpStatus.OK);
    }


}
