package com.coffidelivery.dto.produto;

import lombok.Data;

@Data
public class FileDTO {

    private Integer idFile;

    private String name;

    private String type;

    private byte[] data;
}
