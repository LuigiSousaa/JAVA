package app.View;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.Connection.CarrosDAO;
import app.View.JanelaCarros;
import app.Connection.ClientesDAO;
import app.Connection.VendasDAO;
import app.Controller.VendasControl;
import app.Model.Carros;
import app.Model.Clientes;
import app.Model.Vendas;

public class VendasView extends JPanel {
    // Atributos
    private JButton vender, apagar, limpar;
    private JTextField vendaMarcaField, vendaModeloField, vendaValorField, vendaPlacaField, vendaClienteField,
            vendaDataHoraField;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;
    private List<Carros> carros;
    private List<Clientes> clientes;
    private List<Vendas> vendas;
    JComboBox<String> carrosComboBox;
    JComboBox<String> clientesComboBox;

    public VendasView() {
        super();
        // entrada de dados
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Cadastro Carros"));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        inputPanel.add(new JLabel("Marca:"));
        vendaMarcaField = new JTextField(20);
        inputPanel.add(vendaMarcaField);

        inputPanel.add(new JLabel("Modelo:"));
        vendaModeloField = new JTextField(20);
        inputPanel.add(vendaModeloField);

        inputPanel.add(new JLabel("Valor:"));
        vendaValorField = new JTextField(20);
        inputPanel.add(vendaValorField);

        inputPanel.add(new JLabel("Placa:"));
        vendaPlacaField = new JTextField(20);
        inputPanel.add(vendaPlacaField);

        inputPanel.add(new JLabel("Cliente:"));
        vendaClienteField = new JTextField(20);
        inputPanel.add(vendaClienteField);

        inputPanel.add(new JLabel("Data e Horário:"));
        vendaDataHoraField = new JTextField(20);
        inputPanel.add(vendaDataHoraField);

        carrosComboBox = new JComboBox<>();
        clientesComboBox = new JComboBox<>();

        // Preencha o JComboBox com os carros
        carros = new CarrosDAO().listarTodos();
        carrosComboBox.addItem("Selecione o Carro");
        for (Carros carro : carros) {
            carrosComboBox.addItem(
                    carro.getMarca() + " - " + carro.getModelo() + " - " + carro.getValor() + " - " + carro.getPlaca());
        }
        add(carrosComboBox);

        // Preencha o JComboBox com os carros
        clientes = new ClientesDAO().listarTodos();
        clientesComboBox.addItem("Selecione o Cliente");
        for (Clientes cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome());
        }
        add(clientesComboBox);
        add(inputPanel);

        JPanel botoes = new JPanel();
        botoes.add(vender = new JButton("Vender"));
        botoes.add(apagar = new JButton("Salvar Exclusão"));
        botoes.add(limpar = new JButton("Limpar"));
        add(botoes);

        // tabela de carros
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Marca", "Modelo", "Valor", "Placa", "Cliente", "Data e Hora" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        // Criar a tabela se ela não existir
        new VendasDAO().criaTabela();
        // Atualizar a Tabela na Abertura da Janela
        atualizarTabela();

