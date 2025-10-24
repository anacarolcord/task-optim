package com.projetotask.taskoptm.models;

public enum Prioridade {

    ALTA("Alta"),
    MEDIA("Média"),
    BAIXA("Baixa");

    private final String descricao;

    private Prioridade(String descricao){
        this.descricao=descricao;
    }

    public String getDescricao(){
        return descricao;
    }

}
