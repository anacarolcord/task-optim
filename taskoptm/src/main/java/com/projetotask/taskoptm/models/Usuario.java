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
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//anotar no caderno!!
    @Column(nullable = false)
    private Long idUsuario;
    @Column(nullable = false)
    private String nomeUsuario;

    //relacionamento 1 pra n com tarefas, mapeado com o atributo usuario na classe tarefa
    @OneToMany(mappedBy = "usuario")
    List<Tarefa> tarefas;
}
