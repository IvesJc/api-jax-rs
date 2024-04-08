package fiap.tds;

import fiap.tds.entities.Produto;
import fiap.tds.repositories.ProdutoRepository;
import fiap.tds.services.ProdutoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

import static fiap.tds.Main.LOGGER;

@Path("produtos")
public class ProdutoResource {

    ProdutoRepository produtoRepo = new ProdutoRepository();
    ProdutoService produtoService = new ProdutoService();

    /**
     * Método que retorna todos os produtos
     * @param order String - Campo para ordenação ex: nome, preco
     * @param dir String - Direção da ordenação ex: asc, desc
     * @param limit int - Limite de registros
     * @param page int - Página
     * @return List<Produto> - Lista de produtos
     * @apiNote  - Método HTTP GET
     * @author : Leonardo Gasparini
     * @since : 1.0
     * @see Produto
     * @see ProdutoRepository
     * @see List
     * @see Response
     * @see GET
    */
    @GET
    @Produces("application/json")
    public Response getProdutos(@QueryParam("order") String order,
                                @QueryParam("dir") String dir,
                                @QueryParam("limit") int limit,
                                @QueryParam("page") int page){

        LOGGER.info("GET /produtos");
        return Response.ok(produtoService.getPodutos(order,dir, limit, page)).build();
    }



    @GET
    @Path("{id}")
    public Response getProduto(@PathParam("id")int id){
        var produto = produtoRepo.getProduto(id);
        if(produto == null)
            return Response.status(404).entity("Produto não encontrado")
                    .build();
        return Response.status(200).entity(produto).build();
    }

    @POST
    public Response createProduto(Produto produto){
        if(produto == null)
            return Response.status(400).entity("Produto não pode ser nulo").build();
        produtoRepo.createProduto(produto);
        return Response.status(201).entity(produto).build();
    }

    @PUT
    @Path("{id}")
    public Response updateProduto(@PathParam("id") int id, Produto produto){
        return Response.status(204).entity(produto).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteProduto(@PathParam("id") int id){
        return Response.status(204).build();
    }


}
