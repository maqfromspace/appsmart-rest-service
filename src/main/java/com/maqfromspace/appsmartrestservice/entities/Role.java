package com.maqfromspace.appsmartrestservice.entities;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Role for spring security
*/
@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue
    Long id;
    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;
}