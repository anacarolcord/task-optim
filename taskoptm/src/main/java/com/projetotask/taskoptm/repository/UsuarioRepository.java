package com.projetotask.taskoptm.repository;

import com.projetotask.taskoptm.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
