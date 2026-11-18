package padaria;

import java.util.ArrayList;
import java.util.Scanner;

public class Aplicacao {

    // private static: um único Scanner, usado só dentro desta classe (Apêndice A)
    private static final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        Padaria padaria = new Padaria("Padaria Pão Quente");
        carregarDadosIniciais(padaria);

        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            System.out.println();

            switch (opcao) {
                case 1 -> cadastrarProduto(padaria);
                case 2 -> Relatorio.imprimirEstoque(padaria);
                case 3 -> registrarVenda(padaria);
                case 4 -> reporEstoque(padaria);
                case 5 -> alterarPreco(padaria);
                case 6 -> buscarProduto(padaria);
                case 7 -> Relatorio.imprimirVendasDaSemana(padaria);
                case 8 -> Relatorio.imprimirEstoqueBaixo(padaria);
                case 9 -> Relatorio.imprimirFechamento(padaria);
                case 10 -> Relatorio.imprimirHistorico(padaria);
                case 0 -> {
                    Relatorio.imprimirFechamento(padaria);
                    System.out.println("Sistema encerrado.");
                }
                default -> System.out.println("Opção inválida. Digite um número de 0 a 10.");
            }
        } while (opcao != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n==========================================");
        System.out.println("   PADARIA PÃO QUENTE - MENU PRINCIPAL");
        System.out.println("==========================================");
        System.out.println("1  - Cadastrar produto");
        System.out.println("2  - Listar estoque");
        System.out.println("3  - Registrar venda");
        System.out.println("4  - Repor estoque");
        System.out.println("5  - Alterar preço");
        System.out.println("6  - Buscar produto por nome");
        System.out.println("7  - Relatório de vendas da semana");
        System.out.println("8  - Produtos com estoque baixo");
        System.out.println("9  - Fechamento");
        System.out.println("10 - Histórico de operações");
        System.out.println("0  - Sair");
    }

    private static void cadastrarProduto(Padaria padaria) {
        System.out.println("Cadastro de produto");
        String nome = lerTexto("Nome do produto: ");
        System.out.print("Categoria: ");
        String categoria = entrada.nextLine().trim();
        double preco = lerDecimal("Preço (R$): ");
        int quantidade = lerInteiro("Quantidade inicial: ");

        System.out.println(padaria.cadastrarProduto(nome, categoria, preco, quantidade));
    }

    private static void registrarVenda(Padaria padaria) {
        System.out.println("Registro de venda");
        int codigo = lerInteiro("Código do produto: ");
        int unidades = lerInteiro("Quantidade vendida: ");
        int dia = lerInteiro("Dia (1-Seg 2-Ter 3-Qua 4-Qui 5-Sex 6-Sáb 7-Dom): ");

        System.out.println(padaria.registrarVenda(codigo, unidades, dia));
    }

    private static void reporEstoque(Padaria padaria) {
        System.out.println("Reposição de estoque");
        int codigo = lerInteiro("Código do produto: ");
        int unidades = lerInteiro("Quantidade a repor: ");

        System.out.println(padaria.reporEstoque(codigo, unidades));
    }

    private static void alterarPreco(Padaria padaria) {
        System.out.println("Alteração de preço");
        int codigo = lerInteiro("Código do produto: ");
        double novoPreco = lerDecimal("Novo preço (R$): ");

        System.out.println(padaria.alterarPreco(codigo, novoPreco));
    }

    private static void buscarProduto(Padaria padaria) {
        String trecho = lerTexto("Digite parte do nome do produto: ");
        ArrayList<Produto> encontrados = padaria.buscarPorNome(trecho);
        Relatorio.imprimirResultadoBusca(encontrados, trecho);
    }

    private static void carregarDadosIniciais(Padaria padaria) {
        // Quadro 11 - Produtos iniciais
        padaria.cadastrarProduto("Pão francês", "Pães", 0.75, 200);
        padaria.cadastrarProduto("Pão de queijo", "Salgados", 4.50, 60);
        padaria.cadastrarProduto("Bolo de cenoura", "Doces", 28.00, 6);
        padaria.cadastrarProduto("Sonho", "Doces", 6.00, 25);
        padaria.cadastrarProduto("Café coado", "Bebidas", 5.00, 80);

        // Quadro 12 - Vendas iniciais
        // 1. Seg
        padaria.registrarVenda(1, 120, 1);
        padaria.registrarVenda(2, 15, 1);
        padaria.registrarVenda(5, 30, 1);

        // 2. Ter
        padaria.registrarVenda(1, 60, 2);
        padaria.registrarVenda(3, 2, 2);

        // 3. Qua
        padaria.registrarVenda(4, 10, 3);
        padaria.registrarVenda(2, 20, 3);
        padaria.registrarVenda(5, 25, 3);
    }

    // Lê um número inteiro. Se o usuário digitar algo inválido, pergunta de novo
    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = entrada.nextLine().trim();
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido: digite um número inteiro.");
            }
        }
    }

    /** Lê um número decimal. Aceita vírgula ou ponto (5,50 ou 5.50). */
    private static double lerDecimal(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = entrada.nextLine().trim().replace(',', '.');
            try {
                double valor = Double.parseDouble(texto);
                if (Double.isFinite(valor)) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // texto que não é número cai na mensagem abaixo
            }
            System.out.println("Valor inválido: digite um número, como 5,50.");
        }
    }

    // Lê uma linha de texto que não pode ficar vazia
    private static String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = entrada.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("Este campo não pode ficar vazio.");
        }
    }
}