package com.projeto.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.projeto.Connection.ClientesDAO;
import com.projeto.Controller.ClientesControl;
import com.projeto.Model.Cliente;

public class CadastroCliente extends JFrame {
    // Atributos
    private JButton cadastrar, redirecionar, limpar;
    private JTextField clienteNomeField, clienteCpfField, clienteTelefoneField, clienteDataNascimentoField;
    private List<Cliente> clientes;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;

    // Construtor
    public CadastroCliente() {
        super();

        // Entrada de dados
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Nome"));
        clienteNomeField = new JTextField(20);
        inputPanel.add(clienteNomeField);
        inputPanel.add(new JLabel("CPF"));
        clienteCpfField = new JTextField(20);
        inputPanel.add(clienteCpfField);
        inputPanel.add(new JLabel("Data de nascimento"));
        clienteDataNascimentoField = new JTextField(20);
        inputPanel.add(clienteDataNascimentoField);
        inputPanel.add(new JLabel("Telefone"));
        clienteTelefoneField = new JTextField(20);
        inputPanel.add(clienteTelefoneField);

        JPanel botoes = new JPanel();
        cadastrar = new JButton("Cadastrar");
        redirecionar = new JButton("Redirecionar");
        limpar = new JButton("Limpar");
        botoes.add(cadastrar);
        botoes.add(redirecionar);
        botoes.add(limpar);

        // Tabela de clientes
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Nome", "CPF", "Idade", "Telefone" });
        table = new JTable(tableModel);
        JScrollPane jSPane = new JScrollPane(table);

        // Adicionando componentes ao JFrame
        setLayout(new BorderLayout(8, 8));
        add(inputPanel, BorderLayout.NORTH);
        add(botoes, BorderLayout.CENTER);
        add(jSPane, BorderLayout.SOUTH);

        new ClientesDAO().criaTabela();

        atualizarTabela();

        // Tratamento
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    clienteNomeField.setText((String) table.getValueAt(linhaSelecionada, 0));
                    clienteCpfField.setText((String) table.getValueAt(linhaSelecionada, 1));
                    clienteDataNascimentoField.setText((String) table.getValueAt(linhaSelecionada, 2));
                    clienteTelefoneField.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        ClientesControl operacoesClientes = new ClientesControl(clientes, tableModel, table);

        cadastrar.addActionListener(e -> {
            operacoesClientes.cadastrar(
                    clienteNomeField.getText(),
                    clienteCpfField.getText(),
                    clienteTelefoneField.getText(),
                    clienteDataNascimentoField.getText());
        });

        redirecionar.addActionListener(e -> {
            LoginFuncionario lf = new LoginFuncionario();
            lf.setVisible(true);
            // MinhaOutraClasse outraClasse = new MinhaOutraClasse();
            // outraClasse.setVisible(true);
        });
    }

    // Método para atualizar a tabela de clientes
    private void atualizarTabela() {
        tableModel.setRowCount(0);
        clientes = new ClientesDAO().listarTodos();

        for (Cliente cliente : clientes) {
            tableModel.addRow(new Object[] { cliente.getNome(), cliente.getCpf(),
                    cliente.getdataNascimento(), cliente.getTelefone() });
        }
    }
}
