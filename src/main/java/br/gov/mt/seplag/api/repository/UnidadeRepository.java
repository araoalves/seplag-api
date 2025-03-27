package br.gov.mt.seplag.api.repository;

import br.gov.mt.seplag.api.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {}
