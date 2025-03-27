package br.gov.mt.seplag.api.repository;

import br.gov.mt.seplag.api.model.FotoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoPessoaRepository extends JpaRepository<FotoPessoa, Long> {}
