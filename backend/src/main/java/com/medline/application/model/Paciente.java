package com.medline.application.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import com.medline.application.validation.CPF;

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

public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome não pode está em branco")
    @Column(nullable = false)
    private String nome;

    @Column(length = 1)
    private String sexo;

    @Column(unique = true, length = 20)
    private String rg;

    @NotBlank(message = "O CPF não pode está em branco")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O formato do CPF deve ser XXX.XXX.XXX-XX.")
    @CPF
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(length = 20)
    private String numeroTelefone;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

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
