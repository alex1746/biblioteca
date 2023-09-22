package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookCRUD extends JFrame {
    private JTextField textFieldBookName, textFieldYear;
    private JButton addButton, updateButton, deleteButton, listButton;

    public BookCRUD() {
        setTitle("Gerenciamento de Livros");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel labelBookName = new JLabel("Nome do Livro: ");
        textFieldBookName = new JTextField();
        panel.add(labelBookName);
        panel.add(textFieldBookName);

        JLabel labelYear = new JLabel("Ano de Publicação: ");
        textFieldYear = new JTextField();
        panel.add(labelYear);
        panel.add(textFieldYear);

        addButton = new JButton("Adicionar");
        updateButton = new JButton("Editar");
        deleteButton = new JButton("Deletar");
        listButton = new JButton("Listar");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(listButton);

        addButton.addActionListener(e -> System.out.println("Adicionando livro: " + textFieldBookName.getText()));
        updateButton.addActionListener(e -> System.out.println("Editando livro: " + textFieldBookName.getText()));
        deleteButton.addActionListener(e -> System.out.println("Deletando livro: " + textFieldBookName.getText()));
        listButton.addActionListener(e -> System.out.println("Listando todos os livros"));

        add(panel);
    }
}
