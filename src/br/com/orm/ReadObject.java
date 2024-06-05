package br.com.orm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadObject {
	
	private LoadTablesService service;

	public ReadObject(LoadTablesService service) {
		this.service = service;
	}

	public List<String> read(Class<?> clazz) {
		try {
			List<String> list = new ArrayList<>();
			Scanner scanner = new Scanner(new File(service.getPathBase(clazz.getCanonicalName())));
			while (scanner.hasNext()) {
				list.add(scanner.nextLine());
			}
			return list;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	
}
