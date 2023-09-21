package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorCRUD extends JFrame {
    private JTextField textFieldAuthorName, textFieldNationality;
    private JButton addButton, updateButton, deleteButton, listButton;

    public AuthorCRUD() {
        setTitle("Gerenciamento de Autores");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel labelAuthorName = new JLabel("Nome do Autor: ");
        textFieldAuthorName = new JTextField();
        panel.add(labelAuthorName);
        panel.add(textFieldAuthorName);

        JLabel labelNationality = new JLabel("Nacionalidade: ");
        textFieldNationality = new JTextField();
        panel.add(labelNationality);
        panel.add(textFieldNationality);

        addButton = new JButton("Adicionar");
        updateButton = new JButton("Atualizar");
        deleteButton = new JButton("Deletar");
        listButton = new JButton("Listar");

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(listButton);

        addButton.addActionListener(e -> System.out.println("Adicionando autor: " + textFieldAuthorName.getText()));
        updateButton.addActionListener(e -> System.out.println("Atualizando autor: " + textFieldAuthorName.getText()));
        deleteButton.addActionListener(e -> System.out.println("Deletando autor: " + textFieldAuthorName.getText()));
        listButton.addActionListener(e -> System.out.println("Listando todos os autores"));

        add(panel);
    }
}
