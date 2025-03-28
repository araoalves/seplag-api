package br.gov.mt.seplag.api.services;

import br.gov.mt.seplag.api.dto.ServidorEfetivoDTO;
import br.gov.mt.seplag.api.dto.ServidorTemporarioDTO;
import br.gov.mt.seplag.api.form.ServidorTemporarioForm;
import br.gov.mt.seplag.api.mappers.ServidorTemporarioMapper;
import br.gov.mt.seplag.api.model.*;
import br.gov.mt.seplag.api.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServidorTemporarioService {

    private final ServidorTemporarioRepository repository;
    private final PessoaRepository pessoaRepository;
    private final FotoPessoaRepository fotoPessoaRepository;
    private final CidadeRepository cidadeRepository;
    private final EnderecoRepository enderecoRepository;
    private final LotacaoRepository lotacaoRepository;
    private final UnidadeRepository unidadeRepository;
    private final PessoaEnderecoRepository pessoaEnderecoRepository;
    private final MinioService minioService;
    private final ServidorTemporarioMapper mapper;

    public ServidorTemporarioDTO salvar(ServidorTemporarioForm form) {
        Pessoa pessoa = new Pessoa();
        pessoa.setPesNome(form.getNome());
        pessoa.setPesDataNascimento(form.getPesDataNascimento());
        pessoa.setPesSexo(form.getPesSexo());
        pessoa.setPesMae(form.getPesMae());
        pessoa.setPesPai(form.getPesPai());
        pessoa = pessoaRepository.save(pessoa);

        Cidade cidade = cidadeRepository.findByCidNomeAndCidUf(form.getCidade(), form.getUf());
        if (cidade == null) {
            cidade = new Cidade(null, form.getCidade(), form.getUf());
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

        Unidade unidade = unidadeRepository.findById(form.getUnidadeId()).orElseThrow();

        Lotacao lotacao = new Lotacao();
        lotacao.setPessoa(pessoa);
        lotacao.setUnidade(unidade);
        lotacao.setLotDataLotacao(LocalDate.now());
        lotacao.setLotPortaria("PORTARIA Nº TEMP");
        lotacaoRepository.save(lotacao);

        ServidorTemporario servidor = new ServidorTemporario();
        servidor.setPessoa(pessoa);
        servidor.setStDataAdmissao(form.getDataAdmissao());
        servidor.setStDataDemissao(form.getDataDemissao());
        servidor = repository.save(servidor);

        FotoPessoa foto = new FotoPessoa();
        foto.setPessoa(pessoa);
        foto.setFpData(LocalDate.now());
        foto.setFpBucket("fotos");
        foto.setFpHash(minioService.uploadFoto(pessoa.getPesId(), form.getFoto()));
        fotoPessoaRepository.save(foto);

        ServidorTemporarioDTO dto = mapper.toDTO(servidor);

        fotoPessoaRepository.findTopByPessoaOrderByFpDataDesc(pessoa)
                .ifPresent(f -> dto.setUrlFoto(minioService.getUrlTemporaria(foto.getFpHash())));
        dto.setUnidadeLotacao(unidade.getUnidNome());
        dto.setDataAdmissao(form.getDataAdmissao());
        return dto;
    }

    public Optional<ServidorTemporarioDTO> atualizar(Long id, ServidorTemporarioForm form) {
        return repository.findById(id).map(servidor -> {
            Pessoa pessoa = servidor.getPessoa();
            pessoa.setPesNome(form.getNome());
            pessoa.setPesDataNascimento(form.getPesDataNascimento());
            pessoa.setPesSexo(form.getPesSexo());
            pessoa.setPesMae(form.getPesMae());
            pessoa.setPesPai(form.getPesPai());
            pessoaRepository.save(pessoa);

            servidor.setStDataAdmissao(form.getDataAdmissao());
            servidor.setStDataDemissao(form.getDataDemissao());
            servidor = repository.save(servidor);

            if (form.getFoto() != null && !form.getFoto().isEmpty()) {
                FotoPessoa foto = new FotoPessoa();
                foto.setPessoa(pessoa);
                foto.setFpData(LocalDate.now());
                foto.setFpBucket("fotos");
                foto.setFpHash(minioService.uploadFoto(pessoa.getPesId(), form.getFoto()));
                fotoPessoaRepository.save(foto);
            }

            return montarDTO(servidor);
        });
    }

    public Page<ServidorTemporarioDTO> listar(Specification<ServidorTemporario> spec, Pageable pageable) {
        return repository.findAll(spec, pageable)
                .map(this::montarDTO);
    }

    public Optional<ServidorTemporarioDTO> buscar(Long id) {
        return repository.findById(id)
                .map(this::montarDTO);
    }

    public boolean deletar(Long id) {
        return repository.findById(id).map(s -> {
            repository.delete(s);
            return true;
        }).orElse(false);
    }

    private ServidorTemporarioDTO montarDTO(ServidorTemporario servidor) {
        ServidorTemporarioDTO dto = mapper.toDTO(servidor);

        fotoPessoaRepository.findTopByPessoaOrderByFpDataDesc(servidor.getPessoa())
                .ifPresent(f -> dto.setUrlFoto(minioService.getUrlTemporaria(f.getFpHash())));

        lotacaoRepository.findTopByPessoaOrderByLotDataLotacaoDesc(servidor.getPessoa())
                .ifPresent(l -> dto.setUnidadeLotacao(l.getUnidade().getUnidNome()));

        return dto;
    }
}
