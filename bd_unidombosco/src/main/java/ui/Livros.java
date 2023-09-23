package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;



public class Livros extends JFrame {
    private JTextField textFieldCategoryName;
    private JButton addButton, editButton, deleteButton, listButton;
    private Connection connect;
    private String cadastro = "INSERT INTO livros (nome, ano, id_autor, id_categoria) VALUES (?, ?, ?, ?)";

    public Livros() throws SQLException {
        setTitle("Cadastro de Livros");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        connect = DriverManager.getConnection("jdbc:sqlite:biblioteca.db");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));


        // Botões de operação CRUD
        addButton = new JButton("Adicionar");
        listButton = new JButton("Listar");
        editButton = new JButton("Editar");
        deleteButton = new JButton("Excluir");


        // Botões do painel
        panel.add(addButton);
        panel.add(listButton);
        panel.add(editButton);
        panel.add(deleteButton);


        // Eventos dos botões
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para adicionar um novo livro
                String nome = JOptionPane.showInputDialog("Nome do Livro:");
                String nomeAutor = JOptionPane.showInputDialog("Ano do Livro:");
                String idAutor = JOptionPane.showInputDialog("ID do Autor:");


                //AQUI É PRA SER O LISTAR AUTORES


                String idCategoria = JOptionPane.showInputDialog("ID da Categoria:");

                //AQUI É PARA SER O LISTAR CATEGORIAS

                if (nome != null && !nome.isEmpty()) {
                    inserirLivro(nome, nomeAutor, idAutor, idCategoria);
                    System.out.println("Novo livro cadastrado: " + nome + " | Autor: " + nomeAutor + " | Categoria: " + idCategoria);
                } else {
                    JOptionPane.showMessageDialog(null, "Operação cancelada ou nome do livro vazio. Nenhum livro cadastrado.");
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém o ID do livro a ser editado e o novo nome do livro

                System.out.println("Listando livros:");
                String query = "SELECT id, nome FROM livros";
                PreparedStatement consulta = null;
                try {
                    consulta = connect.prepareStatement(query);
                    ResultSet resultSet = consulta.executeQuery();

                    while (resultSet.next()) {
                        int idLivro = resultSet.getInt("id");
                        String nomeLivro = resultSet.getString("nome");
                        System.out.println(idLivro + " - " + nomeLivro);
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

                String idLivro = JOptionPane.showInputDialog("Insira o ID do livro que deseja editar:");

                String novoNomeLivro = JOptionPane.showInputDialog("Insira um novo nome para o livro:");

                // Verifica se o ID e o novo nome são válidos (não vazios)
                if (idLivro != null && !idLivro.trim().isEmpty() &&
                        novoNomeLivro != null && !novoNomeLivro.trim().isEmpty()) {
                    editarLivro(idLivro, novoNomeLivro);
                } else {
                    JOptionPane.showMessageDialog(null, "ID ou nome do livro inválido(s)");


                }
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Solicita o ID do livro
                System.out.println("Listando livros:");
                String query = "SELECT id, nome FROM livros";
                PreparedStatement consulta = null;
                try {
                    consulta = connect.prepareStatement(query);
                    ResultSet resultSet = consulta.executeQuery();

                    while (resultSet.next()) {
                        int idLivro = resultSet.getInt("id");
                        String nomeLivro = resultSet.getString("nome");
                        System.out.println(idLivro + " - " + nomeLivro);
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
                String idLivro = JOptionPane.showInputDialog("Insira o ID do livro que deseja excluir:");
                String nomeLivro = ("nome");

                // Verifica se o ID é válido (não vazio)
                if (idLivro != null && !idLivro.trim().isEmpty()) {
                    deletarLivro(idLivro);
                } else {
                    JOptionPane.showMessageDialog(null, "ID do livro inválido");
                }
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para listar todas os livros
                System.out.println("Listando livros:");
                listarLivros();
            }
        });

        add(panel);
    }

    public void inserirLivro(String nomeLivro, String anoLivro, String idAutor, String idCategoria) {
        PreparedStatement insercao = null;
        try {
            insercao = connect.prepareStatement(cadastro);
            insercao.setString(1, nomeLivro);
            insercao.setString(2, anoLivro);
            insercao.setString(3, idAutor);
            insercao.setString(4, idCategoria);
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

    public void editarLivro(String idLivro, String novoNomeLivro) {
        String selectQuery = "SELECT id, nome FROM livros WHERE id = ?";
        String updateQuery = "UPDATE livros SET nome = ? WHERE id = ?";
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;

        try {
            selectStmt = connect.prepareStatement(selectQuery);
            selectStmt.setString(1, idLivro);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int livroID = resultSet.getInt("id");
                String nomeLivroAtual = resultSet.getString("nome");

                updateStmt = connect.prepareStatement(updateQuery);
                updateStmt.setString(1, novoNomeLivro);
                updateStmt.setString(2, idLivro);
                updateStmt.executeUpdate();

                System.out.println("O livro " + nomeLivroAtual + " foi editado com sucesso para " + novoNomeLivro);
            } else {
                System.out.println("Nenhum livro encontrado com o ID: " + idLivro);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (selectStmt != null) {
                    selectStmt.close();
                }
                if (updateStmt != null) {
                    updateStmt.close();
                }
            } catch (SQLException ex) {
            }
        }
    }


    public void deletarLivro(String idLivro) {
        String selectQuery = "SELECT id, nome FROM livros WHERE id = ?";
        String deleteQuery = "DELETE FROM livros WHERE id = ?";

        PreparedStatement selectStmt = null;
        PreparedStatement deleteStmt = null;

        try {
            // Seleciona o livro pelo ID e obtém o nome
            selectStmt = connect.prepareStatement(selectQuery);
            selectStmt.setString(1, idLivro);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int livroID = resultSet.getInt("id");
                String nomeLivro = resultSet.getString("nome");

                // Exclui o livro
                deleteStmt = connect.prepareStatement(deleteQuery);
                deleteStmt.setString(1, idLivro);
                deleteStmt.executeUpdate();

                // Imprime o ID e o nome do livro excluído
                System.out.println("Livro " + nomeLivro + " foi excluído com sucesso!");
            } else {
                System.out.println("Nenhum livro encontrado com o ID: " + idLivro);
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

    public void listarLivros() {
        String query = "SELECT id, nome, ano, id_autor, id_categoria FROM livros";
        PreparedStatement consulta = null;

        try {
            consulta = connect.prepareStatement(query);
            ResultSet resultSet = consulta.executeQuery();

            while (resultSet.next()) {
                int idLivros = resultSet.getInt("id");
                String nomeLivros = resultSet.getString("nome");
                String anoLivro = resultSet.getString("ano");
                String idAutor = resultSet.getString("id_autor");
                String idCategoria = resultSet.getString("id_categoria");
                System.out.println(idLivros + " - " + nomeLivros + ", " + anoLivro + ", " + idAutor + ", " + idCategoria );
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
                    new Livros().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}

