package br.com.teste.application.rest;

import br.com.teste.application.dto.ClienteFielResponse;
import br.com.teste.application.dto.MaiorCompraResponse;
import br.com.teste.application.dto.CompraResponse;
import br.com.teste.application.dto.RecomendacaoResponse;
import br.com.teste.domain.service.CompraService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebMvcTest(ComprasController.class)
public class ComprasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompraService compraService;

    @Test
    public void testGetPurchasesWhenCalledThenReturnListOfPurchases() throws Exception {
        List<CompraResponse> expectedPurchases = Arrays.asList(
                new CompraResponse("Client1", "12345678901", null, 2, 200.0),
                new CompraResponse("Client2", "10987654321", null, 1, 100.0)
        );
        Mockito.when(compraService.getPurchases()).thenReturn(expectedPurchases);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/compras"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Client1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cpf").value("12345678901"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantidade").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].valorTotal").value(200.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value("Client2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].cpf").value("10987654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].quantidade").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].valorTotal").value(100.0));
    }

    @Test
    public void testGetMaiorCompraWhenCalledWithYearThenReturnMaiorCompra() throws Exception {
        int year = 2023;
        MaiorCompraResponse expectedMaiorCompra = new MaiorCompraResponse("Client1", "12345678901", null, 2, 200.0);
        Mockito.when(compraService.getMaiorCompra(year)).thenReturn(expectedMaiorCompra);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/maior-compra/{ano}", year))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Client1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("12345678901"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantidade").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotal").value(200.0));
    }

    @Test
    public void testGetClientesFieisWhenCalledThenReturnListOfClientesFieis() throws Exception {
        List<ClienteFielResponse> expectedClientesFieis = Arrays.asList(
                new ClienteFielResponse("Client1", "12345678901", 500.0),
                new ClienteFielResponse("Client2", "10987654321", 300.0)
        );
        Mockito.when(compraService.getClientesFieis()).thenReturn(expectedClientesFieis);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clientes-fieis"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value("Client1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cpf").value("12345678901"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalSpent").value(500.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nome").value("Client2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].cpf").value("10987654321"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].totalSpent").value(300.0));
    }

    @Test
    public void testGetRecomendacaoWhenCalledWithClienteAndTipoThenReturnRecomendacao() throws Exception {
        String cliente = "Client1";
        String tipo = "Tinto";
        RecomendacaoResponse expectedRecomendacao = new RecomendacaoResponse(Collections.emptyList());
        Mockito.when(compraService.getRecomendacao(cliente, tipo)).thenReturn(expectedRecomendacao);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recomendacao/{cliente}/{tipo}", cliente, tipo))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.recommendedProducts").isEmpty());
    }
}