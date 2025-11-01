package com.projetotask.taskoptm.service;

import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.projetotask.taskoptm.dto.*;
import com.projetotask.taskoptm.exceptions.UsuarioNaoEncontradoException;
import com.projetotask.taskoptm.models.*;
import com.projetotask.taskoptm.repository.TarefaRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.remote.JMXConnectionNotification;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TarefaService {
    private final TarefaRepository repository;
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    public TarefaService(TarefaRepository repository, UsuarioService usuarioService, ModelMapper modelMapper) {
        this.repository = repository;
        this.usuarioService=usuarioService;
        this.modelMapper=modelMapper;
    }

    public TarefaResponseDTO toTarefaResponseDTO(Tarefa tarefa){
        TarefaResponseDTO dto = new TarefaResponseDTO();

        dto.setNomeTarefa(tarefa.getNomeTarefa());
        dto.setDuracao(tarefa.getDuracao());
        dto.setPrioridade(tarefa.getPrioridade());

        return dto;
    }

    public List<TarefaResponseDTO> listarTarefas(Long id) {
        List<TarefaResponseDTO> tarefasSemOrdem =  repository.findAllByUsuarioIdUsuario(id).stream().map(this::toTarefaResponseDTO).collect(Collectors.toList());

        List<TarefaRequestDTO> mudarTipo = tarefasSemOrdem.stream().map(tarefaResponseDTO -> modelMapper.map(tarefaResponseDTO, TarefaRequestDTO.class)).collect(Collectors.toList());

        otimizarLista(mudarTipo);

        return mudarTipo.stream().map(tarefaRequestDTO -> modelMapper.map(tarefaRequestDTO, TarefaResponseDTO.class)).collect(Collectors.toList());
    }

    public TarefaResponseDTO buscarId(Long id){
        return repository.findById(id).map(this::toTarefaResponseDTO).orElseThrow(()-> new RuntimeException("Tarefa não encontrada"));
    }

    public TarefaResponseDTO salvar(Long idUsuario, TarefaRequestDTO dados) {

        Usuario usuarioExiste = usuarioService.buscarEntidadePorId(idUsuario);

        if(usuarioExiste == null){
            throw new  UsuarioNaoEncontradoException();
        }

        Tarefa tarefa = new Tarefa();

        tarefa.setUsuario(usuarioExiste);
        tarefa.setNomeTarefa(dados.getNomeTarefa());
        tarefa.setDuracao(dados.getDuracao());
        tarefa.setPrioridade(dados.getPrioridade());



        List<TarefaResponseDTO> tarefasUsuario = usuarioExiste.getTarefas()
                .stream().map(tarefa1 -> modelMapper.map(tarefa1, TarefaResponseDTO.class))
                .collect(Collectors.toList());

        TarefaResponseDTO tarefaalterada=  modelMapper.map(tarefa,TarefaResponseDTO.class);

        tarefasUsuario.add(tarefaalterada);

        repository.save(tarefa);

        List <TarefaRequestDTO>  tarefaRequestDTO = tarefasUsuario.stream()
                .map(tarefasUser->modelMapper.map(tarefasUser, TarefaRequestDTO.class))
                .collect(Collectors.toList());

        otimizarLista(tarefaRequestDTO);

        UsuarioRequestDTO trasnformaUsuarioEmRequest = modelMapper.map(usuarioExiste,UsuarioRequestDTO.class);

        usuarioService.atualizarUsuario(trasnformaUsuarioEmRequest,idUsuario);

        return toTarefaResponseDTO(tarefa);
    }

    public TarefaResponseDTO atualizar(Long id, TarefaRequestDTO dados) {
        Tarefa tarefa = repository.findById(id).orElseThrow(()-> new RuntimeException("Tarefa não encontrada"));

        tarefa.setNomeTarefa(dados.getNomeTarefa());
        tarefa.setDuracao(dados.getDuracao());
        tarefa.setPrioridade(dados.getPrioridade());

        Tarefa atualizada = repository.save(tarefa);

        return toTarefaResponseDTO(atualizada);
    }

    public void  excluir(Long id){
        repository.deleteById(id);
    }


    public void otimizarLista(List<TarefaRequestDTO> tarefas) {

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


        //tarefas.stream().map(tarefa -> modelMapper.map(tarefa, TarefaResponseDTO.class))
                //.collect(Collectors.toList());
    }



}
