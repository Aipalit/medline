package com.medline.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medline.application.model.FilaDeEspera;
import com.medline.application.model.Paciente;
import com.medline.application.model.TipoAtendimento;
import com.medline.application.repository.FilaDeEsperaRepository;

@Service
public class FilaDeEsperaService {
    @Autowired
    private FilaDeEsperaRepository filaDeEsperaRepository;

    public List<FilaDeEspera> listaFilaDeEspera() {
        return filaDeEsperaRepository.findAll();
    }

    public Optional<FilaDeEspera> buscarFilaDeEsperaPorId(Integer id) {
        return filaDeEsperaRepository.findById(id);
    }

    public FilaDeEspera adicionarPacienteNaFila(Paciente paciente, TipoAtendimento tipoAtendimento) {
        FilaDeEspera fila = new FilaDeEspera();
        fila.setPaciente(paciente);
        fila.setTipoAtendimento(tipoAtendimento);
        fila.setStatus("AGUARDANDO");
        fila.setDataEntrada(LocalDateTime.now());
        return filaDeEsperaRepository.save(fila);

    }

    public List<FilaDeEspera> listarFilasPorTipoAtendimentoEAguardando() {
        // Assumindo "AGUARDANDO" como status inicial
        return filaDeEsperaRepository.findByTipoAtendimento_IdAndStatusOrderByDataEntradaAsc(null, "AGUARDANDO");
    }

    public FilaDeEspera iniciarAtendimento(Integer idFila) {
        Optional<FilaDeEspera> filaOptional = filaDeEsperaRepository.findById(idFila);
        return filaOptional.map(fila -> {
            fila.setStatus("EM_ATENDIMENTO");
            fila.setDataInicioAtendimento(java.time.LocalDateTime.now());
            return filaDeEsperaRepository.save(fila);
        }).orElseThrow(() -> new RuntimeException("Fila de espera com ID " + idFila + "não encontrado"));
    }

    public FilaDeEspera finalizarAtendimento(Integer idFila) {
        Optional<FilaDeEspera> filaOptional = filaDeEsperaRepository.findById(idFila);
        return filaOptional.map(fila -> {
            fila.setStatus("CONCLUIDO");
            fila.setDataFimAtendimento(java.time.LocalDateTime.now());
            return filaDeEsperaRepository.save(fila);
        }).orElseThrow(() -> new RuntimeException("Fila de espera com ID " + idFila + "não encontrada"));
    }

    public void removerDaFila(Integer id) {
        filaDeEsperaRepository.deleteById(id);
    }
}
