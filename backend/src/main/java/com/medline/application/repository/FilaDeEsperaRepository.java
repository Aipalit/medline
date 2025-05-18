package com.medline.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medline.application.FilaDeEspera;

@Repository
public interface FilaDeEsperaRepository extends JpaRepository<FilaDeEspera, Integer> {
    List <FilaDeEspera> findByTipoAtendimento_IdAndStatusOrderByDataEntradaAsc(Integer tipoAtendimentoId, String status);

    List <FilaDeEspera> findByStatusOrderByDataEntradaAsc(String status);
}
