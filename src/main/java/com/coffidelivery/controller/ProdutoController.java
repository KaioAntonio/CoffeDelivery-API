package com.coffidelivery.controller;

import com.coffidelivery.dto.produto.FileDTO;
import com.coffidelivery.dto.produto.ProdutoCreateDTO;
import com.coffidelivery.dto.produto.ProdutoDTO;
import com.coffidelivery.exceptions.RegraDeNegocioException;
import com.coffidelivery.service.FileService;
import com.coffidelivery.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final FileService fileService;

    @Operation(summary = "Salvar produto", description = "Salvar produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Salvar produto no banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping()
    public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoCreateDTO produtoCreateDTO){
        return new ResponseEntity<>(produtoService.create(produtoCreateDTO), HttpStatus.OK);

    }

    @Operation(summary = "Salvar imagem do produto", description = "Salvar imagem do produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Salvar imagem do produto por id do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/upload-imagem/")
    public ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam("idProduto") String id) throws RegraDeNegocioException, IOException {
        return new ResponseEntity<>(fileService.store(file, id), HttpStatus.OK);
    }

    @GetMapping("imagem/")
    public ResponseEntity<String> recuperarImagem(@RequestParam("id") String id) throws RegraDeNegocioException {
        return new ResponseEntity<>(fileService.getImage(id), HttpStatus.OK);
    }

}
