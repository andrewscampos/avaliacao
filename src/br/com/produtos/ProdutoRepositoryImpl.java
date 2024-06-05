package br.com.produtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.orm.Converter;
import br.com.orm.ReadObject;
import br.com.orm.RemoveObject;
import br.com.orm.UpdateObject;
import br.com.orm.WriteObject;

public class ProdutoRepositoryImpl implements ProdutoRepository {

	private WriteObject writeObject;
	private UpdateObject updateObject;
	private RemoveObject removeObject;
	private ReadObject readObject;

	public ProdutoRepositoryImpl(WriteObject writeObject, UpdateObject updateObject, RemoveObject removeObject,
			ReadObject readObject) {
		this.writeObject = writeObject;
		this.updateObject = updateObject;
		this.removeObject = removeObject;
		this.readObject = readObject;

	}

	@Override
	public Produto save(Produto produto) {
		writeObject.write(produto);
		return produto;
	}

	@Override
	public Produto delete(Produto produto) {
		removeObject.remove(produto);
		return produto;
	}

	@Override
	public Collection<Produto> list() {
		List<String> list = readObject.read(Produto.class);
		List<Produto> produtos = new ArrayList<>();
		for (String line : list) {

			try {
				Produto produto = new Converter().createObject(Produto.class, line);
				produtos.add(produto);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return produtos;
	}

	@Override
	public Produto update(Produto produtoOld, Produto produto) {
		updateObject.update(produtoOld, produto);
		return produto;
	}

}
