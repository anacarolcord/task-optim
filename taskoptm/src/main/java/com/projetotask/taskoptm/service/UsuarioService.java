package com.projetotask.taskoptm.service;

import com.projetotask.taskoptm.dto.TarefaRequestDTO;
import com.projetotask.taskoptm.dto.TarefaResponseDTO;
import com.projetotask.taskoptm.dto.UsuarioRequestDTO;
import com.projetotask.taskoptm.dto.UsuarioResponseDTO;
import com.projetotask.taskoptm.models.Tarefa;
import com.projetotask.taskoptm.models.Usuario;
import com.projetotask.taskoptm.repository.UsuarioRepository;
import jakarta.websocket.ClientEndpoint;
import org.modelmapper.ModelMapper;
import org.springframework.core.metrics.jfr.FlightRecorderApplicationStartup;
import org.springframework.stereotype.Service;

import javax.swing.text.TabExpander;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final TarefaService tarefaService;

    private final ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository repository, TarefaService tarefaService, ModelMapper modelMapper) {
        this.repository = repository;
        this.tarefaService = tarefaService;
        this.modelMapper=modelMapper;
    }


    public List<UsuarioResponseDTO> listar(){
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorId(Long id){
        Usuario usuario = repository.findById(id).orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));

        return toResponseDTO(usuario);

    }

    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO dto){
        Usuario usuario =  new Usuario();
        usuario.setNomeUsuario(dto.getNomeUsuario());
        usuario.setSenha(dto.getSenha());

        List<Tarefa> tarefasParaSalvar = dto.getTarefas()
                        .stream()
                        .map(tarefaRequestDTO ->modelMapper.map(tarefaRequestDTO, Tarefa.class))
                        .collect(Collectors.toList());

        usuario.setTarefas(tarefasParaSalvar);

        tarefaService.salvar(dto.getTarefas());

        Usuario salvo = repository.save(usuario);


        return toResponseDTO(salvo);
    }

    public UsuarioResponseDTO atualizarUsuario(UsuarioRequestDTO dto, Long id){
        Usuario usuario =  repository.findById(id).orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));

        usuario.setNomeUsuario(dto.getNomeUsuario());

        if(dto.getSenha() != null &&!dto.getSenha().isBlank()){
            usuario.setSenha(dto.getSenha());
        }

        Usuario atualizado = repository.save(usuario);

        return toResponseDTO(atualizado);
    }

    public void excluirUsuario(Long id){
        repository.deleteById(id);
    }

    //PRA PODER RETONAR DTO USANDO DADOS DO TIPO REPOSITORY

    private UsuarioResponseDTO toResponseDTO(Usuario usuario){
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNomeUsuario(usuario.getNomeUsuario());
        dto.setTarefas(usuario.getTarefas()
                .stream()
                .map(tarefaService::toTarefaResponseDTO)
                .collect(Collectors.toList()));

        return dto;
    }


    /*public boolean possuiMaisDeUmaTarefa(UsuarioRequestDTO dados){
        boolean sim;
        if (dados.getTarefas().size() >2 ){
            sim = true;
        }else{
            sim = false;
        }

    return sim;
    }*/
}
