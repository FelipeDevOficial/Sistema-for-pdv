/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConexaoBanco;
import br.com.sistema.model.Clientes;
import br.com.sistema.model.vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author dacru
 */
public class vendasDAO {
    private Connection conn;
    
    public vendasDAO () {
    this.conn = new ConexaoBanco().pegarConexao();
    }
    
    public void salvar (vendas obj) {
        try {;
            String sql = "insert into tb_vendas (cliente_id, data_venda, total_venda, observacoes)"
                    + "values (?,?,?,?)";            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, obj.getClientes().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setDouble(3, obj.getTotal_venda());
            stmt.setString(4, obj.getObservacoes());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Venda realizada com sucesso.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar venda." + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro:  " + e);
        }
    }
    public int retornaUltimoIdVenda() {
        int ultimoId = 0;
        try {            
        //    int ultimoId;
            String sql = "select max(id) id from tb_vendas";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while ( rs.next() ) {
                vendas v = new vendas();
                v.setId(rs.getInt("id"));
                ultimoId = v.getId();                        
                
            }
          //  return ultimoId;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar a útima venda. " + e);
        }
        return ultimoId;
    }
    
    public List<vendas> HistoricoVendas (LocalDate data_inicio, LocalDate data_fim) {
        try {
            List<vendas> lista = new ArrayList<>();
            String sql = "select v.id, c.nome, date_format(v.data_venda, '%d/%m/%Y')"
                    + " as data_formatada, v.total_venda, v.observacoes from tb_vendas as v inner join"
                    + " tb_clientes as c on(v.cliente_id=c.id) where v.data_venda between ? and ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, data_inicio.toString());
            stmt.setString(2, data_fim.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vendas v = new vendas();
                Clientes c = new Clientes();
                v.setId(rs.getInt("v.id"));
                c.setNome(rs.getString("c.nome"));
                v.setClientes(c);
                v.setData_venda(rs.getString("data_formatada"));
                v.setTotal_venda(rs.getDouble("v.total_venda"));
                v.setObservacoes(rs.getString("v.observacoes"));
                lista.add(v);
                
            }
            return lista;
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar histórico de vendas. " + e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar o histótico de vendas. " + e);
        }
        return null;
    }
    
}
