package com.projetotask.taskoptm.controller;

import com.projetotask.taskoptm.dto.AtualizarTarefaComEventoRequest;
import com.projetotask.taskoptm.dto.TarefaRequestDTO;
import com.projetotask.taskoptm.dto.TarefaResponseDTO;
import com.projetotask.taskoptm.service.TarefaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<TarefaResponseDTO> listarTarefas(@PathVariable Long idUsuario) {
         return service.listarTarefas(idUsuario);

    }

    @GetMapping("/{id}")
    public TarefaResponseDTO buscarTarefa(@PathVariable Long id) {

        return service.buscarId(id);
    }

    @PutMapping("/{idUsuario}/{idTarefa}")
    public TarefaResponseDTO atualizarEventos(@PathVariable Long idUsuario, @PathVariable Long idTarefa, @RequestBody AtualizarTarefaComEventoRequest dto) {
        return service.atualizarEvento(idUsuario,idTarefa,dto);
    }

    @PostMapping("/usuario/{idUsuario}")
    public TarefaResponseDTO salvar(@PathVariable Long idUsuario, @RequestBody TarefaRequestDTO dados){
        return service.salvar(idUsuario,dados);
    }

    @DeleteMapping("/{id}")
    public void deletarTarefa(@PathVariable  Long id){
        service.excluir(id);
    }

}
