package com.livro.capitulo3.conexao;

import javax.swing.JOptionPane;

import org.hibernate.Session;

public class ConectaHibernateMySQL {
	public static void main(String[] args) {
		Session sessao = null;
		
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			
			System.out.println("Conectou!");
			JOptionPane.showMessageDialog(null, "Conectou!");
		} finally {
			sessao.close();
		}
	}

}
