package com.medline.application.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import com.medline.application.validation.CPF;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pacientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa um Paicente no Sistema")
public class Paciente {

    @Schema(description = "Identificador único do paciente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nome completo do paciente", example = "Caio Luccas", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome não pode está em branco")
    @Column(nullable = false)
    private String nome;

    @Schema(description = "Sexo do paciente (M,F,O).", example = "M")
    @Column(length = 1)
    private String sexo;

    @Schema(description = "Numero do RG do paciente (formato livre).", example = "12345789  SSP/ES")
    @Column(unique = true, length = 20)
    private String rg;

    @Schema(description = "CPF do paciente no formato XXX.XXX.XXX-XX.", example = "387.147.013-91", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O CPF não pode está em branco")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O formato do CPF deve ser XXX.XXX.XXX-XX.")
    @CPF
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Schema(description = "Número de telefone para contato.", example = "(27) 99999-1234")
    @Column(length = 20)
    private String numeroTelefone;

    @Schema(description = "Data e hora do cadastro do paciente.", example = "2025-05-27T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Schema(description = "Data de nascimento do paciente (formato YYYY-MM-DD).", example = "1990-12-31", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A Data de Nascimento não pode ser Nula")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    /**
     * Calcula e retorna a idade atual do paciente com base na data de nascimento.
     * Este método NÃO é persistido no banco de dados.
     * 
     * @return A idade do paciente em anos, ou null se a data de nascimento não
     *         estiver definida.
     */

    @Transient
    public Integer getIdade() {
        if (this.dataNascimento == null) {
            return null;
        }
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }

}