        // Tratamento de Eventos (Construtor)
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    vendaMarcaField.setText((String) table.getValueAt(linhaSelecionada, 0));
                    vendaModeloField.setText((String) table.getValueAt(linhaSelecionada, 1));
                    vendaValorField.setText((String) table.getValueAt(linhaSelecionada, 2));
                    vendaPlacaField.setText((String) table.getValueAt(linhaSelecionada, 3));
                    vendaClienteField.setText((String) table.getValueAt(linhaSelecionada, 4));
                    vendaDataHoraField.setText((String) table.getValueAt(linhaSelecionada, 5));
                }
            }
        });

        VendasControl operacoes = new VendasControl(vendas, tableModel, table);
        // Configura o metodo "cadastrar" do objeto operacoes com valores dos campos de
        // entrada

        carrosComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verifica se não é a primeira opção ("Selecione o Carro")
                if (carrosComboBox.getSelectedIndex() != 0) {
                    // Obtém o índice do carro selecionado
                    int selectedIndex = carrosComboBox.getSelectedIndex() - 1; // Desconta o índice do título

                    // Obtém o carro selecionado com base no índice
                    Carros carroSelecionado = carros.get(selectedIndex);

                    // Define os valores nos campos de texto com as informações do carro selecionado
                    vendaMarcaField.setText(carroSelecionado.getMarca());
                    vendaModeloField.setText(carroSelecionado.getModelo());
                    vendaValorField.setText(String.valueOf(carroSelecionado.getValor())); // Se for numérico
                    vendaPlacaField.setText(carroSelecionado.getPlaca());

                    // Aqui você define os outros campos de acordo com as informações do carro
                    // selecionado
                    // vendaClienteField.setText(...);
                    // vendaDataHoraField.setText(...);
                } else {
                    vendaMarcaField.setText("");
                    vendaModeloField.setText("");
                    vendaValorField.setText("");
                    vendaPlacaField.setText("");
                }
            }
        });

        clientesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verifica se não é a primeira opção ("Selecione o Carro")
                if (clientesComboBox.getSelectedIndex() != 0) {
                    // Obtém o índice do carro selecionado
                    int selectedIndex = clientesComboBox.getSelectedIndex() - 1; // Desconta o índice do título

                    // Obtém o carro selecionado com base no índice
                    Clientes clienteSelecionado = clientes.get(selectedIndex);

                    // Define os valores nos campos de texto com as informações do carro selecionado
                    vendaClienteField.setText(clienteSelecionado.getNome());

                } else {
                    vendaClienteField.setText("");
                }
            }
        });

        vender.addActionListener(e -> {

            boolean camposVendaVazio = vendaMarcaField.getText().isEmpty() || vendaModeloField.getText().isEmpty()
                    || vendaValorField.getText().isEmpty() || vendaPlacaField.getText().isEmpty()
                    || vendaClienteField.getText().isEmpty() || vendaDataHoraField.getText().isEmpty();

            try {
                // Validação dos campos preenchidos
                if (!camposVendaVazio) {
                    // Obtém o carro selecionado no combo box
                    int selectedIndex1 = carrosComboBox.getSelectedIndex();
                    int selectedIndex2 = clientesComboBox.getSelectedIndex();
                    if (selectedIndex1 > 0 && selectedIndex2 > 0) {
                        Carros carroSelecionado = carros.get(selectedIndex1 - 1);
                        Clientes clienteSelecionado = clientes.get(selectedIndex2 - 1);

                        // Comparação dos campos com as informações do carro e cliente selecionado
                        if (!vendaMarcaField.getText().equals(carroSelecionado.getMarca())
                                || !vendaModeloField.getText().equals(carroSelecionado.getModelo())
                                || !vendaPlacaField.getText().equals(carroSelecionado.getPlaca())
                                || !vendaClienteField.getText().equals(clienteSelecionado.getNome())
                                || vendaDataHoraField.getText().isEmpty()) {

                            // Campos diferentes do carro ou cliente selecionado, exibe uma mensagem de erro
                            JOptionPane.showMessageDialog(null, "Preencha os Campos Corretamente!",
                                    "Informação Inválida",
                                    2);
                        } else {
                            operacoes.apagarCarro(vendaPlacaField.getText());
                            operacoes.vender(vendaMarcaField.getText(), vendaModeloField.getText(),
                                    vendaValorField.getText(),
                                    vendaPlacaField.getText(), vendaClienteField.getText(),
                                    vendaDataHoraField.getText());
                            // Limpa os campos de entrada após a operação de venda
                            vendaMarcaField.setText("");
                            vendaModeloField.setText("");
                            vendaValorField.setText("");
                            vendaPlacaField.setText("");
                            vendaClienteField.setText("");
                            vendaDataHoraField.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Preencha os Campos Corretamente!", "Informação Inválida",
                                2);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Não é Possível Vender Dados Vazios!", "Informação Inválida",
                            2);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Corretamente!", "Informação Inválida",
                        2);
            }
        });

        limpar.addActionListener(e -> {
            // Limpa os campos de entrada após a operação de venda
            vendaMarcaField.setText("");
            vendaModeloField.setText("");
            vendaValorField.setText("");
            vendaPlacaField.setText("");
            vendaClienteField.setText("");
            vendaDataHoraField.setText("");
        });

        // Configura a ação do botão "apagar" para excluir um registro no banco de dados
        apagar.addActionListener(e -> {

            // Chama o método "apagar" do objeto operacoes com o valor do campo de entrada
            // "placa"

            boolean camposVendaVazio = vendaMarcaField.getText().isEmpty() || vendaModeloField.getText().isEmpty()
                    || vendaValorField.getText().isEmpty() || vendaPlacaField.getText().isEmpty()
                    || vendaClienteField.getText().isEmpty() || vendaDataHoraField.getText().isEmpty();

            if (camposVendaVazio) {
                JOptionPane.showMessageDialog(null, "Não é Possível Excluir Dados Vazios!", "Informação Inválida",
                        2);
            } else if (JOptionPane.showConfirmDialog(null, "Deseja excluir esse carro?",
                    "Excluindo Tarefa...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                operacoes.apagar(vendaPlacaField.getText());

                // Limpa os campos de entrada após a operação de exclusão
                vendaMarcaField.setText("");
                vendaModeloField.setText("");
                vendaValorField.setText("");
                vendaPlacaField.setText("");
                vendaClienteField.setText("");
                vendaDataHoraField.setText("");

            }
        });

    }

    // Métodos (Atualizar Tabela)
    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        vendas = new VendasDAO().listarTodos();
        // Obtém os carros atualizados do banco de dados
        for (Vendas venda : vendas) {
            // Adiciona os dados de cada carro como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { venda.getMarca(), venda.getModelo(),
                    venda.getPlaca(), venda.getCliente(), venda.getValor(), venda.getDataHora() });
        }
    }

}