package com.projetotask.taskoptm.repository;

import com.projetotask.taskoptm.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
}
