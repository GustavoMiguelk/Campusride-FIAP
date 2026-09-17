package com.fiap.campusride.entidade;

import com.fiap.campusride.enums.SituacaoReserva;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A carona da reserva e obrigatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carona_id", nullable = false)
    private Carona carona;

    @NotBlank(message = "O nome do passageiro e obrigatorio")
    private String nomePassageiro;

    @NotNull
    private LocalDateTime momentoReserva = LocalDateTime.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    private SituacaoReserva situacao = SituacaoReserva.CONFIRMADA;
}