package com.example.pokrovsk.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    private Long id;

    private String name;

    private int amount;

    private String department;

    private String priceOfSelling;

    private LocalDateTime created;

    private LocalDateTime modified;

    private boolean isNeedCancel;

    public Product (String name, int amount, String department, String priceOfSelling) {
        this.name = name;
        this.amount = amount;
        this.department = department;
        this.priceOfSelling = priceOfSelling;
    }

    @PrePersist
    void onCreated() {
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate() {
        this.setModified(LocalDateTime.now());
    }
}
