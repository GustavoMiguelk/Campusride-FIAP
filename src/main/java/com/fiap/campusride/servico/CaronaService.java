package com.fiap.campusride.servico;

import com.fiap.campusride.entidade.Carona;
import com.fiap.campusride.enums.SituacaoCarona;
import com.fiap.campusride.enums.SituacaoReserva;
import com.fiap.campusride.dto.CaronaRequestDTO;
import com.fiap.campusride.excecao.BusinessException;
import com.fiap.campusride.excecao.ResourceNotFoundException;
import com.fiap.campusride.repositorio.CaronaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CaronaService {

    private final CaronaRepository caronaRepository;

    public CaronaService(CaronaRepository caronaRepository) {
        this.caronaRepository = caronaRepository;
    }

    @Transactional
    public Carona publicar(CaronaRequestDTO dto) {
        Carona carona = new Carona();
        carona.setNomeMotorista(dto.nomeMotorista());
        carona.setOrigem(dto.origem());
        carona.setDestino(dto.destino());
        carona.setHorarioPartida(dto.horarioPartida());
        carona.setTipoVeiculo(dto.tipoVeiculo());
        carona.setVagasTotais(dto.vagasTotais());
        carona.setSituacao(SituacaoCarona.ABERTA);
        return caronaRepository.save(carona);
    }

    public List<Carona> listarDisponiveis() {
        return caronaRepository.findBySituacao(SituacaoCarona.ABERTA);
    }

    public List<Carona> listarTodas() {
        return caronaRepository.findAll();
    }

    public Carona buscarPorId(Long id) {
        return caronaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carona nao encontrada: " + id));
    }

    @Transactional
    public void cancelar(Long id) {
        Carona carona = buscarPorId(id);

        if (carona.getSituacao() == SituacaoCarona.CONCLUIDA) {
            throw new BusinessException("Nao e possivel cancelar uma carona ja concluida");
        }
        if (carona.getSituacao() == SituacaoCarona.CANCELADA) {
            throw new BusinessException("Essa carona ja esta cancelada");
        }

        carona.setSituacao(SituacaoCarona.CANCELADA);

        carona.getReservas().stream()
                .filter(r -> r.getSituacao() == SituacaoReserva.CONFIRMADA)
                .forEach(r -> r.setSituacao(SituacaoReserva.CANCELADA));

        caronaRepository.save(carona);
    }

    @Transactional
    public void atualizarSituacaoPorOcupacao(Carona carona) {
        if (carona.getSituacao() != SituacaoCarona.ABERTA && carona.getSituacao() != SituacaoCarona.LOTADA) {
            return;
        }
        carona.setSituacao(carona.vagasDisponiveis() > 0 ? SituacaoCarona.ABERTA : SituacaoCarona.LOTADA);
        caronaRepository.save(carona);
    }
}