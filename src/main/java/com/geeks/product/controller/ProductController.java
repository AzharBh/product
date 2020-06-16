package com.geeks.product.controller;

import com.geeks.product.beans.Product;
import com.geeks.product.beans.StoreProducts;
import com.geeks.product.repository.ProductRepository;
import com.geeks.product.repository.StoreProductRepository;
import com.geeks.product.repository.StoreRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);



    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreProductRepository storeProductRepository;

    @PostMapping
    public ResponseEntity addOrUpdateProduct(@RequestBody Product product){

        if(product.getId() == null){
            logger.info("POST Api called for Adding New Product");
        }else{
            logger.info("POST Api called for Updating Existing Product");
        }
        if(product.getName() == null){
            //logger to show the error message when product is null
            logger.error("Please Provide the Product Name");

            //set header message to show the product name null error with bad request
            return new ResponseEntity("Product Name is Null", HttpStatus.BAD_REQUEST);

        }
        else if(product.getEpuc() == null){
            logger.error("Please Provide the Estimated per unit cost");
            return new ResponseEntity("Product per unit cost is Null", HttpStatus.BAD_REQUEST);
        }

        if(product.getStore().size() == 0){
            return new ResponseEntity("Plz Select Store First", HttpStatus.BAD_REQUEST);
        }
       Product product1 =  productRepository.save(product);
        product.getStore().forEach(i->{
            if(storeRepository.existsById(i.getId())){
                StoreProducts sp = new StoreProducts();
                sp.setProduct(product1);
                sp.setStore(storeRepository.getOne(i.getId()));
                storeProductRepository.save(sp);
            }
        });

        return ResponseEntity.ok(product1);
    }

    @GetMapping("/store/{id}")
    public ResponseEntity findProductByStore(@PathVariable Integer id){

        logger.info(" GET APi called for Getting Products of particular store");

        if(!storeRepository.existsById(id)){
            logger.error("Product Not Found for deleting");
            return new ResponseEntity("Store Not Found", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(storeProductRepository.findByStore(storeRepository.getOne(id)));
    }




    @DeleteMapping("{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id){
        if(!productRepository.existsById(id)){
            logger.error("Product Not Found");
            return new ResponseEntity("Product Not Found", HttpStatus.BAD_REQUEST);
        }
        productRepository.deleteById(id);
        logger.info(" DELETE Api called for Product with ID : "+id+" Successfully Deleted");

        return new ResponseEntity("Product Deleted Successfully", HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity getSingleProduct(@PathVariable Integer id){
        logger.info("GET API called for retrieving product with ID : "+id);

        if(!productRepository.existsById(id)){
            logger.error("Product Not Found with ID "+id);
            return new ResponseEntity("Product Not Found", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(productRepository.getOne(id));
    }

    @GetMapping
    public ResponseEntity getAllProducts(){
        logger.info("GET API called for retrieving all the products: ");
        return ResponseEntity.ok(productRepository.findAll());
    }

}
