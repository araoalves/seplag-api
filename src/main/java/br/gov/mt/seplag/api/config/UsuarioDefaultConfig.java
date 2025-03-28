package br.gov.mt.seplag.api.config;

import br.gov.mt.seplag.api.model.Usuario;
import br.gov.mt.seplag.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class UsuarioDefaultConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner criarUsuarioAdmin(UsuarioRepository repository) {
        return args -> {
            if (repository.findByLogin("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setLogin("admin");
                admin.setSenha(passwordEncoder.encode("admin"));
                admin.setRole("ADMIN");

                repository.save(admin);
                System.out.println("✅ Usuário admin criado com sucesso.");
            } else {
                System.out.println("🔒 Usuário admin já existe. Nenhuma ação necessária.");
            }
        };
    }
}
