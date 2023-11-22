package app.View;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.Controller.CarrosControl;
import app.Controller.ClientesControl;
import app.Connection.CarrosDAO;
import app.Connection.ClientesDAO;
import app.Model.Carros;
import app.Model.Clientes;

public class JanelaClientes extends JPanel {
    // Atributos
    private JButton cadastrar, apagar, editar, limpar;
    private JTextField clienteNomeField, clienteEmailField, clienteEnderecoField, clienteTelefoneField;
    private List<Clientes> clientes;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;

    // Construtor
    public JanelaClientes() {
        super();
        // entrada de dados
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Cadastro Clientes"));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Nome"));
        clienteNomeField = new JTextField(20);
        inputPanel.add(clienteNomeField);
        inputPanel.add(new JLabel("Email"));
        clienteEmailField = new JTextField(20);
        inputPanel.add(clienteEmailField);
        inputPanel.add(new JLabel("Endereço"));
        clienteEnderecoField = new JTextField(20);
        inputPanel.add(clienteEnderecoField);
        inputPanel.add(new JLabel("Telefone"));
        clienteTelefoneField = new JTextField(20);
        inputPanel.add(clienteTelefoneField);
        add(inputPanel);
        JPanel botoes = new JPanel();
        botoes.add(cadastrar = new JButton("Cadastrar"));
        botoes.add(editar = new JButton("Editar"));
        botoes.add(apagar = new JButton("Apagar"));
        botoes.add(limpar = new JButton("Limpar"));
        add(botoes);
        // tabela de clientes
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Nome", "Email", "Endereço", "Telefone" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        new ClientesDAO().criaTabela();

        atualizarTabela();

        // Tratamento
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    clienteNomeField.setText((String) table.getValueAt(linhaSelecionada, 0));
                    clienteEmailField.setText((String) table.getValueAt(linhaSelecionada, 1));
                    clienteEnderecoField.setText((String) table.getValueAt(linhaSelecionada, 2));
                    clienteTelefoneField.setText((String) table.getValueAt(linhaSelecionada, 3));
                }

            }
        });

        ClientesControl operacoesClientes = new ClientesControl(clientes, tableModel, table);

        cadastrar.addActionListener(
                e -> {
                    String nomeText = clienteNomeField.getText();
                    String emailText = clienteEmailField.getText();
                    String enderecoText = clienteEnderecoField.getText();
                    String telefoneText = clienteTelefoneField.getText();

                    String padraoEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

                    Pattern pattern = Pattern.compile(padraoEmail);

                    Matcher matcher = pattern.matcher(emailText);

                    boolean camposObrigatoriosVazios = nomeText.isEmpty() ||
                            emailText.isEmpty() ||
                            enderecoText.isEmpty() ||
                            telefoneText.isEmpty();

                    if ((!nomeText.isEmpty() && !emailText.isEmpty())) {
                        try {
                            if (camposObrigatoriosVazios) {
                                JOptionPane.showMessageDialog(null,
                                        "Para efetuar o cadastro, preencha todos os campos.");
                            } else if (!matcher.matches()) {
                                JOptionPane.showMessageDialog(null,
                                        "Por favor, digite um e-mail válido.", "Erro",
                                        JOptionPane.ERROR_MESSAGE);
                                clienteEmailField.setText("");
                            } else {
                                operacoesClientes.cadastrar(nomeText, emailText, enderecoText, telefoneText);

                                // Limpar os campos após o cadastro
                                clienteNomeField.setText("");
                                clienteEmailField.setText("");
                                clienteEnderecoField.setText("");
                                clienteTelefoneField.setText("");
                            }
                        } catch (NullPointerException exception) {
                            JOptionPane.showMessageDialog(this, "Para efetuar o cadastro, preencha todos os campos.",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

        editar.addActionListener(
                e -> {
                    String nomeText = clienteNomeField.getText();
                    String emailText = clienteEmailField.getText();
                    String enderecoText = clienteEnderecoField.getText();
                    String telefoneText = clienteTelefoneField.getText();

                    String padraoEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

                    Pattern pattern = Pattern.compile(padraoEmail);

                    Matcher matcher = pattern.matcher(emailText);

                    boolean camposObrigatoriosVazios = nomeText.isEmpty() ||
                            emailText.isEmpty() ||
                            enderecoText.isEmpty() ||
                            telefoneText.isEmpty();

                    if ((!nomeText.isEmpty() && !emailText.isEmpty())) {
                        try {
                            int telefoneInt = Integer.parseInt(telefoneText);

                            if (camposObrigatoriosVazios) {
                                JOptionPane.showMessageDialog(null,
                                        "Para efetuar a edição, preencha todos os campos.");
                            } else if (!matcher.matches()) {
                                JOptionPane.showMessageDialog(null,
                                        "Por favor, digite um e-mail válido.", "Erro",
                                        JOptionPane.ERROR_MESSAGE);
                                clienteEmailField.setText("");
                            }
                            else {
                                operacoesClientes.atualizar(nomeText, emailText, enderecoText, telefoneText);
                            }
                        } catch (NullPointerException exception) {
                            JOptionPane.showMessageDialog(this, "Para efetuar o cadastro, preencha todos os campos.",
                                    "Erro",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

        apagar.addActionListener(
                e -> {
                    boolean camposObrigatoriosVazios = clienteNomeField.getText().isEmpty() ||
                            clienteEmailField.getText().isEmpty() ||
                            clienteEnderecoField.getText().isEmpty() ||
                            clienteTelefoneField.getText().isEmpty();
                    if (camposObrigatoriosVazios) {
                        JOptionPane.showMessageDialog(null,
                                "Selecione um cliente para ser excluído.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        operacoesClientes.apagar(clienteEmailField.getText());

                        // Limpar os campos após a exclusão
                        clienteNomeField.setText("");
                        clienteEmailField.setText("");
                        clienteEnderecoField.setText("");
                        clienteTelefoneField.setText("");
                    }
                });

        limpar.addActionListener(
                e -> {
                    operacoesClientes.limpar(clienteNomeField.getText(), clienteEmailField.getText(),
                            clienteEnderecoField.getText(), clienteTelefoneField.getText());

                    // Limpar os campos
                    clienteNomeField.setText("");
                    clienteEmailField.setText("");
                    clienteEnderecoField.setText("");
                    clienteTelefoneField.setText("");
                });

    }

    // Método para atualizar a tabela de clientes
    private void atualizarTabela() {
        tableModel.setRowCount(0);
        clientes = new ClientesDAO().listarTodos();

        for (Clientes cliente : clientes) {
            tableModel.addRow(new Object[] { cliente.getNome(), cliente.getEmail(),
                    cliente.getEndereco(), cliente.getTelefone() });
        }

    }
}
