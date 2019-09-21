/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1_compiladores_segundosemestre_2019;

import Modelos.Componente;
import Modelos.Ufe;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

/**
 *
 * @author Eleazar Lopez <Universidad de San Carlos de Guatemala>
 */
public class Graficador {

    HashMap<String, Componente> componentes;

    public Graficador(HashMap<String, Componente> componentes) {
        this.componentes = componentes;
    }

    public void agregarPanel(ArrayList<Componente> componentes, JPanel panelPrincipal, JFrame frame) {
        for (Componente componente : componentes) {
            switch (componente.getTipo()) {
                case panel:
                    JPanel panel = new JPanel();
                    panel.setName(componente.getId());
                    panel.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    panel.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        panel.setBorder(border);
                    }
                    panel.setLayout(null);
                    panel.setName(componente.getNombre());
                    agregarPanel(componente.getComponentes(), panel, frame);
                    panelPrincipal.add(panel);
                    break;
                case text:
                    JLabel label = new JLabel();
                    label.setName(componente.getId());
                    label.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    label.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        label.setBorder(border);
                    }
                    label.setText(componente.getContenido());
                    panelPrincipal.add(label);
                    break;
                case textfield:
                    JTextField textField = new JTextField();
                    textField.setName(componente.getId());
                    textField.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    textField.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        textField.setBorder(border);
                    }
                    textField.setText(componente.getContenido());
                    panelPrincipal.add(textField);
                    break;
                case button:
                    JButton boton = new JButton();
                    boton.setName(componente.getNombre());
                    boton.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    boton.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        boton.setBorder(border);
                    }
                    boton.setText(componente.getNombre());
                    boton.addActionListener((ActionEvent e) -> {
                        JOptionPane.showMessageDialog(frame, componente.getOnClick());
                    });
                    panelPrincipal.add(boton);
                    break;
                case image:
                    URL direccion;
                    try {
                        direccion = new URL(componente.getFuente());
                        ImageIcon imagen = new ImageIcon(direccion);
                        label = new JLabel();
                        label.setName(componente.getId());
                        label.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                        label.setBackground(componente.getColor());
                        if (componente.getBorder() > 0) {
                            Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                            label.setBorder(border);
                        }
                        label.setText(componente.getContenido());
                        label.setIcon(imagen);
                        panelPrincipal.add(label);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(Graficador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case spinner:
                    SpinnerModel modelo = new SpinnerNumberModel(componente.getDefecto(), componente.getMin(), componente.getMax(), 1);
                    JSpinner spinner = new JSpinner(modelo);
                    spinner.setName(componente.getId());
                    spinner.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    spinner.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        spinner.setBorder(border);
                    }
                    panelPrincipal.add(spinner);
                    break;
                case list:
                    JComboBox comboBox = new JComboBox();
                    comboBox.setName(componente.getId());
                    comboBox.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    comboBox.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        comboBox.setBorder(border);
                    }
                    componente.getItems().forEach(comboBox::addItem);
                    comboBox.setSelectedIndex(componente.getDefecto());
                    panelPrincipal.add(comboBox);
                    break;
            }
        }
    }

    public void agregarFrame(ArrayList<Componente> componentes, JFrame frame, String div) {
        for (Componente componente : componentes) {
            switch (componente.getTipo()) {
                case panel:
                    JPanel panel = new JPanel();
                    panel.setName(componente.getId());
                    panel.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    panel.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        panel.setBorder(border);
                    }
                    panel.setName(div);
                    panel.setLayout(null);
                    agregarPanel(componente.getComponentes(), panel, frame);
                    frame.add(panel);
                    break;
                case text:
                    JLabel label = new JLabel();
                    label.setName(componente.getId());
                    label.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    label.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        label.setBorder(border);
                    }
                    label.setText(componente.getContenido());
                    frame.add(label);
                    break;
                case textfield:
                    JTextField textField = new JTextField();
                    textField.setName(componente.getId());
                    textField.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    textField.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        textField.setBorder(border);
                    }
                    textField.setText(componente.getContenido());
                    frame.add(textField);
                    break;
                case button:
                    JButton boton = new JButton();
                    boton.setName(componente.getNombre());
                    boton.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    boton.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        boton.setBorder(border);
                    }
                    boton.setText(componente.getNombre());
                    boton.addActionListener((ActionEvent e) -> {
                        JOptionPane.showMessageDialog(frame, componente.getOnClick());
                    });
                    frame.add(boton);
                    break;
                case image:
                    URL direccion;
                    try {
                        direccion = new URL(componente.getFuente());
                        ImageIcon imagen = new ImageIcon(direccion);
                        label = new JLabel();
                        label.setName(componente.getId());
                        label.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                        label.setBackground(componente.getColor());
                        if (componente.getBorder() > 0) {
                            Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                            label.setBorder(border);
                        }
                        label.setText(componente.getContenido());
                        label.setIcon(imagen);
                        frame.add(label);
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(Graficador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case spinner:
                    SpinnerModel modelo = new SpinnerNumberModel(componente.getDefecto(), componente.getMin(), componente.getMax(), 1);
                    JSpinner spinner = new JSpinner(modelo);
                    spinner.setName(componente.getId());
                    spinner.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    spinner.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        spinner.setBorder(border);
                    }
                    frame.add(spinner);
                    break;
                case list:
                    JComboBox comboBox = new JComboBox();
                    comboBox.setName(componente.getId());
                    comboBox.setBounds(componente.getPosX(), componente.getPosY(), componente.getWidth(), componente.getHeight());
                    comboBox.setBackground(componente.getColor());
                    if (componente.getBorder() > 0) {
                        Border border = BorderFactory.createLineBorder(Color.black, componente.getBorder());
                        comboBox.setBorder(border);
                    }
                    componente.getItems().forEach(comboBox::addItem);
                    comboBox.setSelectedIndex(componente.getDefecto());
                    frame.add(comboBox);
                    break;
            }
        }
    }

    public void Graficar() {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        componentes.forEach((div, componente) -> {
            switch (componente.getTipo()) {
                case ufex:
                    agregarFrame(componente.getComponentes(), frame, div);
                    break;
                default:
                    break;
            }
        });
        // set up the jframe, then display it
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
