package com.coffidelivery.dto.usuario;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UsuarioCreateDTO {

    @NotNull
    @NotBlank
    private String login;

    @NotBlank
    private String senha;

}
