package app.View;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import app.Connection.CarrosDAO;
import app.Controller.CarrosControl;
import app.Model.Carros;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import app.Controller.CarrosControl;
import app.Connection.CarrosDAO;
import app.Model.Carros;
public class VendasView extends JPanel{
    JComboBox<String> carrosComboBox;
    List<Carros> carros;
    private JButton vendido;

    public VendasView() {
        super();
        carrosComboBox = new JComboBox<>();
        // Preencha o JComboBox com os carros
        carros = new CarrosDAO().listarTodos();
        carrosComboBox.addItem("Selecione o carro");
        for (Carros carro : carros) {
            carrosComboBox.addItem(carro.getMarca()
                                    + " " + carro.getModelo()
                                    + " " + carro.getPlaca());
        }
        vendido = new JButton("Vendido.");

        add(carrosComboBox);
        add(vendido);

        // Criando os métododos
    }
}
