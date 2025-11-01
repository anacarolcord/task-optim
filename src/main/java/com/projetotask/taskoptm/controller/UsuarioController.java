package com.projetotask.taskoptm.controller;

import com.projetotask.taskoptm.dto.UsuarioRequestDTO;
import com.projetotask.taskoptm.dto.UsuarioResponseDTO;
import com.projetotask.taskoptm.models.Usuario;
import com.projetotask.taskoptm.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios(){
       return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarUsuarioPorId(@PathVariable Long id){
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO editarUsuario(@RequestBody UsuarioRequestDTO dados, @PathVariable Long id){
        return usuarioService.atualizarUsuario(dados,id);
    }

    @PostMapping
    public UsuarioResponseDTO salvarUsuario(@RequestBody UsuarioRequestDTO dados) {
        return usuarioService.salvarUsuario(dados);
    }

    @DeleteMapping("/{id}")
    public void excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
    }


}
