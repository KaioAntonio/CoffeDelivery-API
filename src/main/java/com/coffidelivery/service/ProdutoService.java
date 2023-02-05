package com.coffidelivery.service;

import com.coffidelivery.dto.produto.ProdutoCreateDTO;
import com.coffidelivery.dto.produto.ProdutoDTO;
import com.coffidelivery.entity.ProdutoEntity;
import com.coffidelivery.exceptions.RegraDeNegocioException;
import com.coffidelivery.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;

    public ProdutoDTO create(ProdutoCreateDTO produtoCreateDTO){
        ProdutoEntity produto = objectMapper.convertValue(produtoCreateDTO, ProdutoEntity.class);
        produtoRepository.save(produto);
        return objectMapper.convertValue(produto, ProdutoDTO.class);

    }

    public ProdutoEntity findById(Integer id) throws RegraDeNegocioException {
        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Produto não encontrado"));
    }


}
