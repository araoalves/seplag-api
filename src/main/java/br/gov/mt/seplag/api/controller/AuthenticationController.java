package br.gov.mt.seplag.api.controller;

import br.gov.mt.seplag.api.dto.UsuarioDTO;
import br.gov.mt.seplag.api.model.Usuario;
import br.gov.mt.seplag.api.security.JwtUtil;
import br.gov.mt.seplag.api.services.UsuarioService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getSenha())
        );

        UserDetails user = userDetailsService.loadUserByUsername(request.getLogin());
        String token = jwtUtil.gerarToken(user.getUsername());
        return new TokenResponse(token);
    }

    @Data
    public static class AuthRequest {
        private String login;
        private String senha;
    }

    @Data
    public static class TokenResponse {
        private final String token;
    }

    @PostMapping("/cadastrar")
    public Usuario cadastrar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.cadastrar(usuarioDTO);
    }
}
