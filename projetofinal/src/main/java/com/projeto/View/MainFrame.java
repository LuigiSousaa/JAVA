package com.projeto.View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {
    // criação do tabbedPane para incluir as tabs
    private JTabbedPane jTPane;

    public MainFrame() {
        jTPane = new JTabbedPane();
        add(jTPane);
        // criandos as tabs
        // tab1 carros
        EscolhaLogin tab1 = new EscolhaLogin();
        jTPane.add(tab1, "Clientes");
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // métodos para tornar a janela visível
    public void run() {
        this.setVisible(true);
    }
}