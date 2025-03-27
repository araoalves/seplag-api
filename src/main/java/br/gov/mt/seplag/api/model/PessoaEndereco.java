package br.gov.mt.seplag.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pessoa_endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PessoaEnderecoId.class)
public class PessoaEndereco {
    @Id
    @ManyToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    @Id
    @ManyToOne
    @JoinColumn(name = "end_id")
    private Endereco endereco;
}
