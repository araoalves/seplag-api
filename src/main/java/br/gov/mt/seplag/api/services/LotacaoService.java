package br.gov.mt.seplag.api.services;

import br.gov.mt.seplag.api.dto.LotacaoDTO;
import br.gov.mt.seplag.api.mappers.LotacaoMapper;
import br.gov.mt.seplag.api.model.Lotacao;
import br.gov.mt.seplag.api.repository.LotacaoRepository;
import br.gov.mt.seplag.api.repository.PessoaRepository;
import br.gov.mt.seplag.api.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LotacaoService {

    private final LotacaoRepository repository;
    private final PessoaRepository pessoaRepository;
    private final UnidadeRepository unidadeRepository;
    private final LotacaoMapper mapper;

    public LotacaoDTO salvar(LotacaoDTO dto) {
        Lotacao lotacao = mapper.toEntity(dto);
        lotacao.setPessoa(pessoaRepository.findById(dto.getPessoaId()).orElseThrow());
        lotacao.setUnidade(unidadeRepository.findById(dto.getUnidadeId()).orElseThrow());
        return mapper.toDTO(repository.save(lotacao));
    }

    public Optional<LotacaoDTO> atualizar(Long id, LotacaoDTO dto) {
        return repository.findById(id).map(l -> {
            l.setPessoa(pessoaRepository.findById(dto.getPessoaId()).orElseThrow());
            l.setUnidade(unidadeRepository.findById(dto.getUnidadeId()).orElseThrow());
            l.setLotDataLotacao(dto.getLotDataLotacao());
            l.setLotPortaria(dto.getLotPortaria());
            return mapper.toDTO(repository.save(l));
        });
    }

    public Page<LotacaoDTO> listar(Specification<Lotacao> spec, Pageable pageable) {
        return repository.findAll(spec, pageable).map(mapper::toDTO);
    }

    public Optional<LotacaoDTO> buscar(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public boolean deletar(Long id) {
        return repository.findById(id).map(l -> {
            repository.delete(l);
            return true;
        }).orElse(false);
    }
}
