package com.example.pokrovsk.controller;

import com.example.pokrovsk.amqp.RMQBean;
import com.example.pokrovsk.entity.Product;
import com.example.pokrovsk.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/products")
@Slf4j
public class ProductController {

    ProductRepo productRepo;
    RMQBean rmqBean;
    private final Environment env;

    @Autowired
    public ProductController(ProductRepo productRepo, RMQBean rmqBean, Environment env) {
        this.productRepo = productRepo;
        this.rmqBean = rmqBean;
        this.env = env;
    }


    @GetMapping
    public String getIndex() {
        return "index";
    }

    @GetMapping("/")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Product> getAllProducts() {
        List<Product> list = productRepo.findAll();
        log.info("All products had been gotten!");
        list = list.stream().sorted(Comparator.comparing(Product::getDepartment)).collect(Collectors.toList());
        return list;
    }

    @GetMapping("/department/{departmentName}")
    public List<Product> getAllProductsByDepartment(@PathVariable String departmentName) {
        List<Product> list = productRepo.findAll().stream().filter(p -> p.getDepartment().toLowerCase().contains(departmentName.toLowerCase())).sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        log.info("All products had been gotten!");
        rmqBean.receiveMessage(list.toString());
        list.forEach(prod -> rmqBean.sendTo("product.amqp.queue", prod));
        return list;
    }

    @GetMapping("/product/{productName}")
    public List<Product> getProductByName(@PathVariable String productName) {
        List<Product> list = productRepo.findAll().stream().filter(p -> p.getName().toLowerCase().contains(productName.toLowerCase())).sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        return list;
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody Product product) {
        if (product.getName().contains(");") || product.getDepartment().contains(");")) {
            log.error("Кто-то хочет внести вред в нашу базу данных");
            throw new RuntimeException("Invalid name");
        }
        productRepo.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProductForCancel(@PathVariable long id) {
        Product producto = productRepo.findById(id).isPresent() ? productRepo.findById(id).get() : new Product("null", 0, "null", 0);
        producto.setNeedCancel(true);
        productRepo.save(producto);
        return producto;
    }

    @DeleteMapping("/deleteAll_Yes")
    public String deleteAllProducts() {
        productRepo.deleteAll();
        return "All products had been deleted!";
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productRepo.findById(id).orElseGet(Product::new).setRemoved(true);
    }

    @GetMapping("/env")
    public String getEnv() {
        return env.getProperty("spring.datasource.password");
    }



}
