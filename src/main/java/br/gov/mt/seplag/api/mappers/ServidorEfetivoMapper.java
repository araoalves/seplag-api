package br.gov.mt.seplag.api.mappers;

import br.gov.mt.seplag.api.dto.ServidorEfetivoDTO;
import br.gov.mt.seplag.api.model.ServidorEfetivo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.Period;

@Mapper
public interface ServidorEfetivoMapper {
    ServidorEfetivoMapper INSTANCE = Mappers.getMapper(ServidorEfetivoMapper.class);

    @Mapping(source = "pessoa.pesId", target = "id")
    @Mapping(source = "pessoa.pesNome", target = "nome")
    @Mapping(source = "seMatricula", target = "matricula")
    ServidorEfetivoDTO toDTO(ServidorEfetivo model);

    @AfterMapping
    default void afterToDTO(ServidorEfetivo model, @MappingTarget ServidorEfetivoDTO dto) {
        if (model.getPessoa() != null && model.getPessoa().getPesDataNascimento() != null) {
            int idade = Period.between(model.getPessoa().getPesDataNascimento(), LocalDate.now()).getYears();
            dto.setIdade(idade);
        }
    }
}
