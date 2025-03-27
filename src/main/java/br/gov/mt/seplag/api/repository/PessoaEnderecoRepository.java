package br.gov.mt.seplag.api.repository;

import br.gov.mt.seplag.api.model.PessoaEndereco;
import br.gov.mt.seplag.api.model.PessoaEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaEnderecoRepository extends JpaRepository<PessoaEndereco, PessoaEnderecoId> {}
