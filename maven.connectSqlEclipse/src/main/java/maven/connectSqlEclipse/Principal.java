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
            System.out.println("1. Listar Passagens Aéreas");
            System.out.println("2. Inserir Passagem Aérea");
            System.out.println("3. Excluir Passagem Aérea");
            System.out.println("4. Atualizar Passagem Aérea");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    // Listar passagens aéreas
                    System.out.println("==== Mostrar passagens aéreas === ");
                    PassagemAerea[] passagensAereas = dao.getPassagensAereas();
                    for (int i = 0; i < passagensAereas.length; i++) {
                        System.out.println(passagensAereas[i].toString());
                    }
                    break;

                case 2:
                    // Inserir passagem aérea
                    System.out.print("Digite o número do voo: ");
                    int codigoVoo = scanner.nextInt();
                    System.out.print("Digite a cidade de origem: ");
                    String cidadeOrigem = scanner.next();
                    System.out.print("Digite a cidade de destino: ");
                    String cidadeDestino = scanner.next();
                    System.out.print("Digite o nome do passageiro: ");
                    String nomePassageiro = scanner.next();

                    PassagemAerea novaPassagemAerea = new PassagemAerea(codigoVoo, nomePassageiro, cidadeOrigem, cidadeDestino);
                    if (dao.inserirPassagemAerea(novaPassagemAerea)) {
                        System.out.println("Inserção com sucesso -> " + novaPassagemAerea.toString());
                    } else {
                        System.out.println("Falha ao inserir passagem aérea.");
                    }
                    break;

                case 3:
                    // Excluir passagem aérea
                    System.out.print("Digite o código da passagem aérea a ser excluída: ");
                    int codigoExcluir = scanner.nextInt();
                    if (dao.excluirPassagemAerea(codigoExcluir)) {
                        System.out.println("Passagem aérea excluída com sucesso.");
                    } else {
                        System.out.println("Falha ao excluir passagem aérea. Verifique o código.");
                    }
                    break;

                case 4:
                    // Atualizar passagem aérea
                    System.out.print("Digite o código da passagem aérea a ser atualizada: ");
                    int codigoAtualizar = scanner.nextInt();
                    PassagemAerea passagemAereaAtualizar = dao.getPassagemAereaByCodigo(codigoAtualizar);

                    if (passagemAereaAtualizar != null) {
                        System.out.print("Digite a nova cidade de destino: ");
                        String novaCidadeDestino = scanner.next();
                        passagemAereaAtualizar.setCidadeDestino(novaCidadeDestino);
                        if (dao.atualizarPassagemAerea(passagemAereaAtualizar)) {
                            System.out.println("Passagem aérea atualizada com sucesso.");
                        } else {
                            System.out.println("Falha ao atualizar passagem aérea.");
                        }
                    } else {
                        System.out.println("Passagem aérea não encontrada.");
                    }
                    break;
            }

        } while (escolha != 5);

        scanner.close();
        dao.close();
    }
}
