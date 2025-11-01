package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Prioridade;
import com.projetotask.taskoptm.models.Usuario;
import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
public class TarefaResponseDTO {
    private String nomeTarefa;
    private Duration duracao;
    private Prioridade prioridade;
}
