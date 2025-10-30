package com.projetotask.taskoptm.service;

import com.projetotask.taskoptm.dto.EventoRequestDTO;
import com.projetotask.taskoptm.dto.EventoResponseDTO;
import com.projetotask.taskoptm.dto.TarefaResponseDTO;
import com.projetotask.taskoptm.models.Evento;
import com.projetotask.taskoptm.repository.EventoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {
    private final EventoRepository repository;
    private final ModelMapper modelMapper;

    public EventoService (EventoRepository repository, ModelMapper modelMapper){
        this.repository = repository;
        this.modelMapper=modelMapper;
    }

    public EventoResponseDTO toResponseDTO(Evento evento){
        EventoResponseDTO dto = new EventoResponseDTO();

        dto.setIdEvento(evento.getIdEvento());
        dto.setNomeEvento(evento.getNomeEvento());
        dto.setDescricaoEvento(evento.getDescricaoEvento());

        List<TarefaResponseDTO> tarefas = evento.getTarefas().stream()
                        .map(tarefa -> modelMapper.map(tarefa, TarefaResponseDTO.class))
                                .collect(Collectors.toList());

        dto.setTarefas(tarefas);


        return dto;

    }

    public EventoResponseDTO salvar( EventoRequestDTO dados){
       Evento evento = new Evento();

       evento.setNomeEvento(dados.getNomeEvento());
       evento.setDescricaoEvento(dados.getDescricaoEvento());

       repository.save(evento);

       return toResponseDTO(evento);
    }

    public List<EventoResponseDTO> listar(){
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());

    }

    public EventoResponseDTO buscar(Long id){
        return repository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(()-> new RuntimeException("Evento nao encontrado"));
    }

    public EventoResponseDTO atualizar(Long id,EventoRequestDTO dados){
        Evento evento = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Evento nao encontrado"));

        evento.setNomeEvento(dados.getNomeEvento());
        evento.setDescricaoEvento(dados.getDescricaoEvento());

        Evento atualizado = repository.save(evento);

        return toResponseDTO(atualizado);

    }

    public void excluir(Long id){
        repository.deleteById(id);
    }
}
