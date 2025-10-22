package com.projetotask.taskoptm.repository;

import com.projetotask.taskoptm.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento,Long> {
}
