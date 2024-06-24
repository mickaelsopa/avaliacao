package br.com.teste.application.dto;


import br.com.teste.domain.model.Produto;

public record CompraResponse(
        String nome,
        String cpf,
        Produto product,
        int quantidade,
        double valorTotal
) {}