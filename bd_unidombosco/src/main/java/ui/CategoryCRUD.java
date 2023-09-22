package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CategoryCRUD extends JFrame {
    private JTextField textFieldCategoryName;
    private JButton addButton, updateButton, deleteButton, listButton;
    private Connection connect;
    private String cadastro = "INSERT INTO categorias (nome) VALUES (?)";

    public CategoryCRUD() throws SQLException {
        setTitle("Cadastro de Categorias");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        connect = DriverManager.getConnection("jdbc:sqlite:biblioteca.db");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Label e TextField para o nome da categoria
        JLabel labelCategoryName = new JLabel("Nome da Categoria: ");
        textFieldCategoryName = new JTextField();
        panel.add(labelCategoryName);
        panel.add(textFieldCategoryName);

        // Botões para as operações de CRUD
        addButton = new JButton("Adicionar");
        updateButton = new JButton("Editar");
        deleteButton = new JButton("Deletar");
        listButton = new JButton("Listar");

        // Adiciona os botões ao painel
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(listButton);

        // Eventos para os botões
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para adicionar uma nova categoria
                System.out.println("Adicionando categoria: " + textFieldCategoryName.getText());
                // Inserir um cadastro
                String nome = JOptionPane.showInputDialog("Nome da Categoria:");
                inserirCategoria(nome);
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para atualizar uma categoria
                System.out.println("Editando categoria: " + textFieldCategoryName.getText());
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Solicita o ID da Categoria
                String idCategoria = JOptionPane.showInputDialog("ID da Categoria a Deletar:");
                String nomeCategoria = ("nome");

                // Verifica se o ID é válido (não vazio)
                if (idCategoria != null && !idCategoria.trim().isEmpty()) {
                    deletarCategoria(idCategoria);
                } else {
                    JOptionPane.showMessageDialog(null, "ID da Categoria inválido");
                }
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para listar todas as categorias
                System.out.println("Listando todas as categorias");
                listarCategorias();
            }
        });

        add(panel);
    }

    public void inserirCategoria(String nomeCategoria) {
        PreparedStatement insercao = null;
        try {
            insercao = connect.prepareStatement(cadastro);
            insercao.setString(1, nomeCategoria);
            insercao.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (insercao != null) {
                    insercao.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public void deletarCategoria(String idCategoria) {
        String selectQuery = "SELECT id, nome FROM categorias WHERE id = ?";
        String deleteQuery = "DELETE FROM categorias WHERE id = ?";

        PreparedStatement selectStmt = null;
        PreparedStatement deleteStmt = null;

        try {
            // Seleciona a categoria pelo ID e obtém o nome
            selectStmt = connect.prepareStatement(selectQuery);
            selectStmt.setString(1, idCategoria);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int categoriaID = resultSet.getInt("id");
                String nomeCategoria = resultSet.getString("nome");

                // Exclui a categoria
                deleteStmt = connect.prepareStatement(deleteQuery);
                deleteStmt.setString(1, idCategoria);
                deleteStmt.executeUpdate();

                // Imprime o ID e o nome da categoria excluída
                System.out.println("Categoria excluída: ID=" + categoriaID + ", Nome=" + nomeCategoria);
            } else {
                System.out.println("Nenhuma categoria encontrada com o ID: " + idCategoria);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (selectStmt != null) {
                    selectStmt.close();
                }
                if (deleteStmt != null) {
                    deleteStmt.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public void listarCategorias() {
        String query = "SELECT id, nome FROM categorias";
        PreparedStatement consulta = null;

        try {
            consulta = connect.prepareStatement(query);
            ResultSet resultSet = consulta.executeQuery();

            while (resultSet.next()) {
                int idCategoria = resultSet.getInt("id");
                String nomeCategoria = resultSet.getString("nome");
                System.out.println("ID: " + idCategoria);
                System.out.println("Nome: " + nomeCategoria);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (consulta != null) {
                    consulta.close();
                }
            } catch (SQLException ex) {
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CategoryCRUD().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

