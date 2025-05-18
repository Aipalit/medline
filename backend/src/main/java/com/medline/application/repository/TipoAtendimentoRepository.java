package com.medline.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.medline.application.model.TipoAtendimento;

@Repository
public interface TipoAtendimentoRepository extends JpaRepository<TipoAtendimento, Integer> {

}