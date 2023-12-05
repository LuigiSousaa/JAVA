package com.projeto.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class EscolhaLogin extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel, escolhaPanel;
    private JButton btnFuncionario, btnCliente;

    public EscolhaLogin() {
        setTitle("DEV Marketplace");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Página de escolha
        escolhaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        escolhaPanel.setPreferredSize(new Dimension(500, 500));

        // Carregando os ícones do diretório de recursos
        ClassLoader classLoader = getClass().getClassLoader();

        URL funcionarioIconUrl = classLoader.getResource("administrador.png");
        ImageIcon funcionarioIcon = new ImageIcon(funcionarioIconUrl);


        URL clienteIconUrl = classLoader.getResource("cliente.png");
        ImageIcon clienteIcon = new ImageIcon(clienteIconUrl);

        btnFuncionario = new JButton("Login como Funcionário", funcionarioIcon);
        btnFuncionario.setContentAreaFilled(false);
        btnFuncionario.setFocusPainted(false);
        btnFuncionario.setOpaque(false);
        btnFuncionario.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCliente = new JButton("Login como Cliente", clienteIcon);
        btnCliente.setContentAreaFilled(false);
        btnCliente.setFocusPainted(false);
        btnCliente.setOpaque(false);
        btnCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
        gbc.insets = new Insets(0, 10, 0, 10); // Espaçamento entre os botões
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
