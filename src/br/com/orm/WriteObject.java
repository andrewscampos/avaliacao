package br.com.orm;

import java.io.FileWriter;
import java.io.IOException;

public class WriteObject {
	
	private LoadTablesService service;

	public WriteObject(LoadTablesService service) {
		this.service = service;
	}

	public void write(Object object) {
		 String useIndex = service.useIndex(object);
		 try (FileWriter fw = new FileWriter(service.getPathBase(object), true)) {
	            fw.write((useIndex +",") +new Converter().converter(object));
	            fw.write("\n");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

	
}
