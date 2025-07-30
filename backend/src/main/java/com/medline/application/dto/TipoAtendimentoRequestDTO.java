package com.medline.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TipoAtendimentoRequestDTO {

    @NotBlank(message = "O tipo de Atendimento não pode ser vazio...")
    @Size(max = 100, message = "O nome do tipo de atendimento não pode exceder 100 caracteres ")
    private String nome;

    @Size(max = 1000, message = "A descrição não pode excerder 1000 caracteres.")
    private String descricao;

    @Size(max = 100, message = "O nome do médico responsável não pode exceder 100 caracteres.")
    private String medicoResponsavel;
}
