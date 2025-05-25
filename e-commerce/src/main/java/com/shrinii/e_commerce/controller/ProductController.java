package com.shrinii.e_commerce.controller;


import com.shrinii.e_commerce.model.Product;
import com.shrinii.e_commerce.service.ProductService;
import jakarta.persistence.Id;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
  private   ProductService service;

    @RequestMapping("/")
    public String prductcontroller(){
        return  " hi well come to projecy";
    }

    @GetMapping("/products")
     public List<Product>  getAllProducts(){

        return service.getAllProducts();
     }

     @GetMapping("/product/{id}")
    public  Product getProductById(@PathVariable int id){
       return service.getProductById(id);
     }

   @PostMapping("/product")
   public  ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile){

       try {
           Product product1=service.addProduct(product,imageFile);
           return new ResponseEntity<>(product1,HttpStatus.CREATED);

       }catch (Exception e){
           return  new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
       }
   }

   @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product =service.getProductById(productId);
      byte[] imageFile= product.getImageDate();

      return  ResponseEntity.ok()
              .contentType(MediaType.valueOf(product.getImageType()))
              .body(imageFile);
   }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> upDateProduct(@PathVariable int id,
                                                @RequestPart Product product,
                                                @RequestPart(required = false) MultipartFile imageFile) {
        try {
            Product updatedProduct = service.updateProduct(id, product, imageFile);

            if (updatedProduct != null) {
                return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update product: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product=service.getProductById(id);

        if(product!=null){
            service.DeleteProduct(id);
            return  new ResponseEntity<>("deleted",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Failed to deleted",HttpStatus.BAD_REQUEST);
        }
    }


}


