package br.gov.mt.seplag.api.repository;

import br.gov.mt.seplag.api.model.ServidorEfetivo;
import br.gov.mt.seplag.api.model.Unidade;
import br.gov.mt.seplag.api.model.UnidadeEndereco;
import br.gov.mt.seplag.api.model.UnidadeEnderecoId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnidadeEnderecoRepository extends JpaRepository<UnidadeEndereco, UnidadeEnderecoId> {
    Optional<UnidadeEndereco> findByUnidade(Unidade unidade);
}
