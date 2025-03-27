package br.gov.mt.seplag.api.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class ServidorEfetivoForm {
    private String nome;
    private String matricula;
    private MultipartFile foto;
    private LocalDate pesDataNascimento;
    private String pesSexo;
    private String pesMae;
    private String pesPai;
}
