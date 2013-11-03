package com.livro.capitulo3.crudjdbc;

/* @author rafael silverio da silva */

/**import*/
import java.sql.Date;
import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;

public class ContatoCrudJDBC {

	public void salvar(Contato contato){
		Connection conexao = this.geraConexao();
		PreparedStatement insereSt = null;
		
		String sql = "INSERT INTO contato(nome, telefone, email, dt_cad, obs)"
									+ "values(?, ?, ?, ?, ?)";
		
		try {
			/**faz a preparação da conexão*/
			insereSt = conexao.prepareStatement(sql);
			
			/**Seta os campos de acordo com os values do campo SQL*/
			insereSt.setString(1, contato.getNome());
			insereSt.setString(2, contato.getTelefone());
			insereSt.setString(3, contato.getEmail());
			insereSt.setDate(4, contato.getDataCadastro());
			insereSt.setString(5, contato.getObservacao());
			
			/**Executa o update de acordo com a SQL*/
			insereSt.executeUpdate();
		} catch (SQLException e) {
			/**exibe caso dê erro*/
			System.out.println("Erro SQL: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro SQL: " + e.getMessage());
		} finally{
			try{
				/**fecha as conexoes*/
				insereSt.close();
				conexao.close();
			}catch(Throwable e){
				/**exibe caso dê erro*/
				System.out.println("Erro SQL ao fechar a inserção: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Erro SQL ao fechar a inserção: " + e.getMessage());
			}
		}
	}
	public void atualizar(Contato contato){
		
		Connection conexao = this.geraConexao();
		PreparedStatement atualizaSt = null;

		//Aqui não atualizamos o campo data de cadastro
		String sql = "UPDATE CONTATO SET nome=?, telefone=?, email=?, obs=? WHERE codigo=?";

		try {
			atualizaSt = conexao.prepareStatement(sql);
			atualizaSt.setString(1, contato.getNome());
			atualizaSt.setString(2, contato.getTelefone());
			atualizaSt.setString(3, contato.getEmail());
			atualizaSt.setString(4, contato.getObservacao());
			atualizaSt.setInt(5, contato.getId().intValue());
			atualizaSt.executeUpdate();
		} catch (SQLException e) {
			/**exibe caso dê erro*/
			System.out.println("Erro SQL: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro SQL: " + e.getMessage());
		} finally {
			try {
				atualizaSt.close();
				conexao.close();
			} catch (Throwable e) {
				/**exibe caso dê erro*/
				System.out.println("Erro SQL ao fechar a atualização: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Erro SQL ao fechar a atualização: " + e.getMessage());
			}
		}
	}
	
	public void excluir(Contato contato){
		
		Connection conexao = this.geraConexao();
		PreparedStatement excluiSt = null;

		String sql = "DELETE FROM contato WHERE codigo = ?";

		try {
			excluiSt = conexao.prepareStatement(sql);
			excluiSt.setInt(1, contato.getId().intValue());
			excluiSt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Erro ao excluir contato. Mensagem: " + e.getMessage());
		} finally {
			try {
				excluiSt.close();
				conexao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operações de exclusão. Mensagem: " + e.getMessage());
			}
		}
	}
	
	public List<Contato> listar(){
		Connection conexao = this.geraConexao();
		List<Contato> contatos = new ArrayList<Contato>();
		
		Statement consulta  = null;
		ResultSet resultado = null;
		Contato contato 	= null;
		
		String sql = "SELECT * from contato";
		
		try {
			consulta  = conexao.createStatement();
			resultado = consulta.executeQuery(sql);
			
			while(resultado.next()){
				contato = new Contato();
				
				contato.setId(new Integer(resultado.getInt("codigo")));
				contato.setNome(resultado.getString("nome"));
				contato.setTelefone(resultado.getString("telefone"));
				contato.setEmail(resultado.getString("email"));
				contato.setDataCadastro(resultado.getDate("dt_cad"));
				contato.setObservacao(resultado.getString("obs"));
				
				contatos.add(contato);
			}
		} catch (SQLException e) {
			/**exibe caso dê erro*/
			System.out.println("Erro SQL: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro SQL: " + e.getMessage());
		} finally{
			try{
				/**fecha as conexoes*/
				consulta.close();
				conexao.close();
			}catch(Throwable e){
				/**exibe caso dê erro*/
				System.out.println("Erro SQL ao fechar a inserção: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Erro SQL ao fechar a inserção: " + e.getMessage());
			}
		}
		return contatos;
	}
	public Contato buscaContato(int valor){
		
		Connection conexao = this.geraConexao();
		PreparedStatement consulta = null;
		ResultSet resultado = null;
		Contato contato = null;

		String sql = "SELECT * FROM contato WHERE codigo = ?";

		try {
			consulta = conexao.prepareStatement(sql);
			consulta.setInt(1, valor);
			resultado = consulta.executeQuery();

			if (resultado.next()) {
				contato = new Contato();
				contato.setId(new Integer(resultado.getInt("codigo")));
				contato.setNome(resultado.getString("nome"));
				contato.setTelefone(resultado.getString("telefone"));
				contato.setEmail(resultado.getString("email"));
				contato.setDataCadastro(resultado.getDate("dt_cad"));
				contato.setObservacao(resultado.getString("obs"));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao buscar código do contato. Mensagem: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao buscar código do contato. Mensagem: " + e.getMessage());
		} finally {
			try {
				consulta.close();
				resultado.close();
				conexao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar operações de consulta. Mensagem: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Erro ao fechar operações de consulta. Mensagem: " + e.getMessage());
			}
		}
		return contato;
	}
	
	public Connection geraConexao(){
		Connection conexao = null;

		try {
			// Registrando a classe JDBC no sistema em tempo de execução
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost/agenda";
			String usuario = "root";
			String senha = "123456";
			conexao = DriverManager.getConnection(url, usuario, senha);
		} catch (ClassNotFoundException e) {
			System.out.println("Classe não encontrada. Erro: " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro de SQL. Erro: " + e.getMessage());
		}
		return conexao;
	}
	
	public static void main(String[] args){
		ContatoCrudJDBC contatoCRUDJDBC = new ContatoCrudJDBC();

		// Criando um primeiro contato
		Contato beltrano = new Contato();
		beltrano.setNome("Beltrano Solar");
		beltrano.setTelefone("(47) 5555-3333");
		beltrano.setEmail("beltrano@teste.com.br");
		beltrano.setDataCadastro(new Date(System.currentTimeMillis()));
		beltrano.setObservacao("Novo cliente");
		contatoCRUDJDBC.salvar(beltrano);

		//Criando um segundo contato
		Contato fulano = new Contato();
		fulano.setNome("Fulano Lunar");
		fulano.setTelefone("(47) 7777-2222");
		fulano.setEmail("fulano@teste.com.br");
		fulano.setDataCadastro(new Date(System.currentTimeMillis()));
		fulano.setObservacao("Novo contato - possível cliente");
		contatoCRUDJDBC.salvar(fulano);

		System.out.println("Contatos cadastrados: " + contatoCRUDJDBC.listar().size());
		JOptionPane.showMessageDialog(null, "Contatos cadastrados: " + contatoCRUDJDBC.listar().size());
	}
	
}
