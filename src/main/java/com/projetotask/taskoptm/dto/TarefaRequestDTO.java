package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Prioridade;
import lombok.Data;

import java.time.Duration;

@Data
public class TarefaRequestDTO {
    private String nomeTarefa;
    private Duration duracao;
    private Prioridade prioridade;
}
