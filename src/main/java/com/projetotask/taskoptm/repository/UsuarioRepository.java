package com.projetotask.taskoptm.repository;

import com.projetotask.taskoptm.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

}
