package com.maqfromspace.appsmartrestservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

//Customer entity
@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    UUID id;
    @Column
    String title;
    @JsonIgnore
    @Column(name ="is_deleted")
    boolean deleteFlag;
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