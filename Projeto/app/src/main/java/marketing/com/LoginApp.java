package marketing.com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login - Mercado Virtual");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(10, 10, 10, 10);

            JTextField usernameField = new JTextField(20);
            JPasswordField passwordField = new JPasswordField(20);

            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Adicione a lógica de login aqui
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    System.out.println("Username: " + username);
                    System.out.println("Password: " + password);
                }
            });

            // Adicione uma imagem ao painel
            ImageIcon marketImage = new ImageIcon("market.jpg");
            JLabel imageLabel = new JLabel(marketImage);

            constraints.gridx = 0;
            constraints.gridy = 0;
            panel.add(imageLabel, constraints);

            constraints.gridx = 1;
            constraints.gridy = 0;
            panel.add(usernameField, constraints);

            constraints.gridx = 1;
            constraints.gridy = 1;
            panel.add(passwordField, constraints);

            constraints.gridx = 1;
            constraints.gridy = 2;
            panel.add(loginButton, constraints);

            frame.getContentPane().add(panel);
            frame.setVisible(true);
        });
    }
}
