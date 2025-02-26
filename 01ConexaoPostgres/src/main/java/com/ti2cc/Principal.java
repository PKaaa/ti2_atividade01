package com.ti2cc;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        DAO dao = new DAO();

        if (!dao.conectar()) {
            System.out.println("Erro ao conectar ao banco de dados.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Listar usuários");
            System.out.println("2 - Inserir usuário");
            System.out.println("3 - Atualizar usuário");
            System.out.println("4 - Excluir usuário");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    listarUsuarios(dao);
                    break;
                case 2:
                    inserirUsuario(dao, scanner);
                    break;
                case 3:
                    atualizarUsuario(dao, scanner);
                    break;
                case 4:
                    excluirUsuario(dao, scanner);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 5);

        dao.close();
        scanner.close();
    }

    private static void listarUsuarios(DAO dao) {
        System.out.println("==== Lista de Usuários ====");
        Usuario[] usuarios = dao.getUsuarios();
        for (Usuario u : usuarios) {
            System.out.println(u.toString());
        }
    }

    private static void inserirUsuario(DAO dao, Scanner scanner) {
        System.out.print("Digite o código do usuário: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a senha do usuário: ");
        String senha = scanner.nextLine();

        System.out.print("Digite o sexo (M/F): ");
        char sexo = scanner.next().charAt(0);

        Usuario usuario = new Usuario(codigo, nome, senha, sexo);
        if (dao.inserirUsuario(usuario)) {
            System.out.println("Usuário inserido com sucesso!");
        } else {
            System.out.println("Erro ao inserir usuário.");
        }
    }

    private static void atualizarUsuario(DAO dao, Scanner scanner) {
        System.out.print("Digite o código do usuário a ser atualizado: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Digite a nova senha: ");
        String novaSenha = scanner.nextLine();

        Usuario usuario = new Usuario(codigo, "", novaSenha, ' ');
        if (dao.atualizarUsuario(usuario)) {
            System.out.println("Usuário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar usuário.");
        }
    }

    private static void excluirUsuario(DAO dao, Scanner scanner) {
        System.out.print("Digite o código do usuário a ser excluído: ");
        int codigo = scanner.nextInt();

        if (dao.excluirUsuario(codigo)) {
            System.out.println("Usuário excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir usuário.");
        }
    }
}
