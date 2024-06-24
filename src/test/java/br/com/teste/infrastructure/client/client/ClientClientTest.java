package br.com.teste.infrastructure.client.client;

import br.com.teste.domain.model.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientClientTest {

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private FeignClientBuilder feignClientBuilder;

    @Mock
    private ClienteFeign clienteFeign;

    @Test
    public void testGetClientsWhenRequestIsSuccessfulThenReturnListOfClients() {
        List<Cliente> mockClients = Arrays.asList(Cliente.builder().build(), Cliente.builder().build());
        when(clienteFeign.getClientes()).thenReturn(mockClients);

        List<Cliente> clients = clienteFeign.getClientes();

        assertNotNull(clients);
        assertEquals(2, clients.size());
    }

    @Test
    public void testGetClientsWhenRequestFailsThenThrowException() {
        when(clienteFeign.getClientes()).thenThrow(new RuntimeException("Request failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteFeign.getClientes();
        });
        assertEquals("Request failed", exception.getMessage());
    }
}