package com.coffidelivery.security;

import com.coffidelivery.entity.UsuarioEntity;
import com.coffidelivery.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioEntity> usuarioEntity = usuarioService.findByLogin(username);
        return usuarioEntity
                .orElseThrow(() -> new UsernameNotFoundException("Usuário inválido!"));
    }
}