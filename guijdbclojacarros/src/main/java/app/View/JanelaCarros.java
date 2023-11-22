package app.View;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.*;
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
                    carAnoField.setText((String) table.getValueAt(linhaSelecionada, 2));
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
                    String placaText = carPlacaField.getText();
                    String padraoPlaca = "^[A-Z]{3}\\d{4}$";

                    Pattern pattern = Pattern.compile(padraoPlaca);

                    Matcher matcher = pattern.matcher(placaText);

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
                            } else if (anoInt > 2023 || anoInt < 1980) {
                                JOptionPane.showMessageDialog(null,
                                        "Por favor, digite um ano válido.", "Erro",
                                        JOptionPane.ERROR_MESSAGE);
                                carAnoField.setText("");
                            } else if (!matcher.matches()) {
                                JOptionPane.showMessageDialog(null,
                                        "Por favor, digite uma placa válida."
                                                + "\n Exemplo: ABC1234",
                                        "Erro",
                                        JOptionPane.ERROR_MESSAGE);
                                carPlacaField.setText("");
                            } else if (valorInt < 7000) {
                                JOptionPane.showMessageDialog(null,
                                        "Por favor, digite um valor válido.", "Erro",
                                        JOptionPane.ERROR_MESSAGE);
                                carValorField.setText("");
                            } else {
                                // Apenas cadastra se os campos obrigatórios estão preenchidos
                                operacoes.cadastrar(carMarcaField.getText(), carModeloField.getText(),
                                        carAnoField.getText(), carPlacaField.getText(), carValorField.getText());
                                carMarcaField.setText("");
                                carModeloField.setText("");
                                carAnoField.setText("");
                                carPlacaField.setText("");
                                carValorField.setText("");
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
                        JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });

        editar.addActionListener(e -> {
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
                                "Para efetuar a edição, preencha todos os campos.");
                        carMarcaField.setText("");
                        carModeloField.setText("");
                        carPlacaField.setText("");
                    } else if (anoInt > 2023 || anoInt < 1960) {
                        JOptionPane.showMessageDialog(null,
                                "Por favor, digite um ano válido.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        carAnoField.setText("");
                    } else if (valorInt < 7000) {
                        JOptionPane.showMessageDialog(null,
                                "Por favor, digite um valor válido.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        carValorField.setText("");
                    } else {
                        // Apenas atualiza se os campos obrigatórios estão preenchidos
                        operacoes.atualizar(carMarcaField.getText(), carModeloField.getText(), carAnoField.getText(),
                                carPlacaField.getText(), carValorField.getText());
                    }
                } catch (NumberFormatException exception) {
                    if (exception.getMessage().contains("For input string: \"" + anoText + "\"")) {
                        JOptionPane.showMessageDialog(this, "Por favor, digite um ano válido.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        carAnoField.setText("");
                    } else if (exception.getMessage().contains("For input string: \"" + valorText + "\"")) {
                        JOptionPane.showMessageDialog(this, "Por favor, digite um valor válido2.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        carValorField.setText("");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        apagar.addActionListener(
                e -> {
                    boolean camposObrigatoriosVazios = carMarcaField.getText().isEmpty() ||
                            carModeloField.getText().isEmpty() ||
                            carPlacaField.getText().isEmpty();
                    if (camposObrigatoriosVazios) {
                        JOptionPane.showMessageDialog(null,
                                "Selecione um carro para ser excluído.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        operacoes.apagar(carPlacaField.getText());

                        carMarcaField.setText("");
                        carModeloField.setText("");
                        carAnoField.setText("");
                        carPlacaField.setText("");
                        carValorField.setText("");
                    }
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

    // Métodos de Atualizar tabela de carros
    private void atualizarTabela() {
        tableModel.setRowCount(0);
        carros = new CarrosDAO().listarTodos();

        for (Carros carro : carros) {
            tableModel.addRow(new Object[] { carro.getMarca(), carro.getModelo(),
                    carro.getAno(), carro.getPlaca(), carro.getValor() });
        }

    }

}
