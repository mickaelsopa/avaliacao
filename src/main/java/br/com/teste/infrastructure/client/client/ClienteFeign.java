package br.com.teste.infrastructure.client.client;


import br.com.teste.domain.model.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "clientClient", url = "${client.client.url}")
public interface ClienteFeign {
    @GetMapping("/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json")
    List<Cliente> getClientes();
}