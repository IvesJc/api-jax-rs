package fiap.tds;

import fiap.tds.entities.Produto;
import fiap.tds.repositories.ProdutoRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    ProdutoRepository produtoRepo = new ProdutoRepository();

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!!!!";
    }

    @GET
    @Path("produto-teste")
    @Produces(MediaType.APPLICATION_JSON)
    public Produto getProdutoTest(){
        return new Produto(1, "Fone de ouvido JBL", 100.0);
    }

    @POST
    @Path("produto-teste")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProdutoTest(Produto produto){
        if(produto.getNome() == null)
            return Response.status(400).entity("Produto não pode ser nulo").build();
        System.out.println(produto.toString());
        return Response.status(201).entity(produto).build();
    }

    @PUT
    @Path("produto=teste/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Produto updateProdutoTest(@PathParam("id") int id, Produto produto){
        return produto;
    }

    @DELETE
    @Path("produto-teste/{id}")
    public Response deleteProdutoTest(@PathParam("id") int id){
        return Response.status(204).build();
    }
}
