package com.ti2cc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    private Connection conexao;

    public DAO() {
        conexao = null;
    }

    public boolean conectar() {
        String driverName = "org.postgresql.Driver";                    
        String serverName = "localhost";
        String mydatabase = "teste";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        String username = "ti2cc";
        String password = "ti@cc";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com sucesso!");
        } catch (ClassNotFoundException e) { 
            System.err.println("Erro: Driver não encontrado - " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados - " + e.getMessage());
        }

        return status;
    }

    public boolean close() {
        boolean status = false;
        if (conexao != null) {
            try {
                conexao.close();
                status = true;
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return status;
    }

    public boolean inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (codigo, login, senha, sexo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setInt(1, usuario.getCodigo());
            st.setString(2, usuario.getLogin());
            st.setString(3, usuario.getSenha());
            st.setString(4, String.valueOf(usuario.getSexo()));
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET login = ?, senha = ?, sexo = ? WHERE codigo = ?";
        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setString(1, usuario.getLogin());
            st.setString(2, usuario.getSenha());
            st.setString(3, String.valueOf(usuario.getSexo()));
            st.setInt(4, usuario.getCodigo());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirUsuario(int codigo) {
        String sql = "DELETE FROM usuario WHERE codigo = ?";
        try (PreparedStatement st = conexao.prepareStatement(sql)) {
            st.setInt(1, codigo);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
            return false;
        }
    }

    public Usuario[] getUsuarios() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (PreparedStatement st = conexao.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                listaUsuarios.add(new Usuario(
                    rs.getInt("codigo"), 
                    rs.getString("login"), 
                    rs.getString("senha"), 
                    rs.getString("sexo").charAt(0)
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários: " + e.getMessage());
        }

        return listaUsuarios.toArray(new Usuario[0]);
    }

    public Usuario[] getUsuariosMasculinos() {
        String sql = "SELECT * FROM usuario WHERE sexo = 'M'";
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (PreparedStatement st = conexao.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                listaUsuarios.add(new Usuario(
                    rs.getInt("codigo"), 
                    rs.getString("login"), 
                    rs.getString("senha"), 
                    rs.getString("sexo").charAt(0)
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários masculinos: " + e.getMessage());
        }

        return listaUsuarios.toArray(new Usuario[0]);
    }
}
