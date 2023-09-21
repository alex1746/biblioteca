package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoryCRUD extends JFrame {
    private JTextField textFieldCategoryName;
    private JButton addButton, updateButton, deleteButton, listButton;

    public CategoryCRUD() {
        setTitle("Cadastro de Categorias");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Label e TextField para o nome da categoria
        JLabel labelCategoryName = new JLabel("Nome da Categoria: ");
        textFieldCategoryName = new JTextField();
        panel.add(labelCategoryName);
        panel.add(textFieldCategoryName);

        // Botões para as operações de CRUD
        addButton = new JButton("Adicionar");
        updateButton = new JButton("Atualizar");
        deleteButton = new JButton("Deletar");
        listButton = new JButton("Listar");

        // Adiciona os botões ao painel
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(listButton);

        // Eventos para os botões (a implementar)
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para adicionar uma nova categoria
                System.out.println("Adicionando categoria: " + textFieldCategoryName.getText());
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para atualizar uma categoria
                System.out.println("Atualizando categoria: " + textFieldCategoryName.getText());
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para deletar uma categoria
                System.out.println("Deletando categoria: " + textFieldCategoryName.getText());
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para listar todas as categorias
                System.out.println("Listando todas as categorias");
            }
        });

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CategoryCRUD().setVisible(true);
            }
        });
    }
}
