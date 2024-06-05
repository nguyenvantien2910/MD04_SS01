package com.ra.session01.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "employees")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

    @NotBlank(message = "Full Name is required")
    private String fullname;

    @NotNull(message = "Birthday is required")
    @Past(message = "Birthday must be a past date")
    private Date birthday;

    @NotNull(message = "Gender is required")
    private Boolean gender;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be positive")
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "departId", nullable = false)
    @NotNull(message = "Department is required")
    private Department department;

    private String imageUrl;
}
