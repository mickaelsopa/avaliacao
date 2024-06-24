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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompraService implements CompraUseCase {

    @Autowired
    private ProdutoFeign produtoFeign;

    @Autowired
    private ClienteFeign clienteFeign;

    @Override
    public List<CompraResponse> getPurchases() {
        List<Produto> produtos = produtoFeign.getProdutos();
        List<Cliente> clientes = clienteFeign.getClientes();
        Map<Integer, Produto> produtoMap = getProdutoMap(produtos);

        return clientes.stream()
                .flatMap(client -> client.getCompras().stream()
                        .map(compra -> getComprasResponse(client, compra, produtoMap))
                        .filter(Objects::nonNull))
                .sorted(Comparator.comparingDouble(CompraResponse::valorTotal))
                .collect(Collectors.toList());
    }

    @Override
    public MaiorCompraResponse getMaiorCompra(int ano) {
        List<Produto> produtos = produtoFeign.getProdutos();
        List<Cliente> clientes = clienteFeign.getClientes();
        Map<Integer, Produto> produtoMap = getProdutoMap(produtos);

        return clientes.stream()
                .flatMap(client -> client.getCompras().stream()
                        .map(compra -> createMaiorCompraResponse(client, compra, produtoMap, ano))
                        .filter(Objects::nonNull))
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    @Override
    public List<ClienteFielResponse> getClientesFieis() {
        List<Produto> produtos = produtoFeign.getProdutos();
        List<Cliente> clientes = clienteFeign.getClientes();
        Map<Integer, Produto> produtoMap = getProdutoMap(produtos);

        Map<Cliente, Double> clientSpending = clientes.stream()
                .collect(Collectors.toMap(
                        cliente -> cliente,
                        cliente -> calculateTotalSpent(cliente, produtoMap)
                ));

        return clientSpending.entrySet().stream()
                .sorted(Map.Entry.<Cliente, Double>comparingByValue().reversed())
                .limit(3)
                .map(entry -> new ClienteFielResponse(
                        entry.getKey().getNome(),
                        entry.getKey().getCpf(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public RecomendacaoResponse getRecomendacao(String cliente, String tipo) {
        List<Produto> produtos = produtoFeign.getProdutos();
        List<Cliente> clients = this.clienteFeign.getClientes();

        Cliente selectedClient = clients.stream()
                .filter(c -> c.getNome().toLowerCase().contains(cliente.toLowerCase()))
                .findFirst()
                .orElse(null);

        if (selectedClient == null) {
            return new RecomendacaoResponse(Collections.emptyList());
        }

        Map<Integer, Produto> produtoMap = getProdutoMap(produtos);

        Map<String, Long> wineTypes = selectedClient.getCompras().stream()
                .map(p -> produtoMap.get(Integer.parseInt(p.getCodigo())))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Produto::getTipoVinho, Collectors.counting()));

        String favoriteWineType = wineTypes.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(tipo.toLowerCase());

        List<Produto> recommendedProducts = produtos.stream()
                .filter(p -> p.getTipoVinho().equalsIgnoreCase(favoriteWineType))
                .collect(Collectors.toList());

        return new RecomendacaoResponse(recommendedProducts);
    }

    Map<Integer, Produto> getProdutoMap(List<Produto> produtos) {
        return produtos.stream()
                .collect(Collectors.toMap(Produto::getCodigo, p -> p));
    }

    CompraResponse getComprasResponse(Cliente cliente, Compra compra, Map<Integer, Produto> produtoMap) {
        Produto produto = produtoMap.get(Integer.parseInt(compra.getCodigo()));
        if (produto != null) {
            double valorTotal = produto.getPreco() * compra.getQuantidade();
            return new CompraResponse(
                    cliente.getNome(),
                    cliente.getCpf(),
                    produto,
                    compra.getQuantidade(),
                    valorTotal
            );
        }
        return null;
    }

    AbstractMap.SimpleEntry<Double, MaiorCompraResponse> createMaiorCompraResponse(Cliente cliente, Compra compra, Map<Integer,Produto> produtoMap, int ano) {
        Produto produto = produtoMap.get(Integer.parseInt(compra.getCodigo()));
        if (produto != null && produto.getAnoCompra() == ano) {
            double valorTotal = produto.getPreco() * compra.getQuantidade();
            return new AbstractMap.SimpleEntry<>(valorTotal, new MaiorCompraResponse(
                    cliente.getNome(),
                    cliente.getCpf(),
                    produto,
                    compra.getQuantidade(),
                    valorTotal
            ));
        }
        return null;
    }

    double calculateTotalSpent(Cliente cliente, Map<Integer, Produto> produtoMap) {
        return cliente.getCompras().stream()
                .mapToDouble(compra -> {
                    Produto produto = produtoMap.get(Integer.parseInt(compra.getCodigo()));
                    return produto != null ? produto.getPreco() * compra.getQuantidade() : 0;
                })
                .sum();
    }

}
