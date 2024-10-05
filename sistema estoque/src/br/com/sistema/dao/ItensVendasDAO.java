/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import java.sql.Connection;
import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.ItensVendas;
import br.com.sistema.model.Produtos;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author dacru
 */
public class ItensVendasDAO {
    private Connection conn;
    
    public ItensVendasDAO () {
        this.conn = new ConexaoBanco().pegarConexao();
    }
    
    public void salvar (ItensVendas obj) {
        try {
            String sql = "insert into tb_intensvendas (venda_id, produto_id, qtd, subtotal)"
                    + "values (?,?,?,?)";
            PreparedStatement stmt = conn.prepareCall(sql);
            stmt.setInt(1, obj.getVendas().getId());
            stmt.setInt(2, obj.getProdutos().getId());
            stmt.setInt(3, obj.getQtd());
            stmt.setDouble(4, obj.getSubtotal());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar intens da venda. " + e);
        }
    }
            
  /*  public List<ItensVendas>listaIntens(int venda_id) {
        
        try {
            List<ItensVendas>lista = new ArrayList<>(); 
            String sql = "select p.id, p.descricao, i.qtd, p.preco, i.subtotal from tb_itensvendas as i inner"
                    + " join tb_produtos as p on(i.produto_id=p.id) where i.venda_id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, venda_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItensVendas item =  new ItensVendas();
                Produtos p = new Produtos();
                p.setId(rs.getInt("p.id"));
                item.setProdutos(p);
                p.setDescricao(rs.getString("p.descricao"));
                item.setProdutos(p);
                item.setQtd(rs.getInt("id.qtd"));
                p.setPreco(rs.getDouble("p.preco"));
                item.setProdutos(p);
                item.setSubtotal(rs.getInt("i.subtotal"));
                lista.add(item);
            }
            return lista;
                   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro com o Banco de dados " + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro desconhecido: " + e);
        }
        return null;
}
    */
    public List<ItensVendas> listaIntens(int venda_id) {
    List<ItensVendas> lista = new ArrayList<>();
    
    String sql = "SELECT p.id, p.descricao, i.qtd, p.preco, i.subtotal " +
                 "FROM tb_itensvendas AS i " +
                 "INNER JOIN tb_produtos AS p ON i.produto_id = p.id " +
                 "WHERE i.venda_id = ?";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, venda_id);
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ItensVendas item = new ItensVendas();
                Produtos p = new Produtos();
                
                // Preenchendo o objeto Produtos
                p.setId(rs.getInt("p.id"));
                p.setDescricao(rs.getString("p.descricao"));
                p.setPreco(rs.getDouble("p.preco"));
                
                // Atribuindo o objeto Produtos a ItensVendas
                item.setProdutos(p);
                item.setQtd(rs.getInt("i.qtd"));
                item.setSubtotal(rs.getDouble("i.subtotal"));
                
                lista.add(item);
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro com o Banco de dados: " + e.getMessage());
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Erro desconhecido: " + e.getMessage());
    }
    
    return lista;
}

}
