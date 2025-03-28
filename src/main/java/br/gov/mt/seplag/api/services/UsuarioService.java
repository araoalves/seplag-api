package br.gov.mt.seplag.api.services;

import br.gov.mt.seplag.api.dto.UsuarioDTO;
import br.gov.mt.seplag.api.model.Usuario;
import br.gov.mt.seplag.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    public Usuario cadastrar(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setRole(usuarioDTO.getRole());

        return usuarioRepository.save(usuario);
    }
}
