package com.medline.controller;

import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.atn.SemanticContext.OR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medline.application.model.TipoAtendimento;
import com.medline.application.service.TipoAtendimentoService;

@RestController
@RequestMapping("/api/tipos-atendimento")
public class TipoAtendimentoController {
    @Autowired
    private TipoAtendimentoService tipoAtendimentoService;

    @GetMapping
    public ResponseEntity<List<TipoAtendimento>> listarTiposAtendimento() {
        List<TipoAtendimento> tipos = tipoAtendimentoService.listarTipoAtendimentos();
        return new ResponseEntity<>(tipos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoAtendimento> buscarTipoAtendimentoPorId(@PathVariable Integer id) {
        Optional<TipoAtendimento> tipo = tipoAtendimentoService.buscarTipoAtendimentoPorId(id);
        return tipo.map(t -> new ResponseEntity<>(t, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<TipoAtendimento> salvarTipoAtendimento(@RequestBody TipoAtendimento tipoAtendimento) {
        TipoAtendimento novoTipo = tipoAtendimentoService.salvarTipoAtendimento(tipoAtendimento);
        return new ResponseEntity<>(novoTipo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")

    public ResponseEntity<TipoAtendimento> atualizarTipoAtendimento(@PathVariable Integer id,
            @RequestBody TipoAtendimento tipoAtendimentoAtualizado) {
        return null;
    }

}
