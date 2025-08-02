package com.medline.application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medline.application.dto.AdicionarNaFilaRequest; 
import com.medline.application.model.FilaDeEspera;
import com.medline.application.model.Paciente;
import com.medline.application.model.TipoAtendimento;
import com.medline.application.service.FilaDeEsperaService;
import com.medline.application.service.PacienteService;
import com.medline.application.service.TipoAtendimentoService;

@RestController
@RequestMapping("/api/filas")
public class FilaDeEsperaController {

    @Autowired
    private FilaDeEsperaService filaDeEsperaService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private TipoAtendimentoService tipoAtendimentoService;

    @GetMapping
    public ResponseEntity<List<FilaDeEspera>> listaFilasDeEspera() {
        List<FilaDeEspera> filas = filaDeEsperaService.listaFilaDeEspera();
        return new ResponseEntity<>(filas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilaDeEspera> buscarFilaDeEsperaporId(@PathVariable Integer id) {
        Optional<FilaDeEspera> fila = filaDeEsperaService.buscarFilaDeEsperaPorId(id);
        return fila.map(f -> new ResponseEntity<>(f, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    // 👇 CORREÇÃO NO PARÂMETRO DO MÉTODO
    public ResponseEntity<FilaDeEspera> adicionarPacienteNaFila(@RequestBody AdicionarNaFilaRequest request) {
        Optional<Paciente> pacienteOpt = pacienteService.buscarPacientePorId(request.getPacienteId());

        Optional<TipoAtendimento> tipoAtendimentoOpt = tipoAtendimentoService
                .buscarTipoAtendimentoPorId(request.getTipoAtendimentoId());
                
        if (pacienteOpt.isPresent() && tipoAtendimentoOpt.isPresent()) {
            FilaDeEspera novaFila = filaDeEsperaService.adicionarPacienteNaFila(pacienteOpt.get(),
                    tipoAtendimentoOpt.get());
            return new ResponseEntity<>(novaFila, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<FilaDeEspera> iniciarAtendimento(@PathVariable Integer id) {
        try {
            FilaDeEspera filaAtualizada = filaDeEsperaService.iniciarAtendimento(id);
            return new ResponseEntity<>(filaAtualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<FilaDeEspera> finalizarAtendimento(@PathVariable Integer id) {
        try {
            FilaDeEspera filaAtualizada = filaDeEsperaService.finalizarAtendimento(id);
            return new ResponseEntity<>(filaAtualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}