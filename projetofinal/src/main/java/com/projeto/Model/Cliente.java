package com.projeto.Model;

public class Cliente {
    // Atributos
    private String dataNascimento;
    private String username;
    private String senha;

    // Construtores
    public Cliente() {

    }

    public Cliente(String dataNascimento, String username, String senha) {
        this.dataNascimento = dataNascimento;
        this.username = username;
        this.senha = senha;
    }

    // Getters and Setters
    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
