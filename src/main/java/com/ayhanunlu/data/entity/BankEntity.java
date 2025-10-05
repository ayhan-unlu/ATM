package com.ayhanunlu.data.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="bank")
public class BankEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id",unique=true,nullable=false)
    private int id;

    @Column(name="username",unique=true,nullable=false)
    private String username;

    @Column(name="password",nullable=false)
    private String password;
}
