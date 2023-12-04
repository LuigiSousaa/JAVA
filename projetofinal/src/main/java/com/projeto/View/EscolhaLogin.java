package com.projeto.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EscolhaLogin extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel, escolhaPanel;
    private JButton btnFuncionario, btnCliente;

    public EscolhaLogin() {
        setTitle("Escolha de Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Página de escolha
        escolhaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        escolhaPanel.setPreferredSize(new Dimension(300, 200));

        btnFuncionario = new JButton("Login como Funcionário");
        btnCliente = new JButton("Login como Cliente");

        btnFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "loginFuncionario");
            }
        });

        btnCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "loginCliente");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Espaçamento entre os botões
        gbc.anchor = GridBagConstraints.CENTER;

        escolhaPanel.add(btnFuncionario, gbc);

        gbc.gridx = 1;
        escolhaPanel.add(btnCliente, gbc);

        // Adicionando páginas ao cardPanel
        cardPanel.add(escolhaPanel, "escolha");
        cardPanel.add(new LoginFuncionario(), "loginFuncionario");
        cardPanel.add(new LoginCliente(), "loginCliente");

        // Exibindo a página de escolha por padrão
        cardLayout.show(cardPanel, "escolha");

        add(cardPanel);

        pack(); // Empacota os componentes para ajustar automaticamente o tamanho da janela
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EscolhaLogin());
    }
}