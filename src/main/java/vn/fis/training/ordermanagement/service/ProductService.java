package vn.fis.training.ordermanagement.service;

import vn.fis.training.ordermanagement.domain.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProductById(Long id);
    List<Product> findAll();
    Product findById(Long id);
}
