package com.example.pokrovsk.controller;

import com.example.pokrovsk.entity.Product;
import com.example.pokrovsk.repo.ProductRepo;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class ProductController {

    ProductRepo productRepo;

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts() {
        List<Product> list = productRepo.findAll();
        log.info("All products had been gotten!");
        list = list.stream().sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        return list;
    }

    @GetMapping("/products/department_{departmentName}")
    public List<Product> getAllProductsByDepartment(@PathVariable String departmentName) {
        List<Product> list = productRepo.findAll();
        log.info("All products had been gotten!");
        list = list.stream().filter(p -> p.getDepartment().equals(departmentName)).sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        return list;
    }

    public Product getProductByName(String name) {
        return productRepo.findByName(name);
    }

    public void addProduct(Product product) {
        if (product.getName().contains(");")||product.getDepartment().contains(");")) {
            log.error("Кто-то хочет внести вред в нашу базу данных");
            throw new RuntimeException("Invalid name");
        }
        productRepo.save(product);
    }

    @PutMapping("/products/{id}")
    public Product updateProductForCancel(@PathVariable long id) {
        Product product = productRepo.findById(id).isPresent() ? productRepo.findById(id).get() : new Product("null", 0, "null", "0");
        product.setNeedCancel(true);
        productRepo.save(product);
        return product;
    }

}
