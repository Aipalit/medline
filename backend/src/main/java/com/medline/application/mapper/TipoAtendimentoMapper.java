package com.medline.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.medline.application.dto.TipoAtendimentoRequestDTO;
import com.medline.application.dto.TipoAtendimentoResponseDTO;
import com.medline.application.model.TipoAtendimento;

@Mapper(componentModel = "spring")
public interface TipoAtendimentoMapper {

    @Mapping(target = "id", ignore = true)
    TipoAtendimento toEntity(TipoAtendimentoRequestDTO dto);

    TipoAtendimentoResponseDTO toResponseDTO(TipoAtendimento tipoAtendimento);

    List<TipoAtendimentoResponseDTO> toResponseDTOList(List<TipoAtendimento> tipoAtendimento);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(TipoAtendimentoRequestDTO dto, @MappingTarget TipoAtendimento tipoAtendimento);
}
