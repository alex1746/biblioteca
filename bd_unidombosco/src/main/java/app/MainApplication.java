package app;
import javax.swing.*;
import java.sql.*;
public class MainApplication {
    public static void main(String[] args) throws SQLException {
        System.out.println("Meu projeto com banco de dados");
        //DriverManager - Connection - ResultSet - PreparedStatement
        String pesquisa = "select *from alunos";
        String cadastro = "insert into alunos (nome, ra) values (?, ?)";
        Connection connect = DriverManager.getConnection("jdbc:sqlite:unidombosco.db");
        //Inserir um cadastro
        String nome = JOptionPane.showInputDialog("Nome:");
        String ra = JOptionPane.showInputDialog("RA:");
        PreparedStatement insercao = connect.prepareStatement(cadastro);
        insercao.setString(1, nome);
        insercao.setString(2, ra);
        insercao.execute();
        //Ler todos os valores do banco
        PreparedStatement stmt = connect.prepareStatement(pesquisa);
        ResultSet alunos = stmt.executeQuery();
        while(alunos.next()){
            System.out.println(alunos.getString("nome") + " - " + alunos.getString("id"));
        }

    }
}
