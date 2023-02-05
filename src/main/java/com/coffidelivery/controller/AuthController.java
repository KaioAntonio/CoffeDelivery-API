package com.coffidelivery.controller;

import com.coffidelivery.controller.interfaces.AuthControllerInterface;
import com.coffidelivery.dto.login.LoginDTO;
import com.coffidelivery.dto.usuario.UsuarioCreateDTO;
import com.coffidelivery.dto.usuario.UsuarioDTO;
import com.coffidelivery.entity.UsuarioEntity;
import com.coffidelivery.exceptions.RegraDeNegocioException;
import com.coffidelivery.security.TokenService;
import com.coffidelivery.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController implements AuthControllerInterface {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        try {
            UsernamePasswordAuthenticationToken userAuthDTO = new UsernamePasswordAuthenticationToken(loginDTO.getLogin(),
                    loginDTO.getSenha());
            Authentication authentication = authenticationManager.authenticate(userAuthDTO);
            Object principal = authentication.getPrincipal();
            UsuarioEntity usuarioEntity = (UsuarioEntity) principal;
            return new ResponseEntity<>(tokenService.getToken(usuarioEntity), HttpStatus.OK);
        }
        catch (Exception e){
            throw new RegraDeNegocioException("Erro ao realizar o login!");
        }

    }

    @Override
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO){
        return new ResponseEntity<>(usuarioService.create(usuarioCreateDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UsuarioDTO> pegarUsuarioLogado() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(), HttpStatus.OK);
    }
}