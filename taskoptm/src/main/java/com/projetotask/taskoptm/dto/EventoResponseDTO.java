package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Tarefa;
import lombok.Data;

import java.util.List;

@Data
public class EventoResponseDTO {
    private Long idEvento;
    private String nomeEvento;
    private String descricaoEvento;
    private List<Tarefa> tarefas;
}
