package br.com.orm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

public class LoadTablesService {

	private  Map<String, String> clazzs = new HashMap<>();
	
	private  Map<String, String> indexs = new HashMap<>();
	
	private  String pathBase;
	
	private static LoadTablesService instance;
	
	private LoadTablesService(String pathBase) {
		this.pathBase = pathBase;
	}
	
	public static LoadTablesService of(String pathBase, String...packages ) {
		if(instance == null) {
			instance = new LoadTablesService(pathBase);
			try {
				instance.load(packages);
			} catch (IOException e) {
				new RuntimeException(e);
			}
		}
		return instance;
	}
	
	public String getPathBase(Object object) {
		return  clazzs.get(object.getClass().getCanonicalName());
	}
	
	public String getPathBase(String name) {
		return  clazzs.get(name);
	}
	
	public String useIndex(Object object) {
		int index = Integer.parseInt(indexs.get(object.getClass().getCanonicalName()));
		indexs.put(object.getClass().getCanonicalName(), index+1+"");
		return index+1+"";
	}
	
	
	public   void load(String... packages) throws IOException {
		for (String packageName : packages) {

			Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
			Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);

			for (Class<?> clazz : allClasses) {
				if (clazz.isAnnotationPresent(TextTable.class)) {
					TextTable textTable = clazz.getAnnotation(TextTable.class);
					
					if(!new File(pathBase+textTable.name()).exists()) {
						new File(pathBase+textTable.name()+".txt").createNewFile();
						clazzs.put(clazz.getCanonicalName(), pathBase+textTable.name()+".txt");
						Field[] fields = clazz.getDeclaredFields(); 
						for (Field field : fields) {
							if(field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).name().equalsIgnoreCase("id")) {
								indexs.put(clazz.getCanonicalName(), loadIndex(pathBase+textTable.name()+".txt"));
							}
						}
					}
				}
			}
		}
	}

	private String loadIndex(String string) throws FileNotFoundException {
		Scanner in = new Scanner(new File(string));
		int max = 0;
		while(in.hasNext()) {
			String[] split = in.nextLine().split(",");
			String idString = split[0];
			System.out.println(idString);
			int id = Integer.parseInt(idString);
			if(id > max) {
				max = id;
			}
		}
		in.close();
		return max+"";
	}

}
