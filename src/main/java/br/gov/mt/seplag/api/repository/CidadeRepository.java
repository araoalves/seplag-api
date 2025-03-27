package br.gov.mt.seplag.api.repository;

import br.gov.mt.seplag.api.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    Cidade findByCidNomeAndCidUf(String nomeCidade, String uf);
}
