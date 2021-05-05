package com.maqfromspace.appsmartrestservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Customer entity
@Data
@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private String title;
    @JsonIgnore
    @Column(name ="is_deleted")
    private boolean deleteFlag;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    @JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> productList;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        productList = new ArrayList<>();
    }

    @PreUpdate
    public void preUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}