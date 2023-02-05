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

    public ProdutoDTO update(Integer id, ProdutoCreateDTO produtoCreateDTO) throws RegraDeNegocioException {
        ProdutoEntity produto = findById(id);
        produto.setDescricao(produtoCreateDTO.getDescricao());
        produto.setNome(produtoCreateDTO.getNome());
        produto.setTipo(produtoCreateDTO.getTipo());
        produto.setPreco(produtoCreateDTO.getPreco());

        produtoRepository.save(produto);
        return objectMapper.convertValue(produto, ProdutoDTO.class);
    }


    public void delete(Integer id) throws RegraDeNegocioException {
        ProdutoEntity produto = findById(id);
        produtoRepository.delete(produto);
    }

    public ProdutoEntity findById(Integer id) throws RegraDeNegocioException {
        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Produto não encontrado"));
    }


}
