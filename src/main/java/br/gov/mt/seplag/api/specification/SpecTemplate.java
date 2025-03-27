package br.gov.mt.seplag.api.specification;

import br.gov.mt.seplag.api.model.ServidorEfetivo;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecTemplate {
    @And({
            @Spec(path = "seMatricula", spec = Like.class),
            @Spec(path = "pessoa.pesNome", spec = Like.class)
    })
    public interface ServidorEfetivoSpec extends Specification<ServidorEfetivo> {}
}
