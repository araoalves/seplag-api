package br.gov.mt.seplag.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "foto_pessoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FotoPessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fpId;

    @ManyToOne
    @JoinColumn(name = "pes_id")
    private Pessoa pessoa;

    private LocalDate fpData;
    private String fpBucket;
    private String fpHash;
}
