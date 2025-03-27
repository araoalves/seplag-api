package br.gov.mt.seplag.api.repository;

import br.gov.mt.seplag.api.model.FotoPessoa;
import br.gov.mt.seplag.api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FotoPessoaRepository extends JpaRepository<FotoPessoa, Long> {
    Optional<FotoPessoa> findTopByPessoaOrderByFpDataDesc(Pessoa pessoa);
}
