package com.longcreek.springboot.service;

import com.longcreek.springboot.exception.ResourceNotFoundException;
import com.longcreek.springboot.model.Product;
import com.longcreek.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{


    @Autowired
    private ProductRepository productRepository;


    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Optional<Product> productDb = this.productRepository.findById(product.getId());

        if(productDb.isPresent()){
            Product productUpdate = productDb.get();
            productUpdate.setId(product.getId());
            productUpdate.setName(product.getName());
            productUpdate.setDescription(product.getDescription());
            productRepository.save(productUpdate);
            return productUpdate;
        }else{
            throw new ResourceNotFoundException("Record not found with id : " + product.getId());
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {

        Optional<Product> productDb = this.productRepository.findById(productId);

        if(productDb.isPresent()){
            return productDb.get();
        }else{
            throw new ResourceNotFoundException("Record not found with id : " + productId);
        }
    }

    @Override
    public void deleteProduct(Long productId) {

        Optional<Product> productDb = this.productRepository.findById(productId);

        if(productDb.isPresent()){
            this.productRepository.delete(productDb.get());
        }else{
            throw new ResourceNotFoundException("Record not found with id : " + productId);
        }
    }
}
