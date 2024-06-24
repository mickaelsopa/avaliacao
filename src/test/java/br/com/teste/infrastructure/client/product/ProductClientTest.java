package br.com.teste.infrastructure.client.product;

import br.com.teste.domain.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProductClientTest {

    @Mock
    private ProdutoFeign produtoFeign;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetProductsWhenProductsExistThenReturnListOfProducts() {
        List<Produto> mockProducts = List.of(Produto.builder()
                .codigo(1)
                .tipoVinho("Red")
                .preco(100.0)
                .safra("2018")
                .anoCompra(2020)
                .build(), Produto.builder()
                .codigo(2)
                .tipoVinho("White")
                .preco(150.0)
                .safra("2019")
                .anoCompra(2021)
                .build());
        Mockito.when(produtoFeign.getProdutos()).thenReturn(mockProducts);

        List<Produto> products = produtoFeign.getProdutos();

        assertNotNull(products, "The returned product list should not be null");
        assertEquals(2, products.size(), "The returned product list should have 2 products");
    }

    @Test
    void testGetProductsWhenNoProductsExistThenReturnEmptyList() {
        Mockito.when(produtoFeign.getProdutos()).thenReturn(Collections.emptyList());

        List<Produto> products = produtoFeign.getProdutos();

        assertNotNull(products, "The returned product list should not be null");
        assertEquals(0, products.size(), "The returned product list should be empty");
    }
}
