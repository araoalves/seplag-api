package br.gov.mt.seplag.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "endereco")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long endId;

    private String endTipoLogradouro;
    private String endLogradouro;
    private Integer endNumero;
    private String endBairro;

    @ManyToOne
    @JoinColumn(name = "cid_id")
    private Cidade cidade;
}
