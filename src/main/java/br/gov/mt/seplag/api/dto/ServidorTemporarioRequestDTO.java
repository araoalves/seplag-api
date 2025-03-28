package br.gov.mt.seplag.api.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ServidorTemporarioRequestDTO {
    private String nome;
    private String matricula;
    private String fotoBase64;
    private LocalDate pesDataNascimento;
    private String pesSexo;
    private String pesMae;
    private String pesPai;
    private String cidade;
    private String uf;
    private String tipoLogradouro;
    private String logradouro;
    private int numero;
    private String bairro;
    private Long unidadeId;
    private LocalDate dataAdmissao;
    private LocalDate dataDemissao;
}

