package br.gov.mt.seplag.api.services;

import br.gov.mt.seplag.api.dto.UnidadeDTO;
import br.gov.mt.seplag.api.mappers.UnidadeMapper;
import br.gov.mt.seplag.api.model.Unidade;
import br.gov.mt.seplag.api.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository repository;
    private final UnidadeMapper mapper;

    public UnidadeDTO salvar(UnidadeDTO unidadeDTO) {
        Unidade unidade = mapper.toEntity(unidadeDTO);
        return mapper.toDTO(repository.save(unidade));
    }

    public Optional<UnidadeDTO> atualizar(Long id, UnidadeDTO unidadeDTO) {
        return repository.findById(id).map(unidade -> {
            unidade.setUnidNome(unidadeDTO.getUnidNome());
            return mapper.toDTO(repository.save(unidade));
        });
    }

    public Page<UnidadeDTO> listar(Specification<Unidade> spec, Pageable pageable) {
        return repository.findAll(spec, pageable).map(mapper::toDTO);
    }

    public Optional<UnidadeDTO> buscar(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public boolean deletar(Long id) {
        return repository.findById(id).map(u -> {
            repository.delete(u);
            return true;
        }).orElse(false);
    }
}
