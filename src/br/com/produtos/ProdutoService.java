package br.com.produtos;

import java.util.Collection;

public class ProdutoService {
	
	private ProdutoRepository produtoRepository;

	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}

	public void delete(Produto produto) {
		produtoRepository.delete(produto);
	}

	public Collection<Produto> list() {
		return produtoRepository.list();
	}

	public Produto update(Produto old, Produto produto ) {
		return produtoRepository.update(old,produto);
	}
}
