import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CadastroUsuarios extends JFrame {
    private JTextField inputNome;
    private JTextField inputIdade;
    private JTextField inputDataAgendamento;
    private JTextField inputHoraAgendamento;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Usuario> usuarios = new ArrayList<>();
    private int linhaSelecionada = -1;

    public CadastroUsuarios() {
        setTitle("Cadastro e Agendamento de Usuários");
        setSize(850, 400);
        setDefaultCloseOperation(2);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel cadastroPanel = criarCadastroPanel();
        JPanel agendamentoPanel = criarAgendamentoPanel();

        tabbedPane.addTab("Cadastro", cadastroPanel);
        tabbedPane.addTab("Agendamento", agendamentoPanel);

        add(tabbedPane);

        File arquivo = new File("dados.txt");
        if (arquivo.exists()) {
            usuarios = Serializacao.deserializar("dados.txt");
            atualizarTabela();
        }
    }

    private JPanel criarCadastroPanel() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Nome");
        tableModel.addColumn("Idade");
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        inputNome = new JTextField(20);
        inputIdade = new JTextField(5);
        inputDataAgendamento = new JTextField(20);
        JButton cadastrarButton = new JButton("Cadastrar");
        JButton atualizarButton = new JButton("Atualizar");
        JButton apagarButton = new JButton("Apagar");
        JButton apagarTodosButton = new JButton("Apagar Todos");
        JButton salvarButton = new JButton("Salvar");
        JPanel inputPanel = new JPanel();

        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(inputNome);
        inputPanel.add(new JLabel("Idade:"));
        inputPanel.add(inputIdade);
        inputPanel.add(cadastrarButton);
        inputPanel.add(atualizarButton);
        inputPanel.add(apagarButton);
        inputPanel.add(apagarTodosButton);
        inputPanel.add(salvarButton);

        JPanel cadastroPanel = new JPanel(new BorderLayout());
        cadastroPanel.add(inputPanel, BorderLayout.NORTH);
        cadastroPanel.add(scrollPane, BorderLayout.CENTER);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputNome.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputIdade.setText(table.getValueAt(linhaSelecionada, 1).toString());
                }
            }
        });

        OperacoesUsuarios operacoes = new OperacoesUsuarios(usuarios, tableModel, table);
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacoes.cadastrarUsuario(inputNome.getText(), inputIdade.getText());
                inputNome.setText("");
                inputIdade.setText("");
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacoes.atualizarUsuario(linhaSelecionada, inputNome.getText(), inputIdade.getText());
            }
        });

        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacoes.apagarUsuario(linhaSelecionada);
            }
        });

        apagarTodosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacoes.apagarTodosUsuarios();
            }
        });

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacoes.salvarUsuarios();
            }
        });

        return cadastroPanel;
    }

    private JPanel criarAgendamentoPanel() {
        DefaultTableModel agendamentoTableModel = new DefaultTableModel();
        agendamentoTableModel.addColumn("Data");
        agendamentoTableModel.addColumn("Hora");

        inputDataAgendamento = new JTextField(20);
        inputHoraAgendamento = new JTextField(20);

        JTable agendamentoTable = new JTable(agendamentoTableModel);
        JScrollPane agendamentoScrollPane = new JScrollPane(agendamentoTable);

        JPanel agendamentoPanel = new JPanel(new BorderLayout());
        agendamentoPanel.add(agendamentoScrollPane, BorderLayout.CENTER);

        // Adicione aqui a lógica para agendamento

        return agendamentoPanel;
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        for (Usuario usuario : usuarios) {
            tableModel.addRow(new Object[] { usuario.getNome(), usuario.getIdade() });
        }
    }

    public void run() {
        setVisible(true);
    }
}
