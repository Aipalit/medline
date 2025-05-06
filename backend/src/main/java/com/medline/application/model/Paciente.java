package com.medline.application.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    private Integer idade;

    @Column(length = 1)
    private String sexo;

    @Column(unique = true, length = 20)
    private String rg;

    @Column(nullable = false, unique = true, length = 20)
    private String cpf;

    @Column(length = 20)
    private String numeroTelefone;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;
}
