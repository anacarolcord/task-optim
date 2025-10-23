package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Categoria;
import com.projetotask.taskoptm.models.Evento;
import com.projetotask.taskoptm.models.Usuario;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TarefaResponseDTO {
    private String nomeTarefa;
    private LocalDateTime duracao;
    private String prioridade;
    private Categoria categoria;
    private Usuario usuario;
    private List<EventoResponseDTO> eventos;
}
