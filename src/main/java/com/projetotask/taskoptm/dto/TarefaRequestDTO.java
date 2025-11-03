package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Evento;
import com.projetotask.taskoptm.models.Prioridade;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
@Builder
public class TarefaRequestDTO {
    private String nomeTarefa;
    private Duration duracao;
    private Prioridade prioridade;
    private List<Evento> eventos;
}
