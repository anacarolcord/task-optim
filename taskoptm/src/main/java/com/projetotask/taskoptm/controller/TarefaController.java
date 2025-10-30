package com.projetotask.taskoptm.controller;

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

    @GetMapping
    public List<TarefaResponseDTO> listarTarefas() {
         return service.listarTarefas();

    }

    @GetMapping("/{id}")
    public TarefaResponseDTO buscarTarefa(@PathVariable Long id) {

        return service.buscarId(id);
    }

    @PutMapping("/{id}")
    public TarefaResponseDTO atualizar(@PathVariable Long id, @RequestBody TarefaRequestDTO dto) {
        return service.atualizar(id,dto);
    }

    @PostMapping
    public TarefaResponseDTO salvar(@RequestBody TarefaRequestDTO dados){
        return service.salvar(dados);
    }

    @DeleteMapping("/{id}")
    public void deletarTarefa(@PathVariable  Long id){
        service.excluir(id);
    }

}
