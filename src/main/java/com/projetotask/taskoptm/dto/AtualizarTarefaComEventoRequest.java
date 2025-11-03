package com.projetotask.taskoptm.dto;

import com.projetotask.taskoptm.models.Evento;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Getter
@Setter
public class AtualizarTarefaComEventoRequest {
    private List<Evento> eventos;
}
