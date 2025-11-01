package com.projetotask.taskoptm.repository;

import com.projetotask.taskoptm.models.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa,Long> {


    List<Tarefa> findAllByUsuarioIdUsuario(Long id);
}
