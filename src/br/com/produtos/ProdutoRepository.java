package br.com.produtos;

import java.util.Collection;

public interface ProdutoRepository {

	Produto update(Produto produtoOld, Produto produto);
	
	Produto save(Produto produto);
	
	Produto delete(Produto produto);
	
	Collection<Produto> list();
	
}
