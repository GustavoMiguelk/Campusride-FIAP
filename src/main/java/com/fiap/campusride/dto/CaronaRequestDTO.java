package com.fiap.campusride.dto;

import com.fiap.campusride.enums.TipoVeiculo;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.fiap.campusride.validacao.VagasCompativeisComVeiculo;

import java.time.LocalDateTime;

@VagasCompativeisComVeiculo
public record CaronaRequestDTO(

        @NotBlank(message = "O nome do motorista e obrigatorio")
        String nomeMotorista,

        @NotBlank(message = "A origem e obrigatoria")
        String origem,

        @NotBlank(message = "O destino e obrigatorio")
        String destino,

        @NotNull(message = "O horario de partida e obrigatorio")
        @Future(message = "O horario de partida deve ser no futuro")
        LocalDateTime horarioPartida,

        @NotNull(message = "O tipo de veiculo e obrigatorio")
        TipoVeiculo tipoVeiculo,

        @NotNull(message = "O total de vagas e obrigatorio")
        @Min(value = 1, message = "A carona deve ter pelo menos 1 vaga")
        Integer vagasTotais
) {
}