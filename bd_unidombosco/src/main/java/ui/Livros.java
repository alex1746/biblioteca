package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;



public class Livros extends JFrame {
    private JTextField textFieldCategoryName;

    // Botões do painel de livros
    private JButton addButton, editButton, deleteButton, listButton;
    private Connection connect;

    // Inserir o cadastro dentro do banco de dados, nome, ano, id_autor e id_categoria dos livros
    private String cadastro = "INSERT INTO livros (nome, ano, id_autor, id_categoria) VALUES (?, ?, ?, ?)";

    // Vincula a classe de autores na classe atual
    private Autores autores;

    // Vincula a classe de categorias na classe atual
    private Categorias categorias;

    // // Vincula a parte do painel e funções no banco de dados, configura o painel

    public Livros() throws SQLException {
        setTitle("Cadastro de Livros");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        connect = DriverManager.getConnection("jdbc:sqlite:biblioteca.db");

        autores = new Autores();
        categorias = new Categorias();

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


        // Botão para adicionar livros
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // Código para adicionar um novo livro
            String nome = JOptionPane.showInputDialog("Nome do Livro:");
            String anoLivro = JOptionPane.showInputDialog("Ano do Livro:");

            //Listar autores
            System.out.println("");
            System.out.println("Listando Autores:");
            System.out.println("");
            autores.listarAutores();
            String idAutor = JOptionPane.showInputDialog("ID do Autor:");
            String nomeAutor = autores.obterNomeAutorPorID(idAutor);



            //Listar categorias
            System.out.println("");
            System.out.println("Listando categorias:");
            System.out.println("");
            categorias.listarCategorias();
            System.out.println("");

            String idCategoria = JOptionPane.showInputDialog("ID da Categoria:");
            String nomeCategoria = categorias.obterNomeCategoriaPorID(idCategoria);

            if (nome != null && !nome.isEmpty()) {
                inserirLivro(nome, anoLivro, idAutor, idCategoria);
            System.out.println("Novo livro cadastrado: " + nome + " | Ano: " + anoLivro + " | Autor: " + nomeAutor + " | Categoria: " + nomeCategoria);
            } else {
                JOptionPane.showMessageDialog(null, "Operação cancelada ou nome do livro vazio. Nenhum livro cadastrado.");
            }
            }
        });

        // Botão para editar livros

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                listarLivros();

                String idLivro = JOptionPane.showInputDialog("Insira o ID do livro que deseja editar:");

                if (idLivro != null && !idLivro.isEmpty()) {

                    String novoNomeLivro = JOptionPane.showInputDialog("Insira um novo nome para o livro:");
                    String novoAnoLivro = JOptionPane.showInputDialog("Insira um novo ano para o livro:");

                    System.out.println("");
                    System.out.println("Listando Autores:");
                    System.out.println("");
                    autores.listarAutores();
                    String idAutor = JOptionPane.showInputDialog("Insira um novo ID de autor para o livro:");
                    String nomeAutor = autores.obterNomeAutorPorID(idAutor);


                    System.out.println("");
                    System.out.println("Listando categorias:");
                    System.out.println("");
                    categorias.listarCategorias();
                    System.out.println("");

                    String novoIdCategoria = JOptionPane.showInputDialog("Insira um novo ID de categoria para o livro:");

                    if (novoNomeLivro != null && novoAnoLivro != null && idAutor != null && novoIdCategoria != null) {
                        editarLivro(idLivro, novoNomeLivro, novoAnoLivro, idAutor, novoIdCategoria);
                        System.out.println("Livro editado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ID do livro inválido.");
                }
            }
        });

        // Botão para deletar autores

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                        // Solicita o ID do livro
                    System.out.println("");
                    System.out.println("Listando livros:");
                    System.out.println("");
                    String query = "SELECT id, nome, ano, id_autor, id_categoria FROM livros";
                    PreparedStatement consulta = null;
                    try {
                        consulta = connect.prepareStatement(query);
                        ResultSet resultSet = consulta.executeQuery();

                    while (resultSet.next()) {
                    int idLivro = resultSet.getInt("id");
                        String nomeLivro = resultSet.getString("nome");
                        String anoLivro = resultSet.getString("ano");
                        String idAutor = resultSet.getString("id_autor");
                        String idCategoria = resultSet.getString("id_categoria");
                        String nomeAutor = autores.obterNomeAutorPorID(idAutor);
                        String nomeCategoria = categorias.obterNomeCategoriaPorID(idCategoria);
                        System.out.println(idLivro + " - " + nomeLivro + " | Ano: " + anoLivro + " | Autor: " + nomeAutor + " | Categoria: " + nomeCategoria);
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

        // Botão para listar livros

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Código para listar todas os livros
                listarLivros();
                }
                });

                add(panel);
            }

    // Função dos botões
    // Adicionar livros e colocar no SQL

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

    // Editar livros puxando o ID, nome e ano, idAutor, idCategoria e atualizando para as novas informações, colocando no SQL

    public void editarLivro(String idLivro, String novoNomeLivro, String novoAnoLivro, String novoIdAutor, String novoIdCategoria) {
        System.out.println("");
        System.out.println("Listando livros:");
        System.out.println("");
        String updateQuery = "UPDATE livros SET nome = ?, ano = ?, id_autor = ?, id_categoria = ? WHERE id = ?";
        PreparedStatement updateStmt = null;

        try {
            updateStmt = connect.prepareStatement(updateQuery);
            updateStmt.setString(1, novoNomeLivro);
            updateStmt.setString(2, novoAnoLivro);
            updateStmt.setString(3, novoIdAutor);
            updateStmt.setString(4, novoIdCategoria);
            updateStmt.setString(5, idLivro);

            int rowsAffected = updateStmt.executeUpdate();

            if (rowsAffected > 0) {
            } else {
                System.out.println("Nenhum livro encontrado com o ID: " + idLivro);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (updateStmt != null) {
                    updateStmt.close();
                }
            } catch (SQLException ex) {
                // Lidar com exceções
            }
        }
    }

    // Deletar livros por ID e atualizar no SQL

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

                System.out.println("");
                System.out.println("Livro: " + livroID + " - " + nomeLivro + " excluído com sucesso!");
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

    // Listar autores

    public void listarLivros() {
        System.out.println("");
        System.out.println("Listando livros:");
        System.out.println("");
        String query = "SELECT id, nome, ano, id_autor, id_categoria FROM livros";
        PreparedStatement consulta = null;

        try {
            consulta = connect.prepareStatement(query);
            ResultSet resultSet = consulta.executeQuery();

            while (resultSet.next()) {
                int idLivro = resultSet.getInt("id");
                    String nomeLivro = resultSet.getString("nome");
                    String anoLivro = resultSet.getString("ano");
                    String idAutor = resultSet.getString("id_autor");
                    String nomeAutor = autores.obterNomeAutorPorID(idAutor);
                    String idCategoria = resultSet.getString("id_categoria");
                    String nomeCategoria = categorias.obterNomeCategoriaPorID(idCategoria);
                    System.out.println(idLivro + " - " + nomeLivro + " | Ano: " + anoLivro + " | Autor: " + nomeAutor + " | Categoria: " + nomeCategoria);
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

