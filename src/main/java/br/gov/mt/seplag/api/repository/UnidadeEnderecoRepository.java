package br.gov.mt.seplag.api.repository;

import br.gov.mt.seplag.api.model.UnidadeEndereco;
import br.gov.mt.seplag.api.model.UnidadeEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeEnderecoRepository extends JpaRepository<UnidadeEndereco, UnidadeEnderecoId> {}
