package com.coffidelivery.controller;

import com.coffidelivery.dto.usuario.UsuarioDTO;
import com.coffidelivery.exceptions.RegraDeNegocioException;
import com.coffidelivery.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Buscar usuário por id", description = "Buscar usuário por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Buscar usuário por id do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/findById/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable("id") Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.findById(id), HttpStatus.OK);
    }



}
