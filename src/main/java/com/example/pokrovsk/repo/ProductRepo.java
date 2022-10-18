package com.example.pokrovsk.repo;

import com.example.pokrovsk.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "select * from product where name=?1", nativeQuery = true)
    Product findByName(String name);
}
