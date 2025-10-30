package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Categoria;
import com.projetotask.taskoptm.models.Evento;
import com.projetotask.taskoptm.models.Prioridade;
import com.projetotask.taskoptm.models.Usuario;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TarefaResponseDTO {
    private Long idTarefa;
    private String nomeTarefa;
    private Duration duracao;
    private Prioridade prioridade;
    private Categoria categoria;
    private Usuario usuario;
    private List<EventoResponseDTO> eventos;
}
