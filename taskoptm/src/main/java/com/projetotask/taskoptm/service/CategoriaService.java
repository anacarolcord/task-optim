package com.projetotask.taskoptm.service;

import com.projetotask.taskoptm.dto.CategoriaRequestDTO;
import com.projetotask.taskoptm.dto.CategoriaResponseDTO;
import com.projetotask.taskoptm.models.Categoria;
import com.projetotask.taskoptm.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService (CategoriaRepository repository){
        this.repository=repository;
    }

    public CategoriaResponseDTO toResponseDTO(Categoria categoria){
        CategoriaResponseDTO dto = new CategoriaResponseDTO();

        dto.setIdCategoria(categoria.getIdCategoria());
        dto.setNomeCategoria(categoria.getNomeCategoria());
        dto.setTarefas(categoria.getTarefas());

        return dto;
    }

    public CategoriaResponseDTO salvar (CategoriaRequestDTO dados){
        Categoria categoria = new Categoria();

        categoria.setNomeCategoria(dados.getNomeCategoria());

        repository.save(categoria);

        return toResponseDTO(categoria);
    }

    public CategoriaResponseDTO atualizar (Long id, CategoriaRequestDTO dados){
        Categoria categoria = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Categoria nao encontrada"));

        categoria.setNomeCategoria(dados.getNomeCategoria());

        Categoria atualizada = repository.save(categoria);

        return  toResponseDTO(atualizada);
    }

    public List<CategoriaResponseDTO> listar(){
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarId(Long id){
        Categoria categoria = repository.findById(id).orElseThrow(()-> new RuntimeException("erro"));

        return toResponseDTO(categoria);
    }

    public void deletar(Long id){
        repository.deleteById(id);
    }
}
