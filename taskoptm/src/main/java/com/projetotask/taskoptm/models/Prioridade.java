package com.projetotask.taskoptm.models;

import jakarta.persistence.criteria.CriteriaBuilder;

public enum Prioridade {

    ALTA(3),
    MEDIA(2),
    BAIXA(1);

    private final Integer nivel;

    Prioridade(Integer nivel){
        this.nivel=nivel;
    }

    public int getNivel(){
        return nivel;
    }

}
