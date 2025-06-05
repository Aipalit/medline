package com.medline.application.controller;

import com.medline.application.model.Paciente;
import com.medline.application.service.PacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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

@Tag(name = "Pacientes", description = "gerenciamento de pacientes")
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Operation(summary = "Lista todos os pacientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))

    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        List<Paciente> pacientes = pacienteService.listarPacientes();
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @Operation(description = "Busca um paciente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Paciente.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    @Parameter(description = "ID do paciente a ser buscado.", example = "1", required = true)
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(id);
        return paciente.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Cadastra um novo paciente", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = " Paciente para ser cadastrado. CPF deve ser único.", required = true, content = @Content(schema = @Schema(implementation = Paciente.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Paciente.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos para o paciente", content = @Content)
    })

    @PostMapping
    public ResponseEntity<Paciente> salvarPaciente(
            @Valid @RequestBody Paciente paciente) {

        Paciente novoPaciente = pacienteService.salvarPaciente(paciente);
        return new ResponseEntity<>(novoPaciente, HttpStatus.CREATED);

    }

    @Operation(summary = "Atualizar paciente existente", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Objeto Paciente com os dados atualizados.", required = true))
    // Adicionar @ApiResponses similares para PUT e DELETE futuramente

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizarPaciente(
            @Parameter(description = "ID do paciente a ser atualizado", required = true) @PathVariable Integer id,
            @Valid @RequestBody Paciente pacienteAtualizado) {
        Optional<Paciente> pacienteExistente = pacienteService.buscarPacientePorId(id);
        if (pacienteExistente.isPresent()) {
            pacienteAtualizado.setId(id);
            Paciente pacienteSalvo = pacienteService.salvarPaciente(pacienteAtualizado);
            return new ResponseEntity<>(pacienteSalvo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
