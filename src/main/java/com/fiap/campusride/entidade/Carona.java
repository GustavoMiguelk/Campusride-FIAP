package com.fiap.campusride.entidade;

import com.fiap.campusride.enums.SituacaoCarona;
import com.fiap.campusride.enums.SituacaoReserva;
import com.fiap.campusride.enums.TipoVeiculo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carona")
@Getter
@Setter
@NoArgsConstructor
public class Carona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do motorista e obrigatorio")
    private String nomeMotorista;

    @NotBlank(message = "A origem e obrigatoria")
    private String origem;

    @NotBlank(message = "O destino e obrigatorio")
    private String destino;

    @NotNull(message = "O horario de partida e obrigatorio")
    @Future(message = "O horario de partida deve ser no futuro")
    private LocalDateTime horarioPartida;

    @NotNull(message = "O tipo de veiculo e obrigatorio")
    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipoVeiculo;

    @NotNull(message = "O total de vagas e obrigatorio")
    @Min(value = 1, message = "A carona deve ter pelo menos 1 vaga")
    private Integer vagasTotais;

    @NotNull
    @Enumerated(EnumType.STRING)
    private SituacaoCarona situacao = SituacaoCarona.ABERTA;

    @OneToMany(mappedBy = "carona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    public long contarReservasConfirmadas() {
        return reservas.stream()
                .filter(r -> r.getSituacao() == SituacaoReserva.CONFIRMADA)
                .count();
    }

    public int vagasDisponiveis() {
        return (int) (vagasTotais - contarReservasConfirmadas());
    }
}