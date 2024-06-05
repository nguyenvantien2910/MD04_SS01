package com.ra.session01.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "departments")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departId;
    @Column(nullable = false, length = 50,unique = true)
    private String departName;
    private Boolean status;

    @OneToMany(mappedBy = "department")
    List<Employee> employees;
}
