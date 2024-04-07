package com.invms.ims.repo;

import org.springframework.data.mongodb.repository.MongoRepository; 
import org.springframework.stereotype.Repository; 
  
import com.invms.ims.model.Product; 
  
@Repository
public interface ProductRepo extends MongoRepository<Product, String>{ 
  
} 
