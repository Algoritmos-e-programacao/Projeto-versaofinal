package padaria;

import java.util.ArrayList;
import java.util.Locale;

public class Relatorio {

    private static final int LARGURA = 72;

    private Relatorio() {
    }

    public static void imprimirEstoque(Padaria padaria) {
        titulo("RELATÓRIO DE ESTOQUE");

        System.out.println("Padaria: " + padaria.getNome());
        linha();

        System.out.printf("%-6s %-24s %-15s %10s %10s%n",
                "Código", "Produto", "Categoria", "Preço", "Estoque");

        linha();

        ArrayList<Produto> produtos = padaria.getProdutos();

        for (Produto produto : produtos) {
            System.out.printf("%-6d %-24s %-15s %10s %10d%n",
                    produto.getCodigo(),
                    cortar(produto.getNome(), 24),
                    cortar(produto.getCategoria(), 15),
                    moeda(produto.getPreco()),
                    produto.getQuantidade());
        }

        linha();

        System.out.println("Quantidade de produtos: " + produtos.size());
        System.out.println("Valor total em estoque: "
                + moeda(padaria.valorTotalEmEstoque()));

        rodape();
    }

    public static void imprimirResultadoBusca(
            ArrayList<Produto> encontrados, String trecho) {

        titulo("RESULTADO DA BUSCA");

        System.out.println("Termo pesquisado: " + trecho);
        linha();

        if (encontrados == null || encontrados.isEmpty()) {
            System.out.println("Nenhum produto encontrado.");
        } else {
            for (Produto produto : encontrados) {
                System.out.println(produto);
            }

            System.out.println();
            System.out.println("Total encontrado: " + encontrados.size());
        }

        rodape();
    }

    public static void imprimirVendasDaSemana(Padaria padaria) {
        titulo("RELATÓRIO DE VENDAS DA SEMANA");

        ArrayList<Produto> produtos = padaria.getProdutos();

        System.out.println("Padaria: " + padaria.getNome());
        linha();

        System.out.printf("%-22s", "Produto");

        for (String dia : Padaria.DIAS) {
            System.out.printf("%7s", dia);
        }

        System.out.printf("%9s%n", "Total");
        linha();

        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);

            System.out.printf("%-22s",
                    cortar(produto.getNome(), 22));

            int totalProduto = padaria.totalVendidoDoProduto(i);

            for (int dia = 1; dia <= 7; dia++) {
                System.out.printf("%7d", padaria.getVendas(i, dia));
            }

            System.out.printf("%9d%n", totalProduto);
        }

        linha();

        System.out.printf("%-22s", "Total por dia");

        int totalGeral = 0;
        for (int dia = 1; dia <= 7; dia++) {
            int totalDia = padaria.totalVendidoNoDia(dia);
            totalGeral += totalDia;
            System.out.printf("%7d", totalDia);
        }
        System.out.printf("%9d%n", totalGeral);

        linha();

        System.out.println("Faturamento semanal: "
                + moeda(padaria.getFaturamentoTotal()));

        System.out.println("Produto mais vendido: "
                + nomeProduto(padaria.produtoMaisVendido()));

        int melhorDia = padaria.diaDeMaiorFaturamento();

        if (melhorDia >= 1 && melhorDia <= 7) {
            System.out.println("Dia de maior faturamento: "
                    + Padaria.DIAS[melhorDia - 1]
                    + " - "
                    + moeda(padaria.getFaturamentoDoDia(melhorDia)));
        } else {
            System.out.println("Dia de maior faturamento: Sem vendas");
        }

        rodape();
    }

    public static void imprimirEstoqueBaixo(Padaria padaria) {
        titulo("PRODUTOS COM ESTOQUE BAIXO");

        ArrayList<Produto> produtos = padaria.produtosComEstoqueBaixo();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto com estoque baixo.");
        } else {
            System.out.println("Produtos com menos de "
                    + Padaria.LIMITE_ESTOQUE_BAIXO + " unidades:");
            linha();

            for (Produto produto : produtos) {
                System.out.println(
                        produto.getCodigo() + " - "
                        + produto.getNome()
                        + " | Estoque: "
                        + produto.getQuantidade());
            }

            System.out.println();
            System.out.println("Total de produtos com estoque baixo: "
                    + produtos.size());
        }

        rodape();
    }

    public static void imprimirFechamento(Padaria padaria) {
        titulo("FECHAMENTO DA SEMANA - " + padaria.getNome());

        System.out.println("Faturamento por dia:");
        for (int dia = 1; dia <= 7; dia++) {
            System.out.printf("  %-3s %s%n",
                    Padaria.DIAS[dia - 1],
                    moeda(padaria.getFaturamentoDoDia(dia)));
        }
        System.out.println("-".repeat(48));

        System.out.println("Faturamento total da semana: "
                + moeda(padaria.getFaturamentoTotal()));

        System.out.println("Produto mais vendido: "
                + nomeProduto(padaria.produtoMaisVendido()));

        int melhorDia = padaria.diaDeMaiorFaturamento();

        if (melhorDia >= 1 && melhorDia <= 7) {
            System.out.println("Dia de maior faturamento: "
                    + Padaria.DIAS[melhorDia - 1]);
        } else {
            System.out.println("Dia de maior faturamento: Sem vendas");
        }

        System.out.println("Valor total em estoque: "
                + moeda(padaria.valorTotalEmEstoque()));

        System.out.println("Produtos com estoque baixo: "
                + padaria.produtosComEstoqueBaixo().size());

        System.out.println("Operações registradas: "
                + padaria.getHistorico().size());

        rodape();
    }

    public static void imprimirHistorico(Padaria padaria) {
        titulo("HISTÓRICO DE OPERAÇÕES");

        ArrayList<String> historico = padaria.getHistorico();

        if (historico.isEmpty()) {
            System.out.println("Nenhuma operação registrada.");
        } else {
            for (String operacao : historico) {
                System.out.println(operacao);
            }

            linha();
            System.out.println("Total de operações: " + historico.size());
        }

        rodape();
    }

    private static void titulo(String texto) {
        linha();
        System.out.println(" " + texto);
        linha();
    }

    private static void linha() {
        System.out.println("=".repeat(LARGURA));
    }

    private static void rodape() {
        linha();
        System.out.println();
    }

    private static String moeda(double valor) {
        return String.format(
                Locale.forLanguageTag("pt-BR"),
                "R$ %,.2f",
                valor);
    }

    private static String cortar(String texto, int tamanho) {
        if (texto == null) {
            return "";
        }
        if (texto.length() <= tamanho) {
            return texto;
        }
        return texto.substring(0, tamanho - 3) + "...";
    }

    private static String nomeProduto(Produto produto) {
        if (produto == null) {
            return "Nenhum";
        }
        return produto.getNome();
    }
}