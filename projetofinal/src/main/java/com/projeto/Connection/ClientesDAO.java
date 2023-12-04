package com.projeto.Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.projeto.Controller.*;
import com.projeto.Model.Cliente;

/**
 * ClientesDAO
 */
public class ClientesDAO {
    // atributo
    private Connection connection;
    private List<Cliente> clientes;

    // construtor
    public ClientesDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    // criar Tabela
    public void criaTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS clientes_lojacarros (NOME VARCHAR(255),EMAIL VARCHAR(255) PRIMARY KEY,ENDERECO VARCHAR(255),TELEFONE VARCHAR(255))";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    // Listar todos os valores cadastrados
    // public List<Cliente> listarTodos() {
    //     PreparedStatement stmt = null;
    //     ResultSet rs = null;
    //     clientes = new ArrayList<>();
    //     try {
    //         stmt = connection.prepareStatement("SELECT * FROM clientes_lojacarros");
    //         rs = stmt.executeQuery();
    //         while (rs.next()) {
    //             Cliente clientes = new Clientes(
    //                     rs.getString("nome"),
    //                     rs.getString("email"),
    //                     rs.getString("endereco"),
    //                     rs.getString("telefone"));
    //             clientes.add(cliente);
    //         }
    //     } catch (SQLException ex) {
    //         System.out.println(ex);
    //     } finally {
    //         ConnectionFactory.closeConnection(connection, stmt, rs);
    //     }
    //     return clientes;
    // }

    // Cadastrar Cliente no banco
    public void cadastrar(String nome, String email, String endereco, String telefone) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO clientes_lojacarros (nome, email, endereco, telefone) VALUES (?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, endereco);
            stmt.setString(4, telefone);
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Atualizar dados no banco
    public void atualizar(String nome, String email, String endereco, String telefone) {
        PreparedStatement stmt = null;
        String sql = "UPDATE clientes_lojacarros SET nome = ?, endereco = ?, telefone = ? WHERE email = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, endereco);
            stmt.setString(3, telefone);
            stmt.setString(4, email);
            stmt.executeUpdate();
            System.out.println("Dados atualizados com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Apagar dados do banco
    public void apagar(String email) {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM clientes_lojacarros WHERE email = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.executeUpdate();
            System.out.println("Dado apagado com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
}