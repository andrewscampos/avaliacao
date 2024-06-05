package br.com.orm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Converter {

	public String converter(Object object) {
        if (!object.getClass().isAnnotationPresent(TextTable.class)) {
            throw new IllegalArgumentException("A classe deve estar anotada com @TextTable");
        }

        List<Field> fields = getAnnotatedFields(object);

        fields = sortFields(fields);

        return buildCsvString(object, fields);
    }

    private List<Field> getAnnotatedFields(Object object) {
        List<Field> annotatedFields = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                annotatedFields.add(field);
            }
        }

        return annotatedFields;
    }

     

    private List<Field> sortFields(List<Field> fields) {
        return fields.stream()
                .sorted(Comparator.comparing(field -> field.getAnnotation(Column.class).name(), 
                          java.text.Collator.getInstance()))
                .collect(Collectors.toList());
    }

    private String buildCsvString(Object object, List<Field> fields) {
        List<String> values = fields.stream()
                .map(field -> getFieldValue(object, field))
                .collect(Collectors.toList());

        return String.join(",", values.stream().filter(a -> a != null).toList());
    }

    private String getFieldValue(Object object, Field field) {
        try {
            field.setAccessible(true);
            return field.get(object) == null ? null : field.get(object).toString();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public  <T> T createObject(Class<T> clazz, String line) throws Exception {
    	Field[] fields = clazz.getDeclaredFields();
    	List<Field> sortFields = sortFields(Arrays.asList(fields));
        Object[] fieldNames = sortFields.stream().map(a -> a.getAnnotation(Column.class).name()).toArray();
        String[] values = line.split(",");

        if (fieldNames.length != values.length) {
            throw new IllegalArgumentException("Número de campos no cabeçalho não corresponde ao número de valores na linha CSV.");
        }

        Map<String, String> fieldValueMap = new HashMap<>();
        for (int i = 0; i < fieldNames.length; i++) {
            fieldValueMap.put(fieldNames[i].toString().trim(), values[i].trim());
        }

        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> targetConstructor = null;
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterCount() == fieldNames.length) {
                targetConstructor = constructor;
                break;
            }
        }

        if (targetConstructor == null) {
            throw new NoSuchMethodException("Nenhum construtor encontrado com o número correto de parâmetros.");
        }

        Object[] constructorArgs = new Object[fieldNames.length];
        Class<?>[] parameterTypes = targetConstructor.getParameterTypes();
        
        for (int i = 0; i < parameterTypes.length; i++) {
            String fieldName = fieldNames[i].toString();
            String value = fieldValueMap.get(fieldName);
            
            if (parameterTypes[i] == int.class || parameterTypes[i] == Integer.class) {
                constructorArgs[i] = Integer.parseInt(value);
            } else if (parameterTypes[i] == double.class || parameterTypes[i] == Double.class) {
                constructorArgs[i] = Double.parseDouble(value);
            } else if (parameterTypes[i] == String.class) {
                constructorArgs[i] = value;
            } else {
                throw new IllegalArgumentException("Tipo de parâmetro não suportado: " + parameterTypes[i].getName());
            }
        }

        return clazz.cast(targetConstructor.newInstance(constructorArgs));
    }
    
    
}
