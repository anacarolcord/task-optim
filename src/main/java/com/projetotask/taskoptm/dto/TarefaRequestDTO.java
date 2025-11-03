package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Evento;
import com.projetotask.taskoptm.models.Prioridade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarefaRequestDTO {
    private String nomeTarefa;
    private Duration duracao;
    private Prioridade prioridade;
    private List<Evento> eventos = new ArrayList<Evento>();
}
