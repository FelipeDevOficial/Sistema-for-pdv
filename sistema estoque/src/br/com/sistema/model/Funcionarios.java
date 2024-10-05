/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.model;

/**
 *
 * @author dacru
 */
public class Funcionarios extends Clientes{
    private String senha;
    private String cargo;
    private String cbNivel;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNivel_acesso() {
        return cbNivel;
    }

    public void setNivel_acesso(String nivel_acesso) {
        this.cbNivel = nivel_acesso;
    }

   
    
}
