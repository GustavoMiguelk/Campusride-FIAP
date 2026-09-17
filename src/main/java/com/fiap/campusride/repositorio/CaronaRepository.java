package com.fiap.campusride.repositorio;

import com.fiap.campusride.entidade.Carona;
import com.fiap.campusride.enums.SituacaoCarona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaronaRepository extends JpaRepository<Carona, Long> {

    List<Carona> findBySituacao(SituacaoCarona situacao);
}