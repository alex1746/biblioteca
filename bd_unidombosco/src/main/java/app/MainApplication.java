package app;
import javax.swing.*;
import java.sql.*;
public class MainApplication {
    public static void main(String[] args) throws SQLException {
        System.out.println("Projeto de Sistemas - Valdinei");
        //DriverManager - Connection - ResultSet - PreparedStatement
        String pesquisa = "select * from categorias";
        String cadastro = "insert into categorias (nome) values (?)";
        Connection connect = DriverManager.getConnection("jdbc:sqlite:biblioteca.db");
        //Inserir um cadastro
        String nome = JOptionPane.showInputDialog("Nome da Categoria:");
        PreparedStatement insercao = connect.prepareStatement(cadastro);
        insercao.setString(1, nome);
        insercao.execute();
        //Ler todos os valores do banco
        PreparedStatement stmt = connect.prepareStatement(pesquisa);
        ResultSet categorias = stmt.executeQuery();
        while(categorias.next()){
            System.out.println(categorias.getString("nome") + " - " + categorias.getString("id"));
        }

    }
}
