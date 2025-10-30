package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Categoria;
import com.projetotask.taskoptm.models.Prioridade;
import com.projetotask.taskoptm.models.Usuario;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class TarefaRequestDTO {
    private String nomeTarefa;
    private Duration duracao;
    private Prioridade prioridade;
    private CategoriaRequestDTO categoria;
}
