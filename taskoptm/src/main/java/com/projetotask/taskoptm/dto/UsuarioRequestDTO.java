package com.projetotask.taskoptm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioRequestDTO {
    private String nomeUsuario;
    private List<TarefaRequestDTO> tarefas;
    private String senha;
}
