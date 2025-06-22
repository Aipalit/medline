package com.medline.application.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

// import com.medline.application.validation.CPF;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Pattern;
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

    @Column(nullable = false)
    private String nome;

    @Column(length = 1)
    private String sexo;

    @Column(unique = true, length = 20)
    private String rg;

    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @Column(length = 20)
    private String numeroTelefone;

    @Schema(description = "Data e hora do cadastro do paciente.", example = "2025-05-27T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCadastro;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    /**
     * Calcula e retorna a idade atual do paciente com base na data de nascimento.
     * Este método NÃO é persistido no banco de dados.
     * 
     * @return A idade do paciente em anos, ou null se a data de nascimento não
     *         estiver definida.
     */

    @Schema(description = "Idade calculada do paciente (não armazenada, apenas leitura).", accessMode = Schema.AccessMode.READ_ONLY)
    @Transient
    public Integer getIdade() {
        if (this.dataNascimento == null) {
            return null;
        }
        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }

}
