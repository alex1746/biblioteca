package ui;

import app.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainApp extends JFrame {
    private JButton bookButton, authorButton, categoryButton;

    public MainApp() {

        setTitle("Sistema de Gerenciamento de Biblioteca");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        bookButton = new JButton("Gerenciar Livros");
        authorButton = new JButton("Gerenciar Autores");
        categoryButton = new JButton("Gerenciar Categorias");

        panel.add(bookButton);
        panel.add(authorButton);
        panel.add(categoryButton);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookCRUD().setVisible(true);
            }
        });

        authorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AuthorCRUD().setVisible(true);
            }
        });

        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CategoryCRUD().setVisible(true);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainApp().setVisible(true);
            }
        });
    }
}
