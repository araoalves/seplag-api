package br.gov.mt.seplag.api.mappers;

import br.gov.mt.seplag.api.dto.ServidorEfetivoDTO;
import br.gov.mt.seplag.api.model.ServidorEfetivo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServidorEfetivoMapper {
    ServidorEfetivoMapper INSTANCE = Mappers.getMapper(ServidorEfetivoMapper.class);

    @Mapping(source = "pessoa.pesId", target = "id")
    @Mapping(source = "pessoa.pesNome", target = "nome")
    @Mapping(source = "seMatricula", target = "matricula")
    ServidorEfetivoDTO toDTO(ServidorEfetivo model);
}
