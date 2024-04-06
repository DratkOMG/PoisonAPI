package com.example.poisontest.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author DratkOMG
 * @created 13:25 - 04/04/2024
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer age;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<House> ownerHouses;

    @ManyToMany(mappedBy = "residents", fetch = FetchType.LAZY)
    private List<House> livingHouses;
}
