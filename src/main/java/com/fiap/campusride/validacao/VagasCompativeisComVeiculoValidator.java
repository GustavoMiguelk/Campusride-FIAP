package com.fiap.campusride.validacao;

import com.fiap.campusride.dto.CaronaRequestDTO;
import com.fiap.campusride.enums.TipoVeiculo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VagasCompativeisComVeiculoValidator
        implements ConstraintValidator<VagasCompativeisComVeiculo, CaronaRequestDTO> {

    @Override
    public boolean isValid(CaronaRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        TipoVeiculo tipoVeiculo = dto.tipoVeiculo();
        Integer vagasTotais = dto.vagasTotais();

        if (tipoVeiculo == null || vagasTotais == null) {
            return true;
        }

        boolean valido = vagasTotais <= tipoVeiculo.getCapacidadeMaxima();

        if (!valido) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "O veiculo " + tipoVeiculo + " comporta no maximo "
                                    + tipoVeiculo.getCapacidadeMaxima() + " vaga(s), mas foram informadas "
                                    + vagasTotais)
                    .addPropertyNode("vagasTotais")
                    .addConstraintViolation();
        }

        return valido;
    }
}