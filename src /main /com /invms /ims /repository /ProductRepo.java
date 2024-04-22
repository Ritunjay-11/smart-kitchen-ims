package com.invms.ims.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.invms.ims.model.Product;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {

    // MongoDB uses different syntax for queries, we use the `@Query` annotation here
    @Query("{'minimumProducts' : {$lt : ?0}}")
    List<Product> findByMinimumProductsLessThan(int threshold);

    @Query("{'expirationDate' : {$lte : ?0}}")
    List<Product> findByExpirationDateLessThanEqual(LocalDate date);

}
