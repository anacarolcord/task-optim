package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Tarefa;
import lombok.Data;

import java.util.List;

@Data
public class CategoriaResponseDTO {
    private Long idCategoria;
    private String nomeCategoria;
    private List<Tarefa> tarefas;
}
