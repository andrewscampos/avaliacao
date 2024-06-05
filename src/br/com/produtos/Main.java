package br.com.produtos;

import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;

import br.com.orm.LoadTablesService;
import br.com.orm.ReadObject;
import br.com.orm.RemoveObject;
import br.com.orm.UpdateObject;
import br.com.orm.WriteObject;

// DAVA PRA MELHORAR MAIS FIQUEI COM PREGUIÇA
public class Main {

	static Scanner scanner = new Scanner(System.in);

	public static void exibirMenu() throws IOException {

		System.out.println("\n1 - Listar produtos");
		System.out.println("2 - Cadastrar novo produto");
		System.out.println("3 - Editar produto");
		System.out.println("4 - Excluir produto");
		System.out.println("0 - Sair");
		System.out.print("Escolha uma opção: ");
	}

	public static void main(String[] args) throws IOException {
		int opcao;
		do {
			LoadTablesService service = LoadTablesService.of("/home/andrews/rd/", "br.com.produtos");
			UpdateObject updateObject = new UpdateObject(new WriteObject(service), new RemoveObject(service));
			ProdutoService produtoService = new ProdutoService(new ProdutoRepositoryImpl( new WriteObject(service), updateObject , new RemoveObject(service), new ReadObject(service)));
			exibirMenu();
			opcao = Integer.parseInt(scanner.nextLine());
			switch (opcao) {
			case 1:
				listarProdutos(produtoService);
				break;
			case 2:
				cadastrarProduto(produtoService);
				break;
			case 3:
				editarProduto(produtoService);
				break;
			case 4:
				excluirProduto(produtoService);
				break;
			case 0:
				System.out.println("Saindo...");
				break;
			default:
				System.out.println("Opção inválida! Tente novamente.");
			}
		} while (opcao != 0);
	}

	public static void listarProdutos(ProdutoService produtoService) {
		 
		Collection<Produto> produtos = produtoService.list();
		if (produtos.isEmpty()) {
			System.out.println("Nenhum produto cadastrado.");
		} else {
			System.out.println("------+----------------------+------------+-------------");
			System.out.println("| ID  | Nome do produto      | Valor      | Quantidade |");
			System.out.println("------+----------------------+------------+-------------");
			for (Produto produto : produtos) {
				System.out.println(produto);
				System.out.println("------+----------------------+------------+-------------");
			}

		}
	}

	public static void cadastrarProduto(ProdutoService produtoService) {
		System.out.print("Digite o nome do produto: ");
		String nome = scanner.nextLine();
		System.out.print("Digite o valor do produto: ");
		double valor = Double.parseDouble(scanner.nextLine());
		System.out.print("Digite a quantidade do produto: ");
		int quantidade = Integer.parseInt(scanner.nextLine());

		Produto produto = new Produto(nome, valor, quantidade);
		produtoService.save(produto);
		System.out.println("Produto salvo!");
	}

	public static void editarProduto(ProdutoService produtoService) {
		System.out.print("Digite o ID do produto: ");
		int id = Integer.parseInt(scanner.nextLine());

		System.out.print("Digite o nome do produto: ");
		String nome = scanner.nextLine();
		System.out.print("Digite o valor do produto: ");
		String valor = scanner.nextLine();
		System.out.print("Digite a quantidade do produto: ");
		String quantidade = scanner.nextLine();
		System.out.println("Produto editado com sucesso!");
		Produto produto = new Produto(id);
		Produto produto2 = new Produto(id, nome, Integer.parseInt(quantidade), Double.parseDouble(valor));
		produtoService.update(produto, produto2);

	}

	public static void excluirProduto(ProdutoService produtoService) {
		System.out.print("Digite o ID do produto: ");
		int id = Integer.parseInt(scanner.nextLine());
		Produto produto = new Produto(id);
		produtoService.delete(produto);
		System.out.println("Produto excluído com sucesso!");
	}

}
