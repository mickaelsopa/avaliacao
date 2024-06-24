package br.com.teste.application.dto;

public record ClienteFielResponse(
        String nome,
        String cpf,
        double totalSpent
) {}