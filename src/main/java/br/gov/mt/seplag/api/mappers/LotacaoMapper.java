package br.gov.mt.seplag.api.mappers;

import br.gov.mt.seplag.api.dto.LotacaoDTO;
import br.gov.mt.seplag.api.model.Lotacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LotacaoMapper {
    @Mapping(source = "pessoa.pesId", target = "pessoaId")
    @Mapping(source = "unidade.unidId", target = "unidadeId")
    LotacaoDTO toDTO(Lotacao entity);

    @Mapping(target = "pessoa", ignore = true)
    @Mapping(target = "unidade", ignore = true)
    Lotacao toEntity(LotacaoDTO dto);
}
