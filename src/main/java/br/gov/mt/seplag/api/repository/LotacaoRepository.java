package br.gov.mt.seplag.api.repository;

import br.gov.mt.seplag.api.model.Lotacao;
import br.gov.mt.seplag.api.model.Pessoa;
import br.gov.mt.seplag.api.model.ServidorEfetivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LotacaoRepository extends JpaRepository<Lotacao, Long> {
    Optional<Lotacao> findTopByPessoaOrderByLotDataLotacaoDesc(Pessoa pessoa);

    @Query("SELECT se FROM ServidorEfetivo se JOIN Lotacao l ON se.pessoa = l.pessoa WHERE l.unidade.unidId = :unidId")
    Page<ServidorEfetivo> findByUnidade(@Param("unidId") Long unidId, Pageable pageable);
}
