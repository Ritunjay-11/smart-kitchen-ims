package com.invms.ims.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invms.ims.model.Product;
import com.invms.ims.repo.ProductRepo;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepo.findById(id);
    }

    public void addProduct(Product product) {
        productRepo.save(product);
    }

    public boolean deleteProductById(String id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean productExistsById(String id) {
        return productRepo.existsById(id);
    }


public byte[] generateCsvReport() {
    List<Product> products = getAllProducts();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PrintWriter writer = new PrintWriter(byteArrayOutputStream);

    writer.println("ID,Category,Name,Rating,Quality,Max Size,Min Size,Expiration Date");
    for (Product product : products) {
        writer.println(String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                product.getId(),
                product.getProductCategory(),
                product.getProductName(),
                product.getRating(),
                product.getQuality(),
                product.getMaximumProducts(),
                product.getMinimumProducts(),
                product.getExpirationDate()));
    }
    writer.flush();
    return byteArrayOutputStream.toByteArray();
    }
} 
