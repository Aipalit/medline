package com.medline.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.medline.application.validation.CPF;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastrar ou atualizar um paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteRequestDTO {

    @Schema(description = "Nome completo do paciente", example = "Caio Luccas", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome não pode está em branco")
    @Size(max = 100, message = " o nome não pode exceder 100 caracteres.")
    private String nome;

    @Schema(description = "Sexo do paciente (M,F,O).", example = "M")
    @Size(max = 1, message = "O sexo deve ser  um unico caractere (M,F,O).")
    private String sexo;

    @Schema(description = "Numero do RG do paciente (formato livre).", example = "12345789  SSP/ES")
    private String rg;

    @Schema(description = "CPF do paciente no formato XXX.XXX.XXX-XX.", example = "387.147.013-91", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O CPF não pode está em branco")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "O formato do CPF deve ser XXX.XXX.XXX-XX.")
    @CPF
    private String cpf;

    @Schema(description = "Número de telefone para contato.", example = "(27) 99999-1234")
    private String numeroTelefone;

    @Schema(description = "Data de nascimento do paciente (formato YYYY-MM-DD).", example = "1990-12-31", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A Data de Nascimento não pode ser Nula")
    private LocalDate dataNascimento;
}
