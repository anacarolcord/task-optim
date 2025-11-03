package com.projetotask.taskoptm.service;

import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.projetotask.taskoptm.dto.*;
import com.projetotask.taskoptm.exceptions.UsuarioNaoEncontradoException;
import com.projetotask.taskoptm.models.*;
import com.projetotask.taskoptm.repository.TarefaRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.internal.EntityCopyAllowedObserver;
import org.modelmapper.ModelMapper;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.remote.JMXConnectionNotification;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    public TarefaResponseDTO atualizar(Long idUsuario, Long idTarefa, TarefaRequestDTO dados) {
        Usuario usuarioExiste = usuarioService.buscarEntidadePorId(idUsuario);
        Tarefa tarefa = repository.findById(idTarefa).orElseThrow(()-> new RuntimeException("Tarefa não encontrada"));

        tarefa.setNomeTarefa(dados.getNomeTarefa());
        tarefa.setDuracao(dados.getDuracao());
        tarefa.setPrioridade(dados.getPrioridade());
        tarefa.setEventos(dados.getEventos());

        //PRECISA ADICIONAR A FUNCAO DE SALVAR ESSE NOVO EVENTO NO BANCO!!


        //se a tarefa do request tiver um evento igual a CANCELADO
        //ENTAO A TAREFA É EXCLUIDA DA LISTA DO USUARIO

        //variavel que guarda tarefa com evento CANCELADO
        boolean tarefaComEventoCancelado = tarefa.getEventos().stream().map(Evento::getNomeEvento)
                .anyMatch(s -> s.equalsIgnoreCase("CANCELADO"));

        //crio uma lista que tira a tarefa com evento CANCELADO da lista do usuario
        // e mantem somente as tarefas sem evento
       List<Tarefa> tarefasLimpas =  usuarioExiste.getTarefas().stream().dropWhile(tarefa1 -> tarefaComEventoCancelado).toList();

       //na lista de tarefas do usuario guardo essa nova lista limpa
       usuarioExiste.setTarefas(tarefasLimpas);


        //salvo a tarefa que foi atualizada
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
                        Comparator.reverseOrder()
                );

        Comparator<TarefaRequestDTO> porDuracao = Comparator
                .comparing(
                        TarefaRequestDTO::getDuracao,
                        Comparator.reverseOrder()
                );

        Comparator<TarefaRequestDTO> porEvento = Comparator
                .comparing(
                        (TarefaRequestDTO t)->t.getEventos().stream().anyMatch(evento -> evento.getNomeEvento().equalsIgnoreCase("CANCELADO")),
                        Comparator.reverseOrder()
                );


        List<TarefaRequestDTO> tarefaComEvento  = tarefas.stream().filter(tarefaRequestDTO -> tarefaRequestDTO.getEventos()
                .stream().anyMatch(evento -> evento.getNomeEvento().equalsIgnoreCase("CANCELADO"))).toList();

        Comparator<TarefaRequestDTO> otimizadorCompleto = porPrioridade
                .thenComparing(porDuracao);

        tarefas.sort(otimizadorCompleto);


        //tarefas.stream().map(tarefa -> modelMapper.map(tarefa, TarefaResponseDTO.class))
                //.collect(Collectors.toList());
    }

    public TarefaResponseDTO adicionarEvento(List <Evento> eventos, Long idTarefa){
        //pra adicionar um evento, a tarefa tem que existir!!
        //o evento é adicionado a uma unica tarefa e nao a uma lista.
        //se a tarefa tiver um evento "cancelado" ela é excluida DA LISTA do usuario.
        //... e a lista é reorganizada
        //por enquanto só vai ter cancelamento...mas vou manter como list para uma possivel
        //mudança posterior!

        Tarefa tarefaExiste = repository.findById(idTarefa)
                .orElseThrow(()-> new RuntimeException("Tarefa nao encontrada"));

        tarefaExiste.setEventos(eventos);

        TarefaResponseDTO tarefaComEvento = modelMapper.map(tarefaExiste, TarefaResponseDTO.class);

        return tarefaComEvento;

    }



}
