package com.projeto.Controller;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.projeto.Model.*;
import com.projeto.Connection.*;;

public class ClientesControl {

    // Atributos
    private List<Cliente> clientes; // Lista de objetos Carros
    private DefaultTableModel tableModel; // Modelo da tabela Swing para exibição dos dados
    private JTable table; // Tabela Swing onde os dados são exibidos

    // Construtor
    public ClientesControl(List<Cliente> clientes, DefaultTableModel tableModel, JTable table) {
        this.clientes = clientes; // Inicializa a lista de carros
        this.tableModel = tableModel; // Inicializa o modelo da tabela
        this.table = table; // Inicializa a tabela Swing
    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        clientes = new ClientesDAO().listarTodos(); // Obtém os carros atualizados do banco de dados
        for (Cliente cliente : clientes) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { cliente.getNome(), cliente.getCpf(), cliente.getdataNascimento(),
                    cliente.getTelefone() });
        }
    }

    // Método para cadastrar um novo carro no banco de dados
    public void cadastrar(String cpf, String nome, String telefone, String dataNascimento) {
        new ClientesDAO().cadastrar(cpf, nome, telefone, dataNascimento);
        // Chama o método de cadastro no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
    }

    // Método para atualizar os dados de um carro no banco de dados
    public void atualizar(String cpf, String nome, String telefone, String dataNascimento) {
        new ClientesDAO().atualizar(cpf, nome, telefone, dataNascimento);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String cpf) {
        new ClientesDAO().apagar(cpf);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }

    public void limpar(String text, String text2, String text3, String text4) {
    }

    // Método para verificar se um CPF já existe no banco de dados
    private boolean verificarCPF(String cpf) {
        return new ClientesDAO().verificarCPFExistente(cpf);
    }

}