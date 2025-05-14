package vn.minhtung.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.minhtung.laptopshop.domain.Product;
import vn.minhtung.laptopshop.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product handleSaveProduct(Product product) {
        Product minhtung = this.productRepository.save(product);
        return minhtung;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public List<Product> fetchProducts(){
        return this.productRepository.findAll();
    }

}
