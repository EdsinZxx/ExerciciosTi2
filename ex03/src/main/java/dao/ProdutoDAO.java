package dao;

import model.Produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO extends DAO {	
	public ProdutoDAO() {
		super();
		conectar();
	}
	private int maxID = 0;
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Produto produto) {
	    boolean status = false;
	    try {
	        String sql = "INSERT INTO produto (descricao, preco, quantidade, datafabricacao, datavalidade) "
	                   + "VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement st = conexao.prepareStatement(sql);
	        st.setString(1, produto.getDescricao());
	        st.setFloat(2, produto.getPreco());
	        st.setInt(3, produto.getQuantidade());
	        st.setTimestamp(4, Timestamp.valueOf(produto.getDataFabricacao()));
	        st.setDate(5, Date.valueOf(produto.getDataValidade()));
	        st.executeUpdate();
	        st.close();
	        status = true;
	    } catch (SQLException u) {
	        throw new RuntimeException(u);
	    }
	    return status;
	}

	public int getNextId() {
	    int nextId = 0;
	    try {
	        Statement st = conexao.createStatement();
	        ResultSet rs = st.executeQuery("SELECT MAX(id) FROM produto");
	        if (rs.next()) {
	            nextId = rs.getInt(1) + 1;
	        }
	        st.close();
	    } catch (SQLException e) {
	        System.err.println(e.getMessage());
	    }
	    return nextId;
	}


	

	public Produto get(int id){
		Produto produto = null;
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 produto = new Produto(rs.getInt("id"), rs.getString("descricao"), (float)rs.getDouble("preco"), 
	                				   rs.getInt("quantidade"), 
	        			               rs.getTimestamp("datafabricacao").toLocalDateTime(),
	        			               rs.getDate("datavalidade").toLocalDate());
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produto;
	}
	
	
	public List<Produto> get() {
		return get("");
	}

	
	public List<Produto> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Produto> getOrderByDescricao() {
		return get("descricao");		
	}
	
	
	public List<Produto> getOrderByPreco() {
		return get("preco");		
	}
	
	
	private List<Produto> get(String orderBy) {
		List<Produto> produtos = new ArrayList<Produto>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Produto p = new Produto(rs.getInt("id"), rs.getString("descricao"), (float)rs.getDouble("preco"), 
	        			                rs.getInt("quantidade"),
	        			                rs.getTimestamp("datafabricacao").toLocalDateTime(),
	        			                rs.getDate("datavalidade").toLocalDate());
	            produtos.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return produtos;
	}
	
	
	public boolean update(Produto produto) {
		boolean status = false;
		try {  
			String sql = "UPDATE produto SET descricao = '" + produto.getDescricao() + "', "
					   + "preco = " + produto.getPreco() + ", " 
					   + "quantidade = " + produto.getQuantidade() + ","
					   + "datafabricacao = ?, " 
					   + "datavalidade = ? WHERE id = " + produto.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(produto.getDataFabricacao()));
			st.setDate(2, Date.valueOf(produto.getDataValidade()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	private void updateMaxID() {
        try {
            Statement st = conexao.createStatement();
            ResultSet rs = st.executeQuery("SELECT MAX(id) FROM produto");
            if (rs.next()) {
                maxID = rs.getInt(1);
            }
            st.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

	
	public boolean delete(int id) {
        boolean status = false;
        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM produto WHERE id = " + id);
            st.close();

            // Verificar se o ID excluído é o ID máximo atual
            if (id == maxID) {
                updateMaxID(); // Atualizar o ID máximo
            }

            status = true;
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
        return status;
    }

}