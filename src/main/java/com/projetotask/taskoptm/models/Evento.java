package com.projetotask.taskoptm.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idEvento;
    @Column(nullable = false)
    private String nomeEvento;
    private String descricaoEvento;

    @ManyToMany(mappedBy = "eventos")
    private List<Tarefa> tarefas;
}
