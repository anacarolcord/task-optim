package com.projetotask.taskoptm.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException() {
        super("Usuario nao foi encontrado");
    }
}
