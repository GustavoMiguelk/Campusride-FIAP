package com.fiap.campusride.controlador;

import com.fiap.campusride.entidade.Carona;
import com.fiap.campusride.dto.CaronaRequestDTO;
import com.fiap.campusride.dto.CaronaResponseDTO;
import com.fiap.campusride.servico.CaronaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/caronas")
public class CaronaController {

    private final CaronaService caronaService;

    public CaronaController(CaronaService caronaService) {
        this.caronaService = caronaService;
    }

    @PostMapping
    public ResponseEntity<CaronaResponseDTO> publicar(@Valid @RequestBody CaronaRequestDTO dto) {
        Carona carona = caronaService.publicar(dto);
        CaronaResponseDTO response = CaronaResponseDTO.from(carona, false);
        return ResponseEntity.created(URI.create("/api/caronas/" + carona.getId())).body(response);
    }

    @GetMapping
    public List<CaronaResponseDTO> listar(
            @RequestParam(value = "somenteDisponiveis", defaultValue = "true") boolean somenteDisponiveis) {
        List<Carona> caronas = somenteDisponiveis
                ? caronaService.listarDisponiveis()
                : caronaService.listarTodas();
        return caronas.stream().map(c -> CaronaResponseDTO.from(c, false)).toList();
    }

    @GetMapping("/{id}")
    public CaronaResponseDTO detalhar(@PathVariable Long id) {
        Carona carona = caronaService.buscarPorId(id);
        return CaronaResponseDTO.from(carona, true);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long id) {
        caronaService.cancelar(id);
    }
}