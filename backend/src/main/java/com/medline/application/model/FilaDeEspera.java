package com.medline.application.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "filas_de_espera")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilaDeEspera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "tipo_atendimento_id", nullable = false)
    private TipoAtendimento tipoAtendimento;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataEntrada;

    @Column(length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'AGUARDANDO'")
    private String status;

    private LocalDateTime dataInicioAtendimento;
    private LocalDateTime dataFimAtendimento;
}
