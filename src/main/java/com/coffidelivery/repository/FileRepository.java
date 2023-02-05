package com.coffidelivery.repository;

import com.coffidelivery.entity.FileEntity;
import com.coffidelivery.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> findByProduto(ProdutoEntity produto);

    FileEntity findFileEntitiesByProduto(ProdutoEntity produto);

}
