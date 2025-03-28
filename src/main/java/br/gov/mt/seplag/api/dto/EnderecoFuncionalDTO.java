package br.gov.mt.seplag.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoFuncionalDTO {
    private String nomeServidor;
    private String unidade;
    private String tipoLogradouro;
    private String logradouro;
    private int numero;
    private String bairro;
    private String cidade;
    private String uf;
}

