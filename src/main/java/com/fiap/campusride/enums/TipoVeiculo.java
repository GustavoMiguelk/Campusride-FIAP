package com.fiap.campusride.enums;

public enum TipoVeiculo {
    MOTO(1),
    CARRO_PEQUENO(3),
    CARRO_MEDIO(4),
    SUV(6),
    VAN(10);

    private final int capacidadeMaxima;

    TipoVeiculo(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
}