package fiap.tds.entities;

/**
 * Classe que representa um produto
 * @since 1.0
 * @author Leonardo Gasparini
 */
public class Produto {
    private int id;
    private String nome;
    private double preco;

    /**
     * Construtor vazio
     */
    public Produto(){

    }

    /**
     * Construtor que recebe todos os atributos
     * @param id int - id do produto
     * @param nome String - nome do produto
     * @param preco double - preço do produto
     */
    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * Método que retorna o id do produto
     * @return int - id do produto
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';
    }
}
