/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.suelliton.projeto.model;

/**
 *
 * @author suelliton
 */
public class Mensagem {
    private Long id;
    private String conteudo;

    public Mensagem() {
    }

    public Mensagem(Long id, String conteudo) {
        this.id = id;
        this.conteudo = conteudo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    
}
