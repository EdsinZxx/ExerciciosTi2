package maven.connectSqlEclipse;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        DAO dao = new DAO();
        dao.conectar();

        Scanner scanner = new Scanner(System.in);

        int escolha;

        do {
            System.out.println("===== Menu =====");
            System.out.println("1. Listar usuários");
            System.out.println("2. Inserir usuário");
            System.out.println("3. Excluir usuário");
            System.out.println("4. Atualizar usuário");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    // Listar usuários
                    System.out.println("==== Mostrar usuários === ");
                    Usuario[] usuarios = dao.getUsuarios();
                    for (int i = 0; i < usuarios.length; i++) {
                        System.out.println(usuarios[i].toString());
                    }
                    break;

                case 2:
                    // Inserir usuário
                    System.out.print("Digite o código do usuário: ");
                    int codigo = scanner.nextInt();
                    System.out.print("Digite o nome do usuário: ");
                    String nome = scanner.next();
                    System.out.print("Digite a senha do usuário: ");
                    String senha = scanner.next();
                    System.out.print("Digite o sexo do usuário (M/F): ");
                    char sexo = scanner.next().charAt(0);

                    Usuario novoUsuario = new Usuario(codigo, nome, senha, sexo);
                    if (dao.inserirUsuario(novoUsuario)) {
                        System.out.println("Inserção com sucesso -> " + novoUsuario.toString());
                    } else {
                        System.out.println("Falha ao inserir usuário.");
                    }
                    break;

                case 3:
                    // Excluir usuário
                    System.out.print("Digite o código do usuário a ser excluído: ");
                    int codigoExcluir = scanner.nextInt();
                    if (dao.excluirUsuario(codigoExcluir)) {
                        System.out.println("Usuário excluído com sucesso.");
                    } else {
                        System.out.println("Falha ao excluir usuário. Verifique o código.");
                    }
                    break;

                case 4:
                    // Atualizar usuário
                    System.out.print("Digite o código do usuário a ser atualizado: ");
                    int codigoAtualizar = scanner.nextInt();
                    Usuario usuarioAtualizar = dao.getUsuarioByCodigo(codigoAtualizar);

                    if (usuarioAtualizar != null) {
                        System.out.print("Digite a nova senha do usuário: ");
                        String novaSenha = scanner.next();
                        usuarioAtualizar.setSenha(novaSenha);
                        if (dao.atualizarUsuario(usuarioAtualizar)) {
                            System.out.println("Usuário atualizado com sucesso.");
                        } else {
                            System.out.println("Falha ao atualizar usuário.");
                        }
                    } else {
                        System.out.println("Usuário não encontrado.");
                    }
                    break;
            }

        } while (escolha != 5);

        scanner.close();
        dao.close();
    }
}
