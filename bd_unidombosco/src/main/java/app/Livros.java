package app;

import java.sql.*;

public class Livros {

    Integer id;
    String nome;
    Date ano;
    Integer id_autor;
    Integer id_categoria;


    public static void main(String[] args) throws SQLException {
        String updateLivro = "update livros set nome=? where id=?";
        String selectLivro = "select * from livros where id=?";
        String pesquisaLivro = "select *from livros";
        String cadastroLivro = "insert into livros (nome, ano, id_autor, id_categoria) values (?, ?, ?, ?)";
        String removerLivro = "delete from livros where id=?";

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getAno() {
        return ano;
    }

    public void setAno(Date ano) {
        this.ano = ano;
    }

    public Integer getId_autor() {
        return id_autor;
    }

    public void setId_autor(Integer id_autor) {
        this.id_autor = id_autor;
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

}
