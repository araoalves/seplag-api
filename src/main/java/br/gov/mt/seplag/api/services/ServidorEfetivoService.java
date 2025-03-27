package br.gov.mt.seplag.api.services;

import br.gov.mt.seplag.api.dto.ServidorEfetivoDTO;
import br.gov.mt.seplag.api.form.ServidorEfetivoForm;
import br.gov.mt.seplag.api.mappers.ServidorEfetivoMapper;
import br.gov.mt.seplag.api.model.*;
import br.gov.mt.seplag.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class ServidorEfetivoService {

    private final ServidorEfetivoRepository repository;
    private final PessoaRepository pessoaRepository;
    private final FotoPessoaRepository fotoPessoaRepository;
    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository enderecoRepository;
    private final LotacaoRepository lotacaoRepository;
    private final UnidadeRepository unidadeRepository;
    private final PessoaEnderecoRepository pessoaEnderecoRepository;
    private final MinioService minioService;
    private final ServidorEfetivoMapper mapper;

    public Page<ServidorEfetivoDTO> listar(Specification<ServidorEfetivo> spec, Pageable pageable) {
        return repository.findAll(spec, pageable).map(mapper::toDTO);
    }

    public ServidorEfetivoDTO salvar(ServidorEfetivoForm form) {
        Pessoa pessoa = new Pessoa();
        pessoa.setPesNome(form.getNome());
        pessoa.setPesDataNascimento(form.getPesDataNascimento());
        pessoa.setPesSexo(form.getPesSexo());
        pessoa.setPesMae(form.getPesMae());
        pessoa.setPesPai(form.getPesPai());
        pessoa = pessoaRepository.save(pessoa);

        Cidade cidade = cidadeRepository.findByCidNomeAndCidUf(form.getCidade(), form.getUf());

        if(cidade == null){
            cidade = new Cidade();
            cidade.setCidNome(form.getCidade());
            cidade.setCidUf(form.getUf());
            cidadeRepository.save(cidade);
        }

        Endereco endereco = new Endereco();
        endereco.setCidade(cidade);
        endereco.setEndBairro(form.getBairro());
        endereco.setEndNumero(form.getNumero());
        endereco.setEndTipoLogradouro(form.getTipoLogradouro());
        endereco.setEndLogradouro(form.getLogradouro());
        enderecoRepository.save(endereco);

        PessoaEndereco pessoaEndereco = new PessoaEndereco();
        pessoaEndereco.setPessoa(pessoa);
        pessoaEndereco.setEndereco(endereco);
        pessoaEnderecoRepository.save(pessoaEndereco);

        Unidade unidade = unidadeRepository.findById(form.getUnidadeId()).get();

        Lotacao lotacao = new Lotacao();
        lotacao.setPessoa(pessoa);
        lotacao.setUnidade(unidade);
        lotacao.setLotDataLotacao(LocalDate.now());
        lotacao.setLotPortaria("PORTARIA Nº54756");
        lotacaoRepository.save(lotacao);

        ServidorEfetivo servidor = new ServidorEfetivo();
        servidor.setPessoa(pessoa);
        servidor.setSeMatricula(form.getMatricula());
        servidor = repository.save(servidor);

        FotoPessoa fotoPessoa = new FotoPessoa();
        fotoPessoa.setPessoa(pessoa);
        fotoPessoa.setFpData(LocalDate.now());
        fotoPessoa.setFpBucket("fotos");
        fotoPessoa.setFpHash(minioService.uploadFoto(pessoa.getPesId(), form.getFoto()));
        fotoPessoaRepository.save(fotoPessoa);

        ServidorEfetivoDTO dto = mapper.toDTO(servidor);

        fotoPessoaRepository.findTopByPessoaOrderByFpDataDesc(pessoa)
                .ifPresent(f -> dto.setUrlFoto(minioService.getUrlTemporaria(fotoPessoa.getFpHash())));
        dto.setUnidadeLotacao(unidade.getUnidNome());
        return dto;
    }

}
