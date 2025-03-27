package br.gov.mt.seplag.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnidadeEnderecoId implements Serializable {
    private Long unidade;
    private Long endereco;
}
