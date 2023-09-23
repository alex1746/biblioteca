package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Autores extends JFrame {
    private JButton addButton, editButton, deleteButton, listButton;
    private JTextField textFieldNationality;
    private Connection connect;
    private String cadastro = "INSERT INTO autores (nome, nacionalidade) VALUES (?, ?)";

    public String obterNomeAutorPorID(String idAutor) {
        String query = "SELECT nome FROM autores WHERE id = ?";
        PreparedStatement consulta = null;

        try {
            consulta = connect.prepareStatement(query);
            consulta.setString(1, idAutor);
            ResultSet resultSet = consulta.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("nome");
            } else {
                return "Autor não encontrado";
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


    public Autores() throws SQLException {
        setTitle("Cadastro de Autores");
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
                String nomeAutor = JOptionPane.showInputDialog("Nome do Autor:");
                String nacionalidade = JOptionPane.showInputDialog("Nacionalidade do Autor:");

                if (nomeAutor != null && !nomeAutor.isEmpty() &&
                        nacionalidade != null && !nacionalidade.isEmpty()) {
                    inserirAutores(nomeAutor, nacionalidade);
                    System.out.println("Novo autor cadastrado: " + nomeAutor + ", " + nacionalidade);
                } else {
                    JOptionPane.showMessageDialog(null, "Operação cancelada ou dados inválidos. Nenhum autor foi cadastrado.");
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Listando Autores:");
                String query = "SELECT id, nome, nacionalidade FROM autores";
                PreparedStatement consulta = null;
                try {
                    consulta = connect.prepareStatement(query);
                    ResultSet resultSet = consulta.executeQuery();

                    while (resultSet.next()) {
                        int idAutor = resultSet.getInt("id");
                        String nomeAutor = resultSet.getString("nome");
                        String nacionalidade = resultSet.getString("nacionalidade");
                        System.out.println(idAutor + " - " + nomeAutor + ", " + nacionalidade);
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

                String idAutor = JOptionPane.showInputDialog("Insira o ID do Autor que deseja editar:");
                String novoNomeAutor = JOptionPane.showInputDialog("Insira um novo nome para o autor:");

                if (idAutor != null && !idAutor.trim().isEmpty() && novoNomeAutor != null && !novoNomeAutor.trim().isEmpty()) {
                    String novaNacionalidade = JOptionPane.showInputDialog("Insira a nova nacionalidade do autor:");
                    if (novaNacionalidade != null) {
                        editarAutor(idAutor, novoNomeAutor, novaNacionalidade);
                    } else {
                        JOptionPane.showMessageDialog(null, "Nacionalidade inválida");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ID ou nome do autor inválido(s)");
                }
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Listando Autores:");
                String query = "SELECT id, nome, nacionalidade FROM autores";
                PreparedStatement consulta = null;

                try {
                    consulta = connect.prepareStatement(query);
                    ResultSet resultSet = consulta.executeQuery();

                    while (resultSet.next()) {
                        int idAutor = resultSet.getInt("id");
                        String nomeAutor = resultSet.getString("nome");
                        String nacionalidade = resultSet.getString("nacionalidade");
                        System.out.println(idAutor + " - " + nomeAutor + ", " + nacionalidade);
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

                String idAutor = JOptionPane.showInputDialog("Insira o ID do autor que deseja excluir:");

                if (idAutor != null && !idAutor.trim().isEmpty()) {
                    deletarAutor(idAutor);
                } else {
                    JOptionPane.showMessageDialog(null, "ID do autor inválido");
                }
            }
        });


        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Listando Autores:");
                listarAutores();
            }
        });

        add(panel);
    }

    public void inserirAutores(String nomeAutor, String nacionalidade) {
        PreparedStatement insercao = null;
        try {
            insercao = connect.prepareStatement(cadastro);
            insercao.setString(1, nomeAutor);
            insercao.setString(2, nacionalidade); // Adicione a nacionalidade à query SQL
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

    public void editarAutor(String idAutor, String novoNomeAutor, String novaNacionalidade) {
        String selectQuery = "SELECT id, nome, nacionalidade FROM autores WHERE id = ?";
        String updateQuery = "UPDATE autores SET nome = ?, nacionalidade = ? WHERE id = ?";
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;

        try {
            selectStmt = connect.prepareStatement(selectQuery);
            selectStmt.setString(1, idAutor);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int autorID = resultSet.getInt("id");
                String nomeAutorAtual = resultSet.getString("nome");
                String nacionalidadeAutorAtual = resultSet.getString("nacionalidade");

                updateStmt = connect.prepareStatement(updateQuery);
                updateStmt.setString(1, novoNomeAutor);
                updateStmt.setString(2, novaNacionalidade);
                updateStmt.setString(3, idAutor);
                updateStmt.executeUpdate();

                System.out.println("Autor: " + nomeAutorAtual + ", " + nacionalidadeAutorAtual + " foi editado com sucesso para " + novoNomeAutor + ", " + novaNacionalidade);
            } else {
                System.out.println("Nenhum autor encontrado com o ID: " + idAutor);
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


    public void deletarAutor(String idAutor) {
        String selectQuery = "SELECT id, nome FROM autores WHERE id = ?";
        String deleteQuery = "DELETE FROM autores WHERE id = ?";

        PreparedStatement selectStmt = null;
        PreparedStatement deleteStmt = null;

        try {
            selectStmt = connect.prepareStatement(selectQuery);
            selectStmt.setString(1, idAutor);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                int autorID = resultSet.getInt("id");
                String nomeAutor = resultSet.getString("nome");

                deleteStmt = connect.prepareStatement(deleteQuery);
                deleteStmt.setString(1, idAutor);
                deleteStmt.executeUpdate();

                System.out.println("Autor " + nomeAutor + " excluído com sucesso!");
            } else {
                System.out.println("Nenhum autor encontrado com o ID: " + idAutor);
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

    public void listarAutores() {
        String query = "SELECT id, nome, nacionalidade FROM autores";
        PreparedStatement consulta = null;

        try {
            consulta = connect.prepareStatement(query);
            ResultSet resultSet = consulta.executeQuery();

            while (resultSet.next()) {
                int idAutor = resultSet.getInt("id");
                String nomeAutor = resultSet.getString("nome");
                String nacionalidade = resultSet.getString("nacionalidade");
                System.out.println(idAutor + " - " + nomeAutor + ", " + nacionalidade);
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
                    new Autores().setVisible(true);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        );

    }
}

