package com.projetotask.taskoptm.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//anotar no caderno!!
    @Column(nullable = false)
    private Long idUsuario;
    @Column(nullable = false)
    private String nomeUsuario;
    @Column(nullable = false)
    private String senha;

    //relacionamento 1 pra n com tarefas, mapeado com o atributo usuario na classe tarefa
    @OneToMany(mappedBy = "usuario")
    List<Tarefa> tarefas;


    public Usuario(Long idUsuario, String nomeUsuario, String senha) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }
}
