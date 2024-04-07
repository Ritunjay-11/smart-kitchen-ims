package com.invms.ims.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invms.ims.model.Product;
import com.invms.ims.repo.ProductRepo;

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
}
