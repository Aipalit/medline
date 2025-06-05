package com.medline.application.model;

import io.swagger.v3.oas.annotations.media.Schema;
// import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Representa o tipo de aendimento oferecido pela clínica.")
@Entity
@Table(name = "tipos_atendimento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAtendimento {

    @Schema(description = "Idenitificador unico do tipo de atendimento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(description = "Nome do tipo de Atendimento.", example = "Cardiologista", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O tipo de Atendimento não pode ser vazio...")
    @Size(max = 100, message = "O nome do tipo de atendimento não pode exceder 100 caracteres ")
    @Column(nullable = false, unique = true, length = 100)
    private String nome;

    @Schema(description = "Descrição detalhada do tipo de Atendimento", example = "Consulta de rotina para avaliação Cardio. ")
    @Size(max = 1000, message = "A descrição não pode excerder 1000 caracteres.")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Schema(description = "Nome do médico responsável pelo tipo de atendimento ou setor", example = "Dr. Gustava / Cardiologia")
    @Column(length = 255)
    private String medicoResponsavel;

}
