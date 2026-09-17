package com.fiap.campusride.repositorio;

import com.fiap.campusride.entidade.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByCaronaId(Long caronaId);
}