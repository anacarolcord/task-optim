package com.projetotask.taskoptm.repository;

import com.projetotask.taskoptm.models.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa,Long> {
}
