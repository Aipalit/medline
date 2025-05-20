package com.medline.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medline.application.model.TipoAtendimento;
import com.medline.application.repository.TipoAtendimentoRepository;

@Service
public class TipoAtendimentoService {

    @Autowired
    private TipoAtendimentoRepository tipoAtendimentoRepository;

    public List<TipoAtendimento> listarTipoAtendimentos() {
        return tipoAtendimentoRepository.findAll();
    }

    public Optional<TipoAtendimento> buscarTipoAtendimentoPorId(Integer id) {
        return tipoAtendimentoRepository.findById(id);
    }

    public TipoAtendimento salvarTipoAtendimento(TipoAtendimento tipoAtendimento) {
        return tipoAtendimentoRepository.save(tipoAtendimento);
    }

    public void deletarTipoAtendimento(Integer id) {
        tipoAtendimentoRepository.deleteById(id);
    }
}
