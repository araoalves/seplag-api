package br.gov.mt.seplag.api.repository;

import br.gov.mt.seplag.api.model.ServidorEfetivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServidorEfetivoRepository extends JpaRepository<ServidorEfetivo, Long>, JpaSpecificationExecutor<ServidorEfetivo> {
}
