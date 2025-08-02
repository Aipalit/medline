package com.medline.application.controller;

import java.util.List;
import java.util.Optional;

//import org.antlr.v4.runtime.atn.SemanticContext.OR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medline.application.dto.TipoAtendimentoRequestDTO;
import com.medline.application.dto.TipoAtendimentoResponseDTO;
import com.medline.application.mapper.TipoAtendimentoMapper;
import com.medline.application.model.TipoAtendimento;
import com.medline.application.service.TipoAtendimentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tipo de Atendimento", description = "API Responsável para gerencimaneto dos tipos de atendimento")
@RestController
@RequestMapping("/api/tipos-atendimento")
public class TipoAtendimentoController {
    @Autowired
    private TipoAtendimentoService tipoAtendimentoService;

    @Autowired
    private TipoAtendimentoMapper tipoAtendimentoMapper;

    @GetMapping
    public ResponseEntity<List<TipoAtendimentoResponseDTO>> listarTiposAtendimento() {
        List<TipoAtendimento> tipos = tipoAtendimentoService.listarTipoAtendimentos();
        List<TipoAtendimentoResponseDTO> responseDTOs = tipoAtendimentoMapper.toResponseDTOList(tipos);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @Operation(description = "Busca o tipo de atendimento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TipoAtendimentoResponseDTO> buscarTipoAtendimentoPorId(@PathVariable Integer id) {
        return tipoAtendimentoService.buscarTipoAtendimentoPorId(id)
                .map(tipoAtendimentoMapper::toResponseDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Cadastra novo tipo de atendimento", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto TipoAtendimento para cadastro", required = true))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de atendimento cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<TipoAtendimentoResponseDTO> salvarTipoAtendimento(
            @Valid @RequestBody TipoAtendimentoRequestDTO requestDTO) {
        TipoAtendimento tipoAtendimento = tipoAtendimentoMapper.toEntity(requestDTO);
        TipoAtendimento novoTipo = tipoAtendimentoService.salvarTipoAtendimento(tipoAtendimento);
        TipoAtendimentoResponseDTO responseDTO = tipoAtendimentoMapper.toResponseDTO(novoTipo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")

    public ResponseEntity<TipoAtendimentoResponseDTO> atualizarTipoAtendimento(@PathVariable Integer id,
            @RequestBody TipoAtendimentoRequestDTO requestDTO) {

        // 1. Busque o Optional primeiro
        Optional<TipoAtendimento> tipoExistenteOpt = tipoAtendimentoService.buscarTipoAtendimentoPorId(id);

        // 2. Verifique se o Optional tem um valor
        if (tipoExistenteOpt.isPresent()) {
            // Se sim, pegue a entidade
            TipoAtendimento tipoExistente = tipoExistenteOpt.get();
            // Atualize a entidade com os dados do DTO
            tipoAtendimentoMapper.updateEntityFromDto(requestDTO, tipoExistente);
            // Salve a entidade atualizada
            TipoAtendimento tipoSalvo = tipoAtendimentoService.salvarTipoAtendimento(tipoExistente);
            // Crie e retorne a resposta de sucesso
            TipoAtendimentoResponseDTO responseDTO = tipoAtendimentoMapper.toResponseDTO(tipoSalvo);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            // Se não, retorne a resposta de não encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTipoAtendimento(@PathVariable Integer id) {
        if (tipoAtendimentoService.buscarTipoAtendimentoPorId(id).isPresent()) {
            tipoAtendimentoService.deletarTipoAtendimento(id);
            // Retorna NO_CONTENT (204) para sucesso
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } else {
            // NOT_FOUND (404) se não encontrar
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
