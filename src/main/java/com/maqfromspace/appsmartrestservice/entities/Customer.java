package com.maqfromspace.appsmartrestservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//Customer entity
@ApiModel(value = "Customer", description = "Customer entity")
@Data
@Entity
@Table(name = "customers")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "id", example = "5e0062b5-9e54-4bdf-9b61-ee695b3beb4d", required = true)
    @Id
    @GeneratedValue
    private UUID id;
    @ApiModelProperty(notes = "title", example = "Maksim", required = true)
    @Column
    private String title;
    @JsonIgnore
    @Column(name ="is_deleted")
    private boolean deleteFlag;
    @ApiModelProperty(notes = "created at", example = "2021-05-07T00:47:42.408", required = true)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty(notes = "modified at", example = "2021-05-07T00:47:42.408", required = true)
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