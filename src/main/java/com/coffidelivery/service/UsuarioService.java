package com.coffidelivery.service;

import com.coffidelivery.dto.usuario.UsuarioCreateDTO;
import com.coffidelivery.dto.usuario.UsuarioDTO;
import com.coffidelivery.entity.UsuarioEntity;
import com.coffidelivery.exceptions.RegraDeNegocioException;
import com.coffidelivery.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    private final PasswordEncoder passwordEncoder;

    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha){
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }
    public Optional<UsuarioEntity> findByLogin(String login){
        return usuarioRepository.findByLogin(login);
    }

    public UsuarioDTO findById(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(()-> new RegraDeNegocioException("Usuario não Encontrado!"));

        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO){
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        String encode = passwordEncoder.encode(usuarioEntity.getSenha());
        usuarioEntity.setSenha(encode);
        usuarioRepository.save(usuarioEntity);
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public Integer getIdLoggedUser(){
        return Integer.parseInt( (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    public UsuarioDTO getLoggedUser() throws RegraDeNegocioException {
        UsuarioEntity usuarioLogado = findByIdEntity(getIdLoggedUser());
        return objectMapper.convertValue(usuarioLogado,UsuarioDTO.class);
    }

    public UsuarioEntity findByIdEntity(Integer idUsuario) throws RegraDeNegocioException{
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Usuario nao encontrado"));
    }

}
