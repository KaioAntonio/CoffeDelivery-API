package com.coffidelivery.service;

import com.coffidelivery.dto.produto.FileDTO;
import com.coffidelivery.entity.FileEntity;
import com.coffidelivery.entity.ProdutoEntity;
import com.coffidelivery.exceptions.RegraDeNegocioException;
import com.coffidelivery.repository.FileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static java.lang.Integer.parseInt;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final ProdutoService produtoService;

    private final ObjectMapper objectMapper;

    public FileDTO store(MultipartFile file, String id) throws RegraDeNegocioException {
        try {
            ProdutoEntity produto = produtoService.findById(parseInt(id));
            FileEntity fileEntityExiste = fileRepository.findFileEntitiesByProduto(produto);
            FileEntity fileDB = new FileEntity();
            if (fileEntityExiste != null) {
                fileDB = fileEntityExiste;
            }
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            fileDB.setName(fileName);
            fileDB.setType(file.getContentType());
            fileDB.setData(file.getBytes());

            fileDB.setProduto(produto);
            FileEntity fileEntity = fileRepository.save(fileDB);
            FileDTO fileDTO = objectMapper.convertValue(fileEntity, FileDTO.class);
            return fileDTO;
        } catch (Exception e) {
            throw new RegraDeNegocioException("Ocorreu um erro ao enviar a imagem!");
        }
    }

    public String getImage(String id) throws RegraDeNegocioException {
        ProdutoEntity produto = produtoService.findById(parseInt(id));
        FileEntity fileEntity = fileRepository.findByProduto(produto).orElseThrow(() -> new RegraDeNegocioException("Imagem não encontrada ou não existe."));
        return Base64Utils.encodeToString(fileEntity.getData());

    }

}
