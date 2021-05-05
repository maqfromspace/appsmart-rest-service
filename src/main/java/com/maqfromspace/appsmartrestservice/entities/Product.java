package com.maqfromspace.appsmartrestservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Double price;
    @JsonIgnore
    @Column(name = "is_deleted")
    private boolean deleteFlag;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}
