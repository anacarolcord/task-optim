package com.projetotask.taskoptm.service;

import com.projetotask.taskoptm.dto.*;
import com.projetotask.taskoptm.models.*;
import com.projetotask.taskoptm.repository.TarefaRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TarefaService {
    private final TarefaRepository repository;

    @Autowired
    ModelMapper modelMapper;

    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }

    public TarefaResponseDTO toTarefaResponseDTO(Tarefa tarefa){
        TarefaResponseDTO dto = new TarefaResponseDTO();

        dto.setNomeTarefa(tarefa.getNomeTarefa());
        dto.setDuracao(tarefa.getDuracao());
        dto.setCategoria(tarefa.getCategoria());
        dto.setPrioridade(tarefa.getPrioridade());
        dto.setUsuario(tarefa.getUsuario());

        return dto;
    }

    public List<TarefaResponseDTO> listarTarefas() {
        List<TarefaResponseDTO> tarefasSemOrdem =  repository.findAll().stream().map(this::toTarefaResponseDTO).collect(Collectors.toList());

        List<TarefaRequestDTO> mudarTipo = tarefasSemOrdem.stream().map(tarefaResponseDTO -> modelMapper.map(tarefaResponseDTO, TarefaRequestDTO.class)).collect(Collectors.toList());

        otimizarLista(mudarTipo);

        return mudarTipo.stream().map(tarefaRequestDTO -> modelMapper.map(tarefaRequestDTO, TarefaResponseDTO.class)).collect(Collectors.toList());
    }

    public TarefaResponseDTO buscarId(Long id){
        return repository.findById(id).map(this::toTarefaResponseDTO).orElseThrow(()-> new RuntimeException("Tarefa não encontrada"));
    }

    public TarefaResponseDTO salvar(TarefaRequestDTO dados) {
        Tarefa tarefa = new Tarefa();

        tarefa.setNomeTarefa(dados.getNomeTarefa());
        tarefa.setDuracao(dados.getDuracao());
        tarefa.setCategoria(modelMapper.map(dados.getCategoria(), Categoria.class));
        tarefa.setPrioridade(dados.getPrioridade());


        repository.save(tarefa);




        return toTarefaResponseDTO(tarefa);
    }

    public TarefaResponseDTO atualizar(Long id, TarefaRequestDTO dados) {
        Tarefa tarefa = repository.findById(id).orElseThrow(()-> new RuntimeException("Tarefa não encontrada"));

        tarefa.setNomeTarefa(dados.getNomeTarefa());
        tarefa.setDuracao(dados.getDuracao());
        tarefa.setCategoria(modelMapper.map(dados.getCategoria(), Categoria.class));
        tarefa.setPrioridade(dados.getPrioridade());

        Tarefa atualizada = repository.save(tarefa);

        return toTarefaResponseDTO(atualizada);
    }

    public void  excluir(Long id){
        repository.deleteById(id);
    }


    public List<TarefaResponseDTO> otimizarLista(List<TarefaRequestDTO> tarefas) {

        Comparator<TarefaRequestDTO> porPrioridade = Comparator
                .comparing(
                        (TarefaRequestDTO t)-> t.getPrioridade().getNivel(),
                        Comparator.reverseOrder()//coloca em ordem decrescente
                );

        Comparator<TarefaRequestDTO> porDuracao = Comparator
                .comparing(
                        TarefaRequestDTO::getDuracao,
                        Comparator.reverseOrder()
                );

        Comparator<TarefaRequestDTO> otimizadorCompleto = porPrioridade
                .thenComparing(porDuracao);

        tarefas.sort(otimizadorCompleto);


        return tarefas.stream().map(tarefa -> modelMapper.map(tarefa, TarefaResponseDTO.class))
                .collect(Collectors.toList());
    }



}
