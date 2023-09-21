package app;

import java.sql.*;


public class TesteBD {
    public static void main(String[] args) throws SQLException {
        String updateAluno = "update alunos set nome=?, ra=? where id=?";
        String selectAluno = "select *from alunos where id=?";
        String pesquisaAluno = "select *from alunos";
        String cadastroAluno = "insert into alunos (nome, ra, data_cadastro, curso, ano_conclusao) values (?, ?, ?, ?, ?)";
        String removerAluno = "delete from alunos where id=?";
        //Testar update
        Connection connect = DriverManager.getConnection("jdbc:sqlite:unidombosco.db");
        PreparedStatement preparedUpdate = connect.prepareStatement(updateAluno);
        preparedUpdate.setString(1, "Maria");
        preparedUpdate.setString(2, "1234567890");
        preparedUpdate.setInt(3, 1);
        preparedUpdate.execute();
        //Testar select
        PreparedStatement preparedSelect = connect.prepareStatement(selectAluno);
        preparedSelect.setInt(1, 2);
        ResultSet consulta = preparedSelect.executeQuery();
        while(consulta.next()){
            System.out.println(consulta.getString("nome"));
        }

    }
}
