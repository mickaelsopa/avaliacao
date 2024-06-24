package br.com.teste.domain.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Cliente {
    private String nome;
    private String cpf;
    private List<Compra> compras;
}