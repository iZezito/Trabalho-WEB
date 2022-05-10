package com.example.trabalhoweb.classes;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
public class Cadastro {
    @Column(name = "nome")
    private String nome;
    @Column(name = "email")
    String email;
    @Column(name = "senha")
    String senha;
    @Column(name = "funcao")
    private boolean funcao;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Cadastro(String nome, String email, String senha, boolean funcao) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.funcao = funcao;
    }

    public Cadastro() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isFuncao() {
        return funcao;
    }

    public void setFuncao(boolean funcao) {
        this.funcao = funcao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

