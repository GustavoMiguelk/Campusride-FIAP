package com.fiap.campusride.dto;

import com.fiap.campusride.entidade.Reserva;
import com.fiap.campusride.enums.SituacaoReserva;

import java.time.LocalDateTime;

public record ReservaResponseDTO(
        Long id,
        Long caronaId,
        String nomePassageiro,
        LocalDateTime momentoReserva,
        SituacaoReserva situacao
) {
    public static ReservaResponseDTO from(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getCarona().getId(),
                reserva.getNomePassageiro(),
                reserva.getMomentoReserva(),
                reserva.getSituacao()
        );
    }
}