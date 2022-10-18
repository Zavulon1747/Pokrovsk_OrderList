package com.example.pokrovsk.repo;

import com.example.pokrovsk.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = "select * from product where name LIKE CONCAT('%',:name,'%')", nativeQuery = true)
    Product findByName(@Param("name") String name);

    @Query(value = "select * from product where name LIKE CONCAT('%',?1,'%')", nativeQuery = true)
    List<Product> findAllByName(String productName);


}
