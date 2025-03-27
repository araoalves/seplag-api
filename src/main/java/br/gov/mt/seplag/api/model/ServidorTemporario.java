package br.gov.mt.seplag.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "servidor_temporario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServidorTemporario {
    @Id
    private Long pesId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    private LocalDate stDataAdmissao;
    private LocalDate stDataDemissao;
}
