package padaria;

public class Produto {

    private static int proximoCodigo = 1;

    private final int codigo;
    private final String nome;
    private final String categoria;
    private double preco;
    private int quantidade;

    public Produto(String nome, String categoria, double preco, int quantidade) {
        this.codigo = proximoCodigo++;
        this.nome = (nome == null) ? "" : nome.trim();
        this.categoria = (categoria == null || categoria.trim().isEmpty())
                ? "Sem categoria" : categoria.trim();
        this.preco = (preco > 0) ? preco : 0;
        this.quantidade = (quantidade >= 0) ? quantidade : 0;
    }
    // pega o novo código e guarda
    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // caso outra classe chame getQuantidadeEstoque()
    public int getQuantidadeEstoque() {
        return quantidade;
    }

    public boolean setPreco(double novoPreco) {
        if (novoPreco <= 0) {
            return false;
        }
        this.preco = novoPreco;
        return true;
    }

    public boolean adicionarEstoque(int unidades) {
        if (unidades <= 0) {
            return false;
        }
        this.quantidade += unidades;
        return true;
    }

    public boolean removerEstoque(int unidades) {
        if (unidades <= 0 || unidades > this.quantidade) {
            return false;
        }
        this.quantidade -= unidades;
        return true;
    }

    // mantido para compatibilidade com chamadas a retirarEstoque
    public boolean retirarEstoque(int unidades) {
        return removerEstoque(unidades);
    }

    public double valorEmEstoque() {
        return preco * quantidade;
    }

    public boolean estoqueBaixo(int limite) {
        return quantidade < limite;
    }

    public String toString() {
        return "Código: " + codigo
                + " | Nome: " + nome
                + " | Categoria: " + categoria
                + " | Preço: R$ " + String.format(java.util.Locale.forLanguageTag("pt-BR"), "%.2f", preco)
                + " | Estoque: " + quantidade;
    }
}
