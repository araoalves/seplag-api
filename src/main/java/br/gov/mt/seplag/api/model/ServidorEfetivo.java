package br.gov.mt.seplag.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "servidor_efetivo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorEfetivo {
    @Id
    private Long pesId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    private String seMatricula;
}
