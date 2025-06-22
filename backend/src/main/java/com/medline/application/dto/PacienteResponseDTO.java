package com.medline.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PacienteResponseDTO {
    private Integer id;
    private String nome;
    private String sexo;
    private String rg;
    private String cpf;
    private String numeroTelefone;
    private LocalDate dataNascimento;
    private LocalDateTime dataCadastro;
    private Integer idade;

}
