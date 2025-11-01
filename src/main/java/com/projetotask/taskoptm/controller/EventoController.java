package com.projetotask.taskoptm.controller;

import com.projetotask.taskoptm.dto.EventoRequestDTO;
import com.projetotask.taskoptm.dto.EventoResponseDTO;
import com.projetotask.taskoptm.models.Evento;
import com.projetotask.taskoptm.service.EventoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService service;

    public EventoController (EventoService service){
        this.service = service;
    }

    @GetMapping
    public List<EventoResponseDTO> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public EventoResponseDTO buscarPorId(@PathVariable Long id){
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public EventoResponseDTO atualizar(@PathVariable Long id, @RequestBody EventoRequestDTO dto){
        return service.atualizar(id,dto);
    }

    @PostMapping
    public EventoResponseDTO salvar(@RequestBody EventoRequestDTO dados){
        return service.salvar(dados);
    }
}
