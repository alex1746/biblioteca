package app;

import java.sql.*;

public class Autores {

    Integer id;
    String nome;
    String nacionalidade;


    public static void main(String[] args) throws SQLException {
        String updateAutor = "update autores set nome=? where id=?";
        String selectAutor = "select * from autores where id=?";
        String pesquisaAutor = "select *from autores";
        String cadastroAutor = "insert into autores (nome, nacionalidade) values (?, ?)";
        String removerAutor = "delete from autores where id=?";

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

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }


    }
