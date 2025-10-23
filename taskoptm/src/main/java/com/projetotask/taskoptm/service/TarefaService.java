package com.projetotask.taskoptm.service;

import com.projetotask.taskoptm.dto.TarefaRequestDTO;
import com.projetotask.taskoptm.dto.TarefaResponseDTO;
import com.projetotask.taskoptm.models.Tarefa;
import com.projetotask.taskoptm.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {
    private TarefaRepository repository;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public TarefaResponseDTO toResponseDTO(Tarefa tarefa){
        TarefaResponseDTO dto = new TarefaResponseDTO();

        dto.setNomeTarefa(tarefa.getNomeTarefa());
        dto.setDuracao(tarefa.getDuracao());
        dto.setCategoria(tarefa.getCategoria());
        dto.setPrioridade(tarefa.getPrioridade());
        dto.setUsuario(tarefa.getUsuario());

        return dto;
    }

    public List<TarefaResponseDTO> listarTarefas() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public TarefaResponseDTO buscarId(Long id){
        return repository.findById(id).map(this::toResponseDTO).orElseThrow(()-> new RuntimeException("Tarefa não encontrada"));
    }

    public TarefaResponseDTO salvar(TarefaRequestDTO dados) {
        Tarefa tarefa = new Tarefa();

        tarefa.setNomeTarefa(dados.getNomeTarefa());
        tarefa.setDuracao(dados.getDuracao());
        tarefa.setCategoria(dados.getCategoria());
        tarefa.setPrioridade(dados.getPrioridade());
        tarefa.setUsuario(dados.getUsuario());

        repository.save(tarefa);

        return toResponseDTO(tarefa);
    }

    public TarefaResponseDTO atualizar(Long id, TarefaRequestDTO dados) {
        Tarefa tarefa = repository.findById(id).orElseThrow(()-> new RuntimeException("Tarefa não encontrada"));

        tarefa.setNomeTarefa(dados.getNomeTarefa());
        tarefa.setDuracao(dados.getDuracao());
        tarefa.setCategoria(dados.getCategoria());
        tarefa.setPrioridade(dados.getPrioridade());
        tarefa.setUsuario(dados.getUsuario());

        Tarefa atualizada = repository.save(tarefa);

        return toResponseDTO(atualizada);
    }

    public void  excluir(Long id){
        repository.deleteById(id);
    }

}
