package com.projeto.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.projeto.Connection.*;
import com.projeto.Model.*;
import com.projeto.View.JanelaPrincipal;
import com.projeto.View.LoginFuncionario;

public class FuncionariosControl {

    // Atributos
    private List<Funcionario> funcionarios; // Lista de objetos Carros
    private DefaultTableModel tableModel; // Modelo da tabela Swing para exibição dos dados
    private JTable table; // Tabela Swing onde os dados são exibidos

    // Construtor
    public FuncionariosControl(List<Funcionario> funcionarios, DefaultTableModel tableModel, JTable table) {
        this.funcionarios = funcionarios; // Inicializa a lista de carros
        this.tableModel = tableModel; // Inicializa o modelo da tabela
        this.table = table; // Inicializa a tabela Swing

    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        funcionarios = new FuncionariosDAO().listarTodos(); // Obtém os carros atualizados do banco de dados
        for (Funcionario funcionarios : funcionarios) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { funcionarios.getNome(), funcionarios.getCpf(),
                    funcionarios.getdataNascimento(), funcionarios.getTelefone() });
        }
    }

    // Método para cadastrar um novo carro no banco de dados
    public void cadastrar(String cpf, String nome, String telefone, String idade) {
        new FuncionariosDAO().cadastrar(nome, cpf, telefone, idade);
        // Chama o método de cadastro no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
    }

    // Método para atualizar os dados de um carro no banco de dados
    public void atualizar(String cpf, String nome, String telefone, String idade) {
        new FuncionariosDAO().atualizar(cpf, nome, telefone, idade);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização
    }

    // Método para apagar um carro do banco de dados
    public void apagar(String cpf) {
        new FuncionariosDAO().apagar(cpf);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }

    public void formatData(String dataNascimento) {
        // Obtenha a data atual
        Calendar dataAtual = Calendar.getInstance();

        // Converta a string para um objeto Date
        Date dataNascimentoDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            dataNascimentoDate = sdf.parse(dataNascimento);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use o formato dd/MM/yyyy.");
            return; // Encerra o método se a conversão falhar
        }

        // Calcule a diferença em anos
        Calendar dataNascimentoCal = Calendar.getInstance();
        dataNascimentoCal.setTime(dataNascimentoDate);

        int diferencaAnos = dataAtual.get(Calendar.YEAR) - dataNascimentoCal.get(Calendar.YEAR);

        // Verifique se ainda não fez aniversário este ano
        if (dataAtual.get(Calendar.DAY_OF_YEAR) < dataNascimentoCal.get(Calendar.DAY_OF_YEAR)) {
            diferencaAnos--;
        }

        // Armazene o resultado na variável "idade"
        int idade = diferencaAnos;
    }

    // Método para verificar se o CPF existe
    public boolean verificarCPF(String cpf) {
        FuncionariosDAO funcionariosDAO = new FuncionariosDAO();
        if (funcionariosDAO.verificarCPFExistente(cpf)) {
            // Lógica para login bem-sucedido
            LoginFuncionario loginFuncionario = new LoginFuncionario();
            loginFuncionario.dispose();
            JOptionPane.showMessageDialog(null, "Seja bem-vindo, portador do CPF: " + cpf);
            return true;
        } else {
            // Lógica para login falhado
            JOptionPane.showMessageDialog(null, "CPF não encontrado. Por favor, informe um CPF válido.");
            return false;
        }
    }

}