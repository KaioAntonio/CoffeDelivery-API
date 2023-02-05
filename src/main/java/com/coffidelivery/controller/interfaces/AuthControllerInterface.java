package com.coffidelivery.controller.interfaces;

import com.coffidelivery.dto.login.LoginDTO;
import com.coffidelivery.dto.produto.FileDTO;
import com.coffidelivery.dto.usuario.UsuarioCreateDTO;
import com.coffidelivery.dto.usuario.UsuarioDTO;
import com.coffidelivery.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

public interface AuthControllerInterface {
    @Operation(summary = "Efetuar o login do usuário", description = "Efetua o login do usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Efetua o login do usuário do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    ResponseEntity<String> auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException;


    @Operation(summary = "Criar um usuário", description = "Criar um usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Criar um usuário no banco de dados"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/criar")
    ResponseEntity<UsuarioDTO> create(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO);

    @Operation(summary = "Buscar usuário logado", description = "Buscar usuário logado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Buscar usuário logado do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/logged")
    ResponseEntity<UsuarioDTO> pegarUsuarioLogado() throws RegraDeNegocioException;

}
