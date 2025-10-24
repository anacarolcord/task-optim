package com.projetotask.taskoptm.controller;

import com.projetotask.taskoptm.dto.CategoriaRequestDTO;
import com.projetotask.taskoptm.dto.CategoriaResponseDTO;
import com.projetotask.taskoptm.service.CategoriaService;
import org.apache.catalina.LifecycleState;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service){
        this.service = service;
    }

    @GetMapping
    public List<CategoriaResponseDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public CategoriaResponseDTO buscarId(@PathVariable Long id){
        return service.buscarId(id);
    }

    @PostMapping
    public CategoriaResponseDTO salvar(@RequestBody CategoriaRequestDTO dados){
        return service.salvar(dados);
    }

    @PutMapping("/{id}")
    public CategoriaResponseDTO atualizar(@RequestBody CategoriaRequestDTO dados, @PathVariable Long id){
        return service.atualizar(id,dados);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
}
