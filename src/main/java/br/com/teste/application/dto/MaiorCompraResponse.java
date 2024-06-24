package br.com.teste.application.dto;

import br.com.teste.domain.model.Produto;

public record MaiorCompraResponse(
        String nome,
        String cpf,
        Produto product,
        int quantidade,
        double valorTotal
) {}