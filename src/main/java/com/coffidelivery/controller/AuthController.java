package com.coffidelivery.controller;

import com.coffidelivery.dto.login.LoginDTO;
import com.coffidelivery.dto.usuario.UsuarioCreateDTO;
import com.coffidelivery.dto.usuario.UsuarioDTO;
import com.coffidelivery.entity.UsuarioEntity;
import com.coffidelivery.exceptions.RegraDeNegocioException;
import com.coffidelivery.security.TokenService;
import com.coffidelivery.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Efetuar o login do usuário", description = "Efetua o login do usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Efetua o login do usuário do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
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

    @PostMapping("/criar")
    public ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO){
        return new ResponseEntity<>(usuarioService.create(usuarioCreateDTO), HttpStatus.OK);
    }

    @Operation(summary = "Buscar usuário logado", description = "Buscar usuário logado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Buscar usuário logado do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/logged")
    public ResponseEntity<UsuarioDTO> pegarUsuarioLogado() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUser(), HttpStatus.OK);
    }
}