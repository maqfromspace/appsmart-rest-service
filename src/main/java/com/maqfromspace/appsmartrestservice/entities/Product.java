package com.maqfromspace.appsmartrestservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue
    UUID id;
    @Column(name = "customer_id")
    UUID customerId;
    @Column
    String title;
    @Column
    String description;
    @Column
    Double price;
    @Column(name = "is_deleted")
    boolean isDeleted;
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "modified_at")
    LocalDateTime modifiedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}
