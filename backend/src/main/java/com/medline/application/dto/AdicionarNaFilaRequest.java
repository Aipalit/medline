package com.medline.application.dto;

import lombok.Data;

@Data
public class AdicionarNaFilaRequest {
    private Integer pacienteId;
    private Integer tipoAtendimentoId;
}
