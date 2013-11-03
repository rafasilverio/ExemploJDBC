package com.livro.capitulo3.crudxml;

import java.sql.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.*;

import com.livro.capitulo3.conexao.HibernateUtil;

public class ContatoCrudXML{
	/**Método salvar*/
	public void Salvar(Contato contato){		
		Session 	sessao    = null;
		Transaction transacao = null;
		
		try {
			sessao 	  = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.save(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel inserir o contato. Erro: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Não foi possivel inserir o contato. Erro: " + e.getMessage());
		} finally{
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Erro ao fechar: " + e.getMessage());
			}
		}
	}
	
	/**Método atualizar*/
	public void Atualizar(Contato contato){
		Session 	sessao    = null;
		Transaction transacao = null;
		
		try {
			sessao 	  = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.update(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel atualizar o contato. Erro: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Não foi possivel atualizar o contato. Erro: " + e.getMessage());
		} finally{
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Erro ao fechar: " + e.getMessage());
			}
		}
	}
	
	/**Método Excluir*/
	public void Excluir(Contato contato){
		Session 	sessao    = null;
		Transaction transacao = null;
		
		try {
			sessao 	  = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.delete(contato);
			transacao.commit();
		} catch (HibernateException e) {
			System.out.println("Não foi possivel excluir o contato. Erro: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Não foi possivel excluir o contato. Erro: " + e.getMessage());
		} finally{
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Erro ao fechar: " + e.getMessage());
			}
		}
	}
	
	/**Método Listar*/
	public List<Contato> Listar(){
		Session 	sessao    	= null;
		Transaction transacao 	= null;
		Query consulta 			= null;
		List<Contato> resultado = null;
		
		try {
			sessao 	  = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta  = sessao.createQuery("from Contato");
			resultado = consulta.list();
			transacao.commit();
			
			return resultado;
		} catch (HibernateException e) {
			System.out.println("Não foi possivel excluir o contato. Erro: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Não foi possivel excluir o contato. Erro: " + e.getMessage());
			
			throw new HibernateException(e);
		} finally{
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Erro ao fechar: " + e.getMessage());
			}
		}
	}
	
	/**Método BuscarContato*/
	public Contato buscaContato(int valor){
		Session 	sessao    	= null;
		Transaction transacao 	= null;
		Query 		consulta	= null;
		Contato 	contato		= null;
		
		try {
			sessao 	  = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta  = sessao.createQuery("FROM Contato WHERE codigo = :parametro");
			consulta.setInteger("parametro", valor);
			contato   = (Contato) consulta.uniqueResult();
			transacao.commit();
			
			return contato;
		} catch (HibernateException e) {
			System.out.println("Não foi possivel buscar o contato. Erro: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar o contato. Erro: " + e.getMessage());
		} finally{
			try {
				sessao.close();
			} catch (Throwable e) {
				System.out.println("Erro ao fechar: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Erro ao fechar: " + e.getMessage());
			}
		}
		return contato;
	}
	
	public static void main(String[] args){
		ContatoCrudXML contatoCrudXML = new ContatoCrudXML();
		
		String[] nomes = {"Fulano", "Beltrano", "Ciclano"};
		String[] fones = {"(47) 2222-1111", "(47) 7777-5555", "(47) 9090-2525"};
		String[] emails = {"fulano@teste.com.br", "beltrano@teste.com.br", "ciclano@teste.com.br"};
		String[] observacoes = {"Novo cliente", "Cliente em dia", "Ligar na quinta"};
		Contato contato = null;
		
		for (int i = 0; i < nomes.length; i++) {
			contato = new Contato();
			contato.setNome(nomes[i]);
			contato.setTelefone(fones[i]);
			contato.setEmail(emails[i]);
			contato.setDataCadastro(new Date(System.currentTimeMillis()));
			contato.setObservacao(observacoes[i]);
			contatoCrudXML.Salvar(contato);
		}
		System.out.println("Total de registros cadastrados: " + contatoCrudXML.Listar().size());
		JOptionPane.showMessageDialog(null, "Total de registros cadastrados: " + contatoCrudXML.Listar().size());
	}
}
