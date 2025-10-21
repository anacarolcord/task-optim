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
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long idCategoria;
    private String nomeCategoria;

    //uma categoria pode ter n tarefas
    @OneToMany(mappedBy = "categoria")
    private List<Tarefa> tarefas;
}
