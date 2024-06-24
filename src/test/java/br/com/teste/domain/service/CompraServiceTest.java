package br.com.teste.domain.service;

import br.com.teste.application.dto.ClienteFielResponse;
import br.com.teste.application.dto.MaiorCompraResponse;
import br.com.teste.application.dto.CompraResponse;
import br.com.teste.application.dto.RecomendacaoResponse;
import br.com.teste.domain.model.Cliente;
import br.com.teste.domain.model.Compra;
import br.com.teste.domain.model.Produto;
import br.com.teste.infrastructure.client.client.ClienteFeign;
import br.com.teste.infrastructure.client.product.ProdutoFeign;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompraServiceTest {

    @Mock
    private ProdutoFeign produto;

    @Mock
    private ClienteFeign cliente;

    @InjectMocks
    private CompraService compraService;

    private List<Produto> products;
    private List<Cliente> clients;

    @BeforeEach
    public void setUp() {

        products = Arrays.asList(Produto.builder()
                .codigo(1)
                .tipoVinho("Tinto")
                .preco(100.0)
                .safra("2015")
                .anoCompra(2020).build(),
                Produto.builder()
                .codigo(2)
                .tipoVinho("Branco")
                .preco(200.0)
                .safra("2016")
                .anoCompra(2021).build());

        clients = Collections.singletonList(Cliente.builder()
                .nome("John Doe")
                .cpf("12345678901")
                .compras(Arrays.asList(Compra.builder()
                                .codigo("1").quantidade(2).build(),
                        Compra.builder()
                                .codigo("2").quantidade(1).build())).build());
    }

    @Test
    public void testGetPurchasesWhenCalledThenReturnSortedList() {
        when(produto.getProdutos()).thenReturn(products);
        when(cliente.getClientes()).thenReturn(clients);

        List<CompraResponse> purchaseResponses = compraService.getPurchases();

        assertThat(purchaseResponses).isSortedAccordingTo((p1, p2) -> Double.compare(p1.valorTotal(), p2.valorTotal()));
    }

    @Test
    public void testGetMaiorCompraWhenCalledWithSpecificYearThenReturnCorrectValorTotal() {
        when(produto.getProdutos()).thenReturn(products);
        when(cliente.getClientes()).thenReturn(clients);

        MaiorCompraResponse maiorCompraResponse = compraService.getMaiorCompra(2020);

        assertThat(maiorCompraResponse).isNotNull();
        assertThat(maiorCompraResponse.valorTotal()).isEqualTo(200.0);
    }

    @Test
    public void testGetClientesFieisWhenCalledThenReturnTop3Clients() {
        when(produto.getProdutos()).thenReturn(products);
        when(cliente.getClientes()).thenReturn(clients);

        List<ClienteFielResponse> clienteFielResponses = compraService.getClientesFieis();

        assertThat(clienteFielResponses).hasSize(1);
        assertThat(clienteFielResponses.get(0).nome()).isEqualTo("John Doe");
        assertThat(clienteFielResponses.get(0).cpf()).isEqualTo("12345678901");
        assertThat(clienteFielResponses.get(0).totalSpent()).isEqualTo(400.0);
    }

    @Test
    public void testGetRecomendacaoWhenCalledWithSpecificClientAndWineTypeThenReturnCorrectRecommendedProducts() {
        when(produto.getProdutos()).thenReturn(products);
        when(cliente.getClientes()).thenReturn(clients);

        RecomendacaoResponse recomendacaoResponse = compraService.getRecomendacao("John Doe", "Tinto");

        assertThat(recomendacaoResponse).isNotNull();
        assertThat(recomendacaoResponse.recommendedProducts()).hasSize(1);
        assertThat(recomendacaoResponse.recommendedProducts().get(0).getTipoVinho()).isEqualTo("Tinto");
    }
}