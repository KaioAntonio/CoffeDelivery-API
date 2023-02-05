package com.coffidelivery.controller;

import com.coffidelivery.controller.interfaces.ProdutoControllerInterface;
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
public class ProdutoController implements ProdutoControllerInterface {

    private final ProdutoService produtoService;
    private final FileService fileService;

    @Override
    public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoCreateDTO produtoCreateDTO){
        return new ResponseEntity<>(produtoService.create(produtoCreateDTO), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam("idProduto") String id) throws RegraDeNegocioException {
        return new ResponseEntity<>(fileService.store(file, id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> recuperarImagem(@RequestParam("id") String id) throws RegraDeNegocioException {
        return new ResponseEntity<>(fileService.getImage(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> update(@RequestParam("id") Integer id,
            @RequestBody @Valid ProdutoCreateDTO produtoCreateDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(produtoService.update(id, produtoCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@RequestParam("id") Integer id) throws RegraDeNegocioException {
        produtoService.delete(id);
        return ResponseEntity.ok().build();
    }

}
