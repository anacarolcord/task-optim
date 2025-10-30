package com.projetotask.taskoptm.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idTarefa;
    @Column(nullable = false)
    private String nomeTarefa;
    private Duration duracao;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    //relacionamento n pra 1 muitas tarefas podem possuir 1 categoria
    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "tarefa_evento", joinColumns = @JoinColumn(name ="tarefaId" ),inverseJoinColumns = @JoinColumn(name = "idEvento") )
    private List<Evento> eventos;


}
