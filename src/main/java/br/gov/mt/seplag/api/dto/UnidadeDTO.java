package br.gov.mt.seplag.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnidadeDTO {
    private Long unidId;
    private String unidNome;
    private String unidSigla;
}
