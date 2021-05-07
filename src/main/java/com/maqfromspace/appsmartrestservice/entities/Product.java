package com.maqfromspace.appsmartrestservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@ApiModel(value = "Product", description = "Product entity")
@Data
@Entity
@Table(name = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(notes = "id", example = "5e0062b5-9e54-4bdf-9b61-ee695b3beb4d", required = true)
    @Id
    @GeneratedValue
    private UUID id;
    @ApiModelProperty(notes = "title", example = "Snikers", required = true)
    @Column
    private String title;
    @ApiModelProperty(notes = "description", example = "Chocolate with nuts!")
    @Column
    private String description;
    @ApiModelProperty(notes = "price", example = "10.50", required = true)
    @Column
    private Double price;
    @JsonIgnore
    @Column(name = "is_deleted")
    private boolean deleteFlag;
    @ApiModelProperty(notes = "created at", example = "2021-05-07T00:47:42.408", required = true)
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ApiModelProperty(notes = "modified at", example = "2021-05-07T00:47:42.408")
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
