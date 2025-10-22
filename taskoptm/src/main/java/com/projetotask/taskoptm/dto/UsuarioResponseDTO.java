package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Tarefa;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String nomeUsuario;
    private List<Tarefa> tarefas;
}
