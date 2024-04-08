package fiap.tds.services;

import fiap.tds.entities.Produto;
import fiap.tds.models.ProdutoDTO;
import fiap.tds.repositories.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;

public class ProdutoService {

    private ProdutoRepository produtoRepository;

    public ProdutoService(){
        produtoRepository = new ProdutoRepository();
    }

    public ProdutoDTO.SearchProductDTO getPodutos(String order,
                                                  String dir,
                                                  int limit,
                                                  int page){
        List<Produto> results = produtoRepository.getProdutos(order, dir, limit, page);
        return new ProdutoDTO.SearchProductDTO(order, dir, limit, page, results.stream()
                .mapToDouble(Produto::getPreco).sum(), results.size(), 0, results);
    }
}
