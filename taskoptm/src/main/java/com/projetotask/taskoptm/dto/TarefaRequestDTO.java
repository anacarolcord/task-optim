package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Categoria;
import com.projetotask.taskoptm.models.Prioridade;
import com.projetotask.taskoptm.models.Usuario;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TarefaRequestDTO {
    private String nomeTarefa;
    private LocalDateTime duracao;
    private Prioridade prioridade;
    private Categoria categoria;
    private Usuario usuario;

}
