package br.com.teste.domain.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Produto {
    private int codigo;

    @JsonProperty("tipo_vinho")
    private String tipoVinho;

    private double preco;

    private String safra;

    @JsonProperty("ano_compra")
    private int anoCompra;

}