package com.medline.application.repository;

import com.medline.application.model.Paciente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    List<Paciente> findByNomeContainingIgnoreCase(String nome);

    Optional<Paciente> findByCpf(String cpf);
}