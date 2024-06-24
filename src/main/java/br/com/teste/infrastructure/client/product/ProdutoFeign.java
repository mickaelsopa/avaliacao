package br.com.teste.infrastructure.client.product;

import br.com.teste.domain.model.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "productClient", url = "${product.client.url}")
public interface ProdutoFeign {
    @GetMapping("/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json")
    List<Produto> getProdutos();
}