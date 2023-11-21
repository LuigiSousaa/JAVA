package app.View;

import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
import app.Connection.CarrosDAO;
import app.Model.Carros;

public class JanelaCarros extends JPanel {
    // Atributos
    private JButton cadastrar, apagar, editar, limpar, vendido;
    private JTextField carMarcaField, carModeloField, carAnoField, carPlacaField,
            carValorField;
    private List<Carros> carros;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;

    // Construtor
    public JanelaCarros() {
        super();
        // entrada de dados
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Cadastro Carros"));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Marca"));
        carMarcaField = new JTextField(20);
        inputPanel.add(carMarcaField);
        inputPanel.add(new JLabel("Modelo"));
        carModeloField = new JTextField(20);
        inputPanel.add(carModeloField);
        inputPanel.add(new JLabel("Ano"));
        carAnoField = new JTextField(20);
        inputPanel.add(carAnoField);
        inputPanel.add(new JLabel("Placa"));
        carPlacaField = new JTextField(20);
        inputPanel.add(carPlacaField);
        inputPanel.add(new JLabel("Valor"));
        carValorField = new JTextField(20);
        inputPanel.add(carValorField);
        add(inputPanel);
        JPanel botoes = new JPanel();
        botoes.add(cadastrar = new JButton("Cadastrar"));
        botoes.add(editar = new JButton("Editar"));
        botoes.add(apagar = new JButton("Apagar"));
        botoes.add(limpar = new JButton("Limpar"));
        botoes.add(vendido = new JButton("Vendido"));
        add(botoes);
        // tabela de carros
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Marca", "Modelo", "Ano", "Placa", "Valor" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        new CarrosDAO().criaTabela();

        atualizarTabela();

        // Tratamento
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    carMarcaField.setText((String) table.getValueAt(linhaSelecionada, 0));
                    carModeloField.setText((String) table.getValueAt(linhaSelecionada, 1));
                    carAnoField.setText((String) table.getValueAt(linhaSelecionada, 4));
                    carPlacaField.setText((String) table.getValueAt(linhaSelecionada, 3));
                    carValorField.setText((String) table.getValueAt(linhaSelecionada, 4));
                }

            }
        });

        CarrosControl operacoes = new CarrosControl(carros, tableModel, table);

        cadastrar.addActionListener(

                e -> {
                    String anoText = carAnoField.getText();
                    String valorText = carValorField.getText();

                    boolean camposObrigatoriosVazios = carMarcaField.getText().isEmpty() ||
                            carModeloField.getText().isEmpty() ||
                            carPlacaField.getText().isEmpty();

                    if ((!anoText.isEmpty() && !valorText.isEmpty())) {
                        try {
                            int anoInt = Integer.parseInt(anoText); // Converte o valor de "ano" para um inteiro
                            int valorInt = Integer.parseInt(valorText); // Converte o valor de "valor" para um inteiro

                            if (camposObrigatoriosVazios) {
                                JOptionPane.showMessageDialog(null,
                                        "Para efetuar o cadastro, preencha todos os campos.");
                                carMarcaField.setText("");
                                carModeloField.setText("");
                                carPlacaField.setText("");
                            } else {
                                // Apenas cadastra se os campos obrigatórios estão preenchidos
                                operacoes.cadastrar(carMarcaField.getText(), carModeloField.getText(),
                                        carAnoField.getText(), carPlacaField.getText(), carValorField.getText());
                            }
                        } catch (NumberFormatException exception) {
                            if (exception.getMessage().contains("For input string: \"" + anoText + "\"")) {
                                JOptionPane.showMessageDialog(this, "Por favor, digite um ano válido.", "Erro",
                                        JOptionPane.ERROR_MESSAGE);
                                carAnoField.setText("");
                            } else if (exception.getMessage().contains("For input string: \"" + valorText + "\"")) {
                                JOptionPane.showMessageDialog(this, "Por favor, digite um valor válido.", "Erro",
                                        JOptionPane.ERROR_MESSAGE);
                                carValorField.setText("");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Preencha pelo menos um campo (ano ou valor).", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

        apagar.addActionListener(
                e -> {
                    operacoes.apagar(carPlacaField.getText());

                    carMarcaField.setText("");
                    carModeloField.setText("");
                    carAnoField.setText("");
                    carPlacaField.setText("");
                    carValorField.setText("");

                });
        limpar.addActionListener(
                e -> {
                    operacoes.limpar(carMarcaField.getText(), carModeloField.getText(),
                            carAnoField.getText(), carPlacaField.getText(), carValorField.getText());

                    carMarcaField.setText("");
                    carModeloField.setText("");
                    carAnoField.setText("");
                    carPlacaField.setText("");
                    carValorField.setText("");
                }

        );
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        carros = new CarrosDAO().listarTodos();

        for (Carros carro : carros) {
            tableModel.addRow(new Object[] { carro.getMarca(), carro.getModelo(),
                    carro.getAno(), carro.getPlaca(), carro.getValor() });
        }

    }

}

// Métodos de Atualizar tabela
