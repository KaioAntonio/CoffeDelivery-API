package com.coffidelivery.dto.produto;

import com.coffidelivery.entity.FileEntity;
import lombok.Data;

@Data
public class ProdutoCreateDTO {

    private String tipo;

    private String nome;

    private String descricao;

    private Double preco;

}
