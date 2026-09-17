package com.fiap.campusride.controlador;

import com.fiap.campusride.entidade.Reserva;
import com.fiap.campusride.dto.ReservaRequestDTO;
import com.fiap.campusride.dto.ReservaResponseDTO;
import com.fiap.campusride.servico.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping("/api/caronas/{caronaId}/reservas")
    public ResponseEntity<ReservaResponseDTO> reservar(
            @PathVariable Long caronaId,
            @Valid @RequestBody ReservaRequestDTO dto) {
        Reserva reserva = reservaService.reservar(caronaId, dto);
        ReservaResponseDTO response = ReservaResponseDTO.from(reserva);
        return ResponseEntity.created(URI.create("/api/reservas/" + reserva.getId())).body(response);
    }

    @DeleteMapping("/api/reservas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long id) {
        reservaService.cancelar(id);
    }
}