package com.medline.application.dto;

import lombok.Data;

@Data
public class TipoAtendimentoResponseDTO {
    private String id;
    private String nome;
    private String descricao;
    private String medicoReponsavel;
}
