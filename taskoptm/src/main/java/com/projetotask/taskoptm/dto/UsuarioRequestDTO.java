package com.projetotask.taskoptm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioRequestDTO {
    private Long idUsuario;
    private String nomeUsuario;
    private String senha;
}
