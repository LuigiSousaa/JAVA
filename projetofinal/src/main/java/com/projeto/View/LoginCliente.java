package com.projeto.View;

import javax.swing.*;

import com.projeto.Connection.ClientesDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginCliente extends JFrame {
    private JTextField cpfTextField;

    public LoginCliente() {
        // Configuração da janela
        setTitle("Login do Cliente");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout e componentes
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5); // Espaçamento

        JLabel cpfLabel = new JLabel("CPF:");
        cpfTextField = new JTextField(15);
        JButton loginButton = new JButton("Login");

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_END;
        panel.add(cpfLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.LINE_START;
        panel.add(cpfTextField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, constraints);

        // Adiciona o ActionListener para o botão de login
        loginButton.addActionListener(e -> {
            realizarLogin();
        });

        // Adiciona o painel à janela
        add(panel);

        // Torna a janela visível
        setVisible(true);
    }

    private void realizarLogin() {
        String cpf = cpfTextField.getText();

        // Cria uma instância de ClientesDAO
        ClientesDAO clientesDAO = new ClientesDAO();

        // Chama o método verificarCPFExistente para validar o CPF
        if (clientesDAO.verificarCPFExistente(cpf)) {
            // Lógica para login bem-sucedido
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso para o CPF: " + cpf);
        } else {
            // Lógica para login falhado
            JOptionPane.showMessageDialog(this, "CPF não encontrado. Por favor, informe um CPF válido.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginCliente();
            }
        });
    }
}
