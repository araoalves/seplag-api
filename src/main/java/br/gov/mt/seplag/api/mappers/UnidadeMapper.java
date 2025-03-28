package br.gov.mt.seplag.api.mappers;

import br.gov.mt.seplag.api.dto.UnidadeDTO;
import br.gov.mt.seplag.api.model.Unidade;
import org.mapstruct.Mapper;

@Mapper
public interface UnidadeMapper {
    UnidadeDTO toDTO(Unidade unidade);
    Unidade toEntity(UnidadeDTO unidadeDTO);
}
