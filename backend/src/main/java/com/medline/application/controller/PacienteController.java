package com.medline.application.controller;

import com.medline.application.dto.PacienteRequestDTO;
import com.medline.application.dto.PacienteResponseDTO;
import com.medline.application.mapper.PacienteMapper;
import com.medline.application.model.Paciente;
import com.medline.application.service.PacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
// import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Pacientes", description = "Gerenciamento de Pacientes")
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private PacienteMapper pacienteMapper;

    @Operation(summary = "Lista todos os pacientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))

    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.listarPacientes();

        List<PacienteResponseDTO> responseDTOs = pacienteMapper.tResponseDTOList(pacientes);
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @Operation(description = "Busca um paciente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Paciente.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content)
    })
    @GetMapping("/{id}")

    public ResponseEntity<PacienteResponseDTO> buscarPacientePorId(
            @Parameter(description = "ID do paciente a ser buscado", example = "1", required = true) @PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(id);
        return paciente.map(pacienteMapper::toResponseDTO).map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Cadastra um novo paciente", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = " Paciente para ser cadastrado. CPF deve ser único.", required = true, content = @Content(schema = @Schema(implementation = Paciente.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Paciente.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para o paciente", content = @Content)
    })

    @PostMapping
    public ResponseEntity<PacienteResponseDTO> salvarPaciente(
            @Valid @RequestBody PacienteRequestDTO requestDTO) {

        // usando mapper para converter DTO -> entidade
        Paciente novoPaciente = pacienteMapper.toEntity(requestDTO);
        Paciente pacienteSalvo = pacienteService.salvarPaciente(novoPaciente);

        // usando mapper pra converter entidade -> DTO resposta
        PacienteResponseDTO responseDTO = pacienteMapper.toResponseDTO(pacienteSalvo);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar paciente existente", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto Paciente com os dados atualizados.", required = true))
    // Adicionar @ApiResponses similares para PUT e DELETE futuramente

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizarPaciente(
            @PathVariable Integer id,
            @Valid @RequestBody PacienteRequestDTO requestDTO) {

        return pacienteService.buscarPacientePorId(id).map(pacienteExistente -> {

            // Use o mapper para atualizar a entidade existente a partir do DTO

            pacienteMapper.updateEntityFromDto(requestDTO, pacienteExistente);
            Paciente pacienteSalvo = pacienteService.salvarPaciente(pacienteExistente);

            // Use o mapper para converter a entidade salva para o DTO de resposta

            PacienteResponseDTO responseDTO = pacienteMapper.toResponseDTO(pacienteSalvo);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @Operation(summary = "Exclui um paciente pelo seu ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(
            @Parameter(description = "ID do paciente a ser excluído", required = true) @PathVariable Integer id) {
        if (pacienteService.buscarPacientePorId(id).isPresent()) {
            pacienteService.deletarPaciente(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
