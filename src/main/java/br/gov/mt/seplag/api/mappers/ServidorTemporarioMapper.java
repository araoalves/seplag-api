package br.gov.mt.seplag.api.mappers;

import br.gov.mt.seplag.api.dto.ServidorEfetivoDTO;
import br.gov.mt.seplag.api.dto.ServidorTemporarioDTO;
import br.gov.mt.seplag.api.model.ServidorTemporario;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.time.Period;

@Mapper
public interface ServidorTemporarioMapper {

    @Mapping(source = "pessoa.pesId", target = "id")
    @Mapping(source = "pessoa.pesNome", target = "nome")
    ServidorTemporarioDTO toDTO(ServidorTemporario servidor);

    @AfterMapping
    default void afterToDTO(ServidorTemporario model, @MappingTarget ServidorEfetivoDTO dto) {
        if (model.getPessoa() != null && model.getPessoa().getPesDataNascimento() != null) {
            int idade = Period.between(model.getPessoa().getPesDataNascimento(), LocalDate.now()).getYears();
            dto.setIdade(idade);
        }
    }
}
