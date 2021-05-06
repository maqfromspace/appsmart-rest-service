package com.maqfromspace.appsmartrestservice.entities;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * User for spring security
 */
@Entity
@Table(name = "users")
@Data
public class User{
    @Id
    @GeneratedValue
    Long id;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private Date modifiedAt;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;
}
