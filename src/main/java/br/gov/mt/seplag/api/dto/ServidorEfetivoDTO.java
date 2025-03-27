package br.gov.mt.seplag.api.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServidorEfetivoDTO implements Serializable {
    private Long id;
    private String nome;
    private String matricula;
    private String urlFoto;
    private Integer idade;
    private String unidadeLotacao;
}
