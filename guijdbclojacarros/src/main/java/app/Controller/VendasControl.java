package app.Controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import app.Connection.VendasDAO;
import app.Model.Vendas;
import app.Connection.CarrosDAO;

public class VendasControl {
    // Atributos
    private List<Vendas> vendas;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public VendasControl(List<Vendas> vendas, DefaultTableModel tableModel, JTable table) {
        this.vendas = vendas;
        this.tableModel = tableModel;
        this.table = table;
    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        vendas = new VendasDAO().listarTodos();
        // Obtém os vendas atualizados do banco de dados
        for (Vendas venda : vendas) {
            if (venda.getMarca().equals("") && venda.getModelo().equals("") && venda.getValor().equals("")
                    && venda.getPlaca().equals("")
                    && venda.getCliente().equals("")
                    && venda.getDataHora().equals("")) {
                JOptionPane.showMessageDialog(null, "Preencha os Campos Corretamente", "Informação Inválida", 1);
            } else {
                // Adiciona os dados de cada venda como uma nova linha na tabela Swing
                tableModel.addRow(new Object[] { venda.getMarca(), venda.getModelo(), venda.getValor(),
                        venda.getPlaca(), venda.getCliente(), venda.getDataHora() });
            }
        }
    }

    // Método para vender um novo venda no banco de dados
    public void vender(String marca, String modelo, String valor, String placa, String cliente, String dataHora) {
        new VendasDAO().vender(marca, modelo, valor, placa, cliente, dataHora);
        // Chama o método de cadastro no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
        CarrosDAO opera = new CarrosDAO();
        opera.apagar(placa);
    }

    // Método para atualizar os dados de um venda no banco de dados
    public void atualizar(String marca, String modelo, String valor, String placa, String cliente, String dataHora) {
        new VendasDAO().atualizar(marca, modelo, valor, placa, cliente, dataHora);
        // Chama o método de atualização no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a atualização

    }

    // Método para apagar um venda do banco de dados
    public void apagar(String placa) {
        new VendasDAO().apagar(placa);
        // Chama o método de exclusão no banco de dados
        atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
    }

    // Método para excluir carro em outro banco de dados
    // public void apagarCarro(String placa) {
    //     new VendasDAO().apagarCarro(placa);
    //     try {
    //         JOptionPane.showMessageDialog(null, "Carro com placa " + placa + " não está mais diponível para compra",
    //                 "Exclusão de Carro", JOptionPane.INFORMATION_MESSAGE);
    //     } catch (Exception e) {
    //         JOptionPane.showMessageDialog(null, "Erro ao excluir carro do outro banco de dados.", "Erro",
    //                 JOptionPane.ERROR_MESSAGE);
    //         e.printStackTrace(); // Apenas para depuração, remova em produção
    //     }
    // }
}
