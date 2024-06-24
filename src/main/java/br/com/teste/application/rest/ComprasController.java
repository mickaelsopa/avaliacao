package br.com.teste.application.rest;

import br.com.teste.application.dto.ClienteFielResponse;
import br.com.teste.application.dto.MaiorCompraResponse;
import br.com.teste.application.dto.CompraResponse;
import br.com.teste.application.dto.RecomendacaoResponse;
import br.com.teste.domain.service.CompraUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ComprasController {

    @Autowired
    private CompraUseCase useCase;

    @GetMapping("/compras")
    public List<CompraResponse> getCompras() {
        return useCase.getPurchases();
    }

    @GetMapping("/maior-compra/{ano}")
    public MaiorCompraResponse getMaiorCompra(@PathVariable int ano) {
        return useCase.getMaiorCompra(ano);
    }

    @GetMapping("/clientes-fieis")
    public List<ClienteFielResponse> getClientesFieis() {
        return useCase.getClientesFieis();
    }

    @GetMapping("/recomendacao/{cliente}/{tipo}")
    public RecomendacaoResponse getRecomendacao(@PathVariable String cliente, @PathVariable String tipo) {
        return useCase.getRecomendacao(cliente, tipo);
    }
}