package br.com.teste.domain.service;

import br.com.teste.application.dto.ClienteFielResponse;
import br.com.teste.application.dto.MaiorCompraResponse;
import br.com.teste.application.dto.CompraResponse;
import br.com.teste.application.dto.RecomendacaoResponse;

import java.util.List;

public interface CompraUseCase {

    List<CompraResponse> getPurchases();
    MaiorCompraResponse getMaiorCompra(int ano);
    List<ClienteFielResponse> getClientesFieis();
    RecomendacaoResponse getRecomendacao(String cliente, String tipo);
}

