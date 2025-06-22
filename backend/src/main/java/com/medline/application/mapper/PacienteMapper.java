package com.medline.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.medline.application.dto.PacienteRequestDTO;
import com.medline.application.dto.PacienteResponseDTO;
import com.medline.application.model.Paciente;

@Mapper(componentModel = "spring")
public interface PacienteMapper {

    // Permite acesso à instância do mapper de forma estática
    PacienteMapper INSTACE = Mappers.getMapper(PacienteMapper.class);

    // Mapeia de DTO de requisição para Entidade
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    Paciente toEntity(PacienteRequestDTO dto);

    // Mapeia de Entidade para DTO de resposta
    PacienteResponseDTO toResponseDTO(Paciente paciente);

    // Mapeia uma lista de Entidades para uma lista de DTOs de resposta
    List<PacienteResponseDTO> tResponseDTOList(List<Paciente> pacientes);

    // Atualiza uma entidade existente a partir de um DTO (para o método PUT)
     @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    void updateEntityFromDto(PacienteRequestDTO dto, @MappingTarget Paciente paciente);
}
