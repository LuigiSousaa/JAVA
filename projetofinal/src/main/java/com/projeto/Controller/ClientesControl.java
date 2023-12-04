// package com.projeto.Controller;

// import java.util.List;
// import javax.swing.JTable;
// import javax.swing.table.DefaultTableModel;

// import com.projeto.Model.Cliente;

// /**
//  * ClientesControl
//  */
// public class ClientesControl {
//     // Atributos
//     private List<Cliente> clientes;
//     private DefaultTableModel tableModel;
//     private JTable table;

//     // Construtor
//     public ClientesControl(List<Cliente> clientes, DefaultTableModel tableModel, JTable table) {
//         this.clientes = clientes;
//         this.tableModel = tableModel;
//         this.table = table;
//     }

//     public ClientesControl() {
//     }

//     // Método para atualizar a tabela de exibição com dados do banco de dados
//     private void atualizarTabela() {
//         tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
//         clientes = new ClientesDAO().listarTodos();
//         // Obtém os clientes atualizados do banco de dados
//         for (Cliente cliente : clientes) {
//             // Adiciona os dados de cada cliente como uma nova linha na tabela Swing
//             tableModel.addRow(new Object[] { cliente.getDataNascimento(),
//                     cliente.getUsername() });
//         }
//     }

//     // Método para cadastrar um novo cliente no banco de dados
//     public void cadastrar(String dataNascimento, String username, String senha) {
//         new ClientesDAO().cadastrar(dataNascimento, username, senha);
//         // Chama o método de cadastro no banco de dados
//         atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
//     }

//     // Método para atualizar os dados de um cliente no banco de dados
//     public void atualizar(String dataNascimento, String username, String senha) {
//         new ClientesDAO().atualizar(dataNascimento, username, senha);
//         // Chama o método de atualização no banco de dados
//         atualizarTabela(); // Atualiza a tabela de exibição após a atualização
//     }

//     // Método para apagar um cliente do banco de dados
//     public void apagar(String username, String senha) {
//         new ClientesDAO().apagar(username, senha);
//         // Chama o método de exclusão no banco de dados
//         atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
//     }

//     public void limpar(String dataNascimento, String username, String senha) {
//         // Implemente a lógica para limpar os campos, se necessário
//     }
// }
