package br.com.produtos;

import br.com.orm.Column;
import br.com.orm.TextTable;

@TextTable(name = "produtos")
public class Produto {
	private @Column(name = "id") Integer id;
	private @Column(name = "nome") String nome;
	private @Column(name = "valor") Double valor;
	private @Column(name = "quantidade") Integer quantidade;

	public Produto(Integer id) {
		this.id = id;
	}

	public Produto(String nome, double valor, int quantidade) {

		if (nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome não pode ser vazio.");
		}
		if (valor < 0) {
			throw new IllegalArgumentException("Valor não pode ser negativo.");
		}
		if (quantidade < 0) {
			throw new IllegalArgumentException("Quantidade não pode ser negativa.");
		}

		this.nome = nome;
		this.valor = valor;
		this.quantidade = quantidade;
	}
	
	public Produto(int id, String nome, int quantidade, double valor) {
		this(nome,valor,quantidade);
		if (id <= 0) {
			throw new IllegalArgumentException("ID deve ser maior que zero.");
		}

		this.id = id;
		 
	}

	@Override
	public String toString() {
		return String.format("| %-3d | %-20s | %-10.2f | %-10d |", id, nome, valor, quantidade);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
