package com.fiap.campusride.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservaRequestDTO(

        @NotBlank(message = "O nome do passageiro e obrigatorio")
        String nomePassageiro
) {
}