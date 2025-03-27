package br.gov.mt.seplag.api.services;

import br.gov.mt.seplag.api.dto.ServidorEfetivoDTO;
import br.gov.mt.seplag.api.form.ServidorEfetivoForm;
import br.gov.mt.seplag.api.mappers.ServidorEfetivoMapper;
import br.gov.mt.seplag.api.model.FotoPessoa;
import br.gov.mt.seplag.api.model.Pessoa;
import br.gov.mt.seplag.api.model.ServidorEfetivo;
import br.gov.mt.seplag.api.repository.FotoPessoaRepository;
import br.gov.mt.seplag.api.repository.PessoaRepository;
import br.gov.mt.seplag.api.repository.ServidorEfetivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ServidorEfetivoService {

    private final ServidorEfetivoRepository repository;
    private final PessoaRepository pessoaRepository;
    private final FotoPessoaRepository fotoPessoaRepository;
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

        return mapper.toDTO(servidor);
    }

}
