package app;

import java.sql.*;


public class Categoria {

    Integer id;
    String nome;



    public static void main(String[] args) throws SQLException {
        String updateCategoria = "update categorias set nome=? where id=?";
        String selectCategoria = "select * from categorias where id=?";
        String pesquisaCategoria = "select *from categorias";
        String cadastroCategoria = "insert into categorias (nome) values (?)";
        String removerCategoria = "delete from categorias where id=?";


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

    }


