package com.fiap.campusride.servico;

import com.fiap.campusride.entidade.Carona;
import com.fiap.campusride.entidade.Reserva;
import com.fiap.campusride.enums.SituacaoCarona;
import com.fiap.campusride.enums.SituacaoReserva;
import com.fiap.campusride.dto.ReservaRequestDTO;
import com.fiap.campusride.excecao.BusinessException;
import com.fiap.campusride.excecao.ResourceNotFoundException;
import com.fiap.campusride.repositorio.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CaronaService caronaService;

    public ReservaService(ReservaRepository reservaRepository, CaronaService caronaService) {
        this.reservaRepository = reservaRepository;
        this.caronaService = caronaService;
    }

    @Transactional
    public Reserva reservar(Long caronaId, ReservaRequestDTO dto) {
        Carona carona = caronaService.buscarPorId(caronaId);

        if (carona.getSituacao() != SituacaoCarona.ABERTA) {
            throw new BusinessException(
                    "Nao e possivel reservar: a carona esta " + carona.getSituacao().name().toLowerCase());
        }
        if (carona.vagasDisponiveis() <= 0) {
            throw new BusinessException("Nao ha vagas disponiveis nessa carona");
        }

        Reserva reserva = new Reserva();
        reserva.setCarona(carona);
        reserva.setNomePassageiro(dto.nomePassageiro());
        reserva.setSituacao(SituacaoReserva.CONFIRMADA);
        Reserva salva = reservaRepository.save(reserva);

        caronaService.atualizarSituacaoPorOcupacao(carona);

        return salva;
    }

    @Transactional
    public void cancelar(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva nao encontrada: " + id));

        Carona carona = reserva.getCarona();
        if (carona.getSituacao() == SituacaoCarona.CONCLUIDA) {
            throw new BusinessException("Nao e possivel cancelar uma reserva de uma carona ja concluida");
        }
        if (reserva.getSituacao() == SituacaoReserva.CANCELADA) {
            throw new BusinessException("Essa reserva ja esta cancelada");
        }

        reserva.setSituacao(SituacaoReserva.CANCELADA);
        reservaRepository.save(reserva);

        caronaService.atualizarSituacaoPorOcupacao(carona);
    }
}