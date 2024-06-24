package br.com.teste.application.dto;


import br.com.teste.domain.model.Produto;

import java.util.List;

public record RecomendacaoResponse(
        List<Produto> recommendedProducts
) {}