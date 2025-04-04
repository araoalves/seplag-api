package br.gov.mt.seplag.api.repository;

import br.gov.mt.seplag.api.model.ServidorTemporario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface ServidorTemporarioRepository extends JpaRepository<ServidorTemporario, Long>, JpaSpecificationExecutor<ServidorTemporario> {
}

