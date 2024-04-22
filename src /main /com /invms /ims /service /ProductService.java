package com.invms.ims.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invms.ims.model.Product;
import com.invms.ims.repo.ProductRepo;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

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

    @Scheduled(fixedRate = 60000) // Check every 60 seconds
    public void checkLowInventoryLevels() {
        // Assuming the field name in Product for minimum stock level is 'minimumProducts'
        List<Product> lowInventoryProducts = productRepo.findByMinimumProductsLessThan(2);
        for (Product product : lowInventoryProducts) {
            logger.warn("Low inventory alert for Product ID {}: Only {} left in stock.",
                        product.getId(), product.getMinimumProducts());
        }
    }

    @Scheduled(cron = "0 0 8 * * ?") // Check every day at 8 AM
    public void checkForExpiringProducts() {
        // Assuming the field name in Product for the expiration date is 'expirationDate'
        LocalDate today = LocalDate.now();
        List<Product> expiringProducts = productRepo.findByExpirationDateLessThanEqual(today.plusDays(7));
        for (Product product : expiringProducts) {
            logger.info("Expiry notification for Product ID {}: Expiring soon on {}.",
                        product.getId(), product.getExpirationDate());
        }
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
