package fiap.tds.models;

import fiap.tds.entities.Produto;

import java.util.List;

public class ProdutoDTO {
    public record SearchProductDTO(String order, String dir, int limit, int page,
                                   double total, int count, int totalPages, List<Produto> results){
    }
    public record getProdutos(List<Produto> results){
    }

}
