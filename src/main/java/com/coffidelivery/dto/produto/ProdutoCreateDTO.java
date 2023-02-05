package com.coffidelivery.dto.produto;

import lombok.Data;

@Data
public class ProdutoCreateDTO {

    private String tipo;

    private String nome;

    private String descricao;

    private Double preco;

}
