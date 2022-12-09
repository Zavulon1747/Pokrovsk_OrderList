package com.example.pokrovsk.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "amount")
    private int amount;
    @Column(name = "department")
    private String department;
    @Column(name = "price_of_selling")
    private double priceOfSelling;
    @CreationTimestamp
    @Column(name = "date_of_created")
    private LocalDateTime created;
    @Column(name = "date_of_modified")
    private LocalDateTime modified;
    @Column(name = "need_to_cancel")
    private boolean isNeedCancel;
    @Column(name = "removed")
    private boolean removed;

    public Product (String name, int amount, String department, double priceOfSelling) {
        this.name = name;
        this.amount = amount;
        this.department = department;
        this.priceOfSelling = priceOfSelling;
    }

    @PrePersist
    void onCreated() {
        this.setCreated(LocalDateTime.now());
        this.setModified(LocalDateTime.now());
        this.setNeedCancel(false);
        this.setRemoved(false);
    }

    @PreUpdate
    void onUpdate() {
        this.setModified(LocalDateTime.now());
    }
}
