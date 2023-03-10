package com.coffidelivery.controller.interfaces;

import com.coffidelivery.dto.produto.FileDTO;
import com.coffidelivery.dto.produto.ProdutoCreateDTO;
import com.coffidelivery.dto.produto.ProdutoDTO;
import com.coffidelivery.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

public interface ProdutoControllerInterface {

    @Operation(summary = "Salvar produto", description = "Salvar produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Salvar produto no banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping()
    ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoCreateDTO produtoCreateDTO);

    @Operation(summary = "Salvar imagem do produto", description = "Salvar imagem do produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Salvar imagem do produto por id do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/upload-imagem/")
    ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                       @RequestParam("idProduto") String id) throws RegraDeNegocioException;



    @Operation(summary = "Recuperar imagem do produto", description = "Recuperar imagem do produto por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Recuperar imagem do produto por id do banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("imagem/")
    ResponseEntity<String> recuperarImagem(@RequestParam("id") String id) throws RegraDeNegocioException;


    @Operation(summary = "Atualizar dados do produto", description = "Atualizar dados do produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Atualizar dados do produto no banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<ProdutoDTO> update(@RequestParam("id") Integer id,
                                             @RequestBody @Valid ProdutoCreateDTO produtoCreateDTO) throws RegraDeNegocioException;


    @Operation(summary = "Deletar dados do produto", description = "Deletar dados do produto")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Deletar dados do produto no banco"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@RequestParam("id") Integer id) throws RegraDeNegocioException;
}
