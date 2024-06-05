package br.com.orm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RemoveObject {

	private LoadTablesService service;

	public RemoveObject(LoadTablesService service) {
		this.service = service;
	}

	public void remove(Object object) {
		List<String> list = new ArrayList<>();
		Scanner in;
		try {
			in = new Scanner(new File(service.getPathBase(object)));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		while (in.hasNext()) {
			String line = in.nextLine();
			if (!object.toString().split("|")[2].equals(line.split(",")[line.split(",").length -1])) {
				list.add(line);
			}
		}

		try (FileWriter fw = new FileWriter(service.getPathBase(object), false)) {
			for (String line : list) {
				fw.write(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
