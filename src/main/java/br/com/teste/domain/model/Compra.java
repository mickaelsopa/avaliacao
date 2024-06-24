package br.com.teste.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Compra {
    private String codigo;
    private int quantidade;
}