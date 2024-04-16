package com.invms.ims.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id; 
import org.springframework.data.mongodb.core.mapping.Document; 
  
import lombok.AllArgsConstructor; 
import lombok.Data; 
import lombok.NoArgsConstructor; 
  
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "productdetails") 
public class Product { 
    @Id
    private String id; 
    private String productCategory; 
    private String productName; 
    private Long rating; 
    private Long quality; 
    private Long maximumProducts; 
    private Long minimumProducts; 
    private LocalDate expirationDate;
  
} 
