package app;

import java.sql.*;


public class Categoria {

    String nome ;
    public static void main(String[] args) throws SQLException {
        String updateCategoria = "update categorias set nome=? where id=?";
        String selectCategoria = "select * from categorias where id=?";
        String pesquisaCategoria = "select *from categorias";
        String cadastroCategoria = "insert into categorias (nome) values (?)";
        String removerCategoria = "delete from categorias where id=?";

        }

    }

