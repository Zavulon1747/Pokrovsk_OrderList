package com.example.pokrovsk.controller;

import com.example.pokrovsk.entity.Product;
import com.example.pokrovsk.repo.ProductRepo;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController("api/v1/products")
@Slf4j
public class ProductController {

    ProductRepo productRepo;

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
    @GetMapping("/")
    public List<Product> getAllProducts() {
        List<Product> list = productRepo.findAll();
        log.info("All products had been gotten!");
        list = list.stream().sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        return list;
    }

    @GetMapping("/department_{departmentName}")
    public List<Product> getAllProductsByDepartment(@PathVariable String departmentName) {
        List<Product> list = productRepo.findAll();
        log.info("All products had been gotten!");
        list = list.stream().filter(p -> p.getDepartment().equals(departmentName)).sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        return list;
    }
    @GetMapping("/{productName}")
    public Product getProductByName(@PathVariable String productName) {
        return productRepo.findByName(productName);
    }

    @PostMapping("/")
    public void addProduct(Product product) {
        if (product.getName().contains(");")||product.getDepartment().contains(");")) {
            log.error("Кто-то хочет внести вред в нашу базу данных");
            throw new RuntimeException("Invalid name");
        }
        productRepo.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProductForCancel(@PathVariable long id) {
        Product product = productRepo.findById(id).isPresent() ? productRepo.findById(id).get() : new Product("null", 0, "null", "0");
        product.setNeedCancel(true);
        productRepo.save(product);
        return product;
    }

}
