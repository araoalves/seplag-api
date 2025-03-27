package br.gov.mt.seplag.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaEnderecoId implements Serializable {
    private Long pessoa;
    private Long endereco;
}
