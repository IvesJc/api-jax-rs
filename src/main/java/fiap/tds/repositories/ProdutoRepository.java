package fiap.tds.repositories;

import fiap.tds.entities.Produto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fiap.tds.Main.LOGGER;

public class ProdutoRepository {

    /**
     * Atributos de conexão com o banco de dados
     * URL_CONNECTION - URL de conexão com o banco de dados
     * USER - Usuário do banco de dados
     * PASSWORD - Senha do banco de dados
     * @since 1.0
     */
    public static final String URL_CONNECTION =
            "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    public static final String USER = "pf1779";
    public static final String PASSWORD = "fiap23";

    /**
     * Construtor padrão
     * @since 1.0
     */
    public ProdutoRepository(){
    }

    /**
     * Método que retorna todos os produtos
     * @return List<Produto> - Lista de produtos
     * @since 1.0
     * @see Produto
     * @see List
     * @see Connection
     * @see DriverManager
     * @see SQLException
     */
    public List<Produto> getProdutos(){
        var lista = new ArrayList<Produto>();
        try (
            var conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
            var stmt = conn.prepareStatement("SELECT * FROM PRODUTO");
            var rs = stmt.executeQuery();){
            while (rs.next()){
                var produto = new Produto();
                produto.setId(rs.getInt("ID"));
                produto.setNome(rs.getString("NOME"));
                produto.setPreco(rs.getDouble("PRECO"));
                lista.add(produto);
            }
            LOGGER.info("Produtos retornados com sucesso");
        }
        catch (SQLException e){
            e.printStackTrace();
            LOGGER.error(MessageFormat.format("Erro ao buscar produtos: {0}", e.getMessage()));
        }
        return lista;
    }

    public List<Produto> getProdutos(String order, String dir, int limit, int page){
        limit = limit == 0 ? 10 : limit;
        var lista = new ArrayList<Produto>();
        try(
                var conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                var stmt = conn.prepareStatement(
                        ("SELECT * FROM PRODUTO ORDER BY %s %s OFFSET %d " +
                                "ROWS FETCH NEXT %d ROWS ONLY")
                        .formatted(
                                Optional.ofNullable(order).orElse("ID"),
                                Optional.ofNullable(dir).orElse("ASC"),
                                (page - 1) * limit,
                                limit)
                )
        ){

            var rs = stmt.executeQuery();
            while (rs.next()){
                var produto = new Produto();
                produto.setId(rs.getInt("ID"));
                produto.setNome(rs.getString("NOME"));
                produto.setPreco(rs.getDouble("PRECO"));
                lista.add(produto);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return lista;
    }

    public Produto getProduto(int id){
        Produto produto = null;
        try (
            var conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
            var stmt = conn.prepareStatement(
                    "SELECT * FROM PRODUTO WHERE ID = ?");){
            stmt.setInt(1,id);
            var rs = stmt.executeQuery();
            if (rs.next()){
                produto = new Produto();
                produto.setId(rs.getInt("ID"));
                produto.setNome(rs.getString("NOME"));
                produto.setPreco(rs.getDouble("PRECO"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return produto;
    }

    public List<Produto> getProdutosByName(String nome){
        var lista = new ArrayList<Produto>();
        try (
                var conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                var stmt = conn.prepareStatement("SELECT * FROM PRODUTO WHERE NOME LIKE ?");
                ) {
            stmt.setString(1, "%" + nome + "%");
            var rs = stmt.executeQuery();
            while (rs.next()) {
                var produto = new Produto();
                produto.setId(rs.getInt("ID"));
                produto.setNome(rs.getString("NOME"));
                produto.setPreco(rs.getDouble("PRECO"));
                lista.add(produto);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return lista;
    }

    public int createProduto(Produto produto){
        try{
            var conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
            var stmt = conn.prepareStatement(
                    "INSERT INTO PRODUTO (NOME, PRECO) VALUES (?,?)");
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            return stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int updateProduto(Produto produto){
        try(
                var conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                var stmt = conn.prepareStatement(
                        "UPDATE PRODUTO SET NOME = ?, PRECO = ? WHERE ID = ?");
                ){
            stmt.setString(1,produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getId());
            return stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteProduto(int id){
        try(
                var conn = DriverManager.getConnection(URL_CONNECTION, USER, PASSWORD);
                var stmt = conn.prepareStatement(
                        "DELETE PRODUTO WHERE ID = ?");
        ){
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
