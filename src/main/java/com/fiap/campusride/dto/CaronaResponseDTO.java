package com.fiap.campusride.dto;

import com.fiap.campusride.entidade.Carona;
import com.fiap.campusride.enums.SituacaoCarona;
import com.fiap.campusride.enums.TipoVeiculo;

import java.time.LocalDateTime;
import java.util.List;

public record CaronaResponseDTO(
        Long id,
        String nomeMotorista,
        String origem,
        String destino,
        LocalDateTime horarioPartida,
        TipoVeiculo tipoVeiculo,
        Integer vagasTotais,
        Integer vagasDisponiveis,
        SituacaoCarona situacao,
        List<ReservaResponseDTO> reservas
) {
    public static CaronaResponseDTO from(Carona carona, boolean incluirReservas) {
        return new CaronaResponseDTO(
                carona.getId(),
                carona.getNomeMotorista(),
                carona.getOrigem(),
                carona.getDestino(),
                carona.getHorarioPartida(),
                carona.getTipoVeiculo(),
                carona.getVagasTotais(),
                carona.vagasDisponiveis(),
                carona.getSituacao(),
                incluirReservas
                        ? carona.getReservas().stream().map(ReservaResponseDTO::from).toList()
                        : List.of()
        );
    }
}