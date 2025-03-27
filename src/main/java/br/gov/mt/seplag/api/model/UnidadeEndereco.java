package br.gov.mt.seplag.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unidade_endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UnidadeEnderecoId.class)
public class UnidadeEndereco {
    @Id
    @ManyToOne
    @JoinColumn(name = "unid_id")
    private Unidade unidade;

    @Id
    @ManyToOne
    @JoinColumn(name = "end_id")
    private Endereco endereco;
}
