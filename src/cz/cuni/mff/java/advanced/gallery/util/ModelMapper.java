package cz.cuni.mff.java.advanced.gallery.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.reflect.TypeUtils;

import cz.cuni.mff.java.advanced.gallery.common.IdentifiedObject;
import cz.cuni.mff.java.advanced.gallery.exceptions.ConversionException;

public class ModelMapper {
	
	public static <T extends IdentifiedObject> T replaceNullsWithStoredData(T toComplete, T toGetData) {
		Class<?> klass = toComplete.getClass(); //Class<T>
		for(Field field : getAllFields(klass)) {
			
			try{
				Method getter = getGetter(klass, field);
				Object value = getter.invoke(toComplete);
				if(value == null || (value instanceof String && ((String)value).isEmpty())) {
					Method setter = getSetter(klass, field);
					setter.invoke(toComplete, getter.invoke(toGetData));
				}
			} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return toComplete;
	}
	
	public static <SRC, DEST> Collection<DEST> convert(Collection<SRC> sourceList, Class<DEST> toClass) throws ConversionException {
		Collection<DEST> resultList = new ArrayList<DEST>();
		for(SRC sourceObject : sourceList) {
			resultList.add(convert(sourceObject, toClass));
		}
		
		return resultList;
	}
	
	public static <SRC, DEST> DEST convert(SRC sourceObject, Class<DEST> toClass) throws ConversionException {

		if (sourceObject == null)
			return null;
		
		try {
			DEST destinationObject = toClass.newInstance();
			List<Field> fields = getAllFields(sourceObject.getClass());
			
			for (Field field : fields) {
				Method sourceGetter = getGetter(sourceObject.getClass(), field);
				Method destinationSetter = getSetter(toClass, field);
	
				if(sourceGetter == null)
					continue;
				
				try {
					Object toSet = sourceGetter.invoke(sourceObject);
					if (destinationSetter != null && toSet != null) { // if setting object or primitive type
						Class<?> parameterType = destinationSetter.getParameterTypes()[0];
						if (TypeUtils.isInstance(toSet, parameterType)) {
							// if toSet is castable to parameter type, set it directly
							destinationSetter.invoke(destinationObject, toSet);
						} else if (toSet != null) {
							// if toSet is an unknown object, translate it via recursion
							destinationSetter.invoke(destinationObject, convert(toSet, parameterType)); // recursion
						}
					}
				} catch (InvocationTargetException e) {
					e.printStackTrace(); // don't throw to continue in mapping other fields
				}
			}
			return destinationObject;
		} catch (IllegalAccessException | IllegalArgumentException | InstantiationException e) {
			e.printStackTrace();
			throw new ConversionException(e);
		}
	}
	
	private static List<Field> getAllFields(Class<?> klass) {
		List<Field> allFields = new ArrayList<Field>();
		Class<?> currentClass = klass;
		while(currentClass != null) {
			Collections.addAll(allFields, currentClass.getDeclaredFields());
			currentClass = currentClass.getSuperclass();
		}
		
		return allFields;
	}
	
	private static List<Method> getAllMethods(Class<?> klass) {
		List<Method> allFields = new ArrayList<Method>();
		Class<?> currentClass = klass;
		while(currentClass != null) {
			Collections.addAll(allFields, currentClass.getDeclaredMethods());
			currentClass = currentClass.getSuperclass();
		}
		
		return allFields;
	}

	private static <C> Method getGetter(Class<C> klass, Field field) {
		Method[] methods = klass.getMethods();
		for (Method method : methods) {
			if (isGetter(method, field.getName()) || (boolean.class == method.getReturnType() && isBooleanGetter(method, field.getName())))
				return method;
		}
		return null;
	}

	private static <C> Method getAdder(Class<C> klass, Field field) {
		Method[] methods = klass.getMethods();
		for (Method method : methods) {
			if (isAdder(method, field.getName()))
				return method;
		}
		return null;
	}

	private static <C> Method getSetter(Class<C> klass, Field field) {
		List<Method> methods = getAllMethods(klass); //event protected or private methods
		for (Method method : methods) {
			if (isSetter(method, field.getName())) {
				method.setAccessible(true);
				return method;
			}
		}
		return null;
	}

	private static boolean isGetter(Method method, String fieldName) {
		return isGetter("get", method, fieldName);
	}

	private static boolean isBooleanGetter(Method method, String fieldName) {
		return isGetter("is", method, fieldName);
	}

	private static boolean isGetter(String prefix, Method method, String fieldName) {
		if (!method.getName().equalsIgnoreCase(prefix + fieldName))
			return false;
		if (method.getParameterTypes().length != 0)
			return false;
		if (void.class.equals(method.getReturnType()))
			return false; // return type is not void
		
		return true;
	}

	private static boolean isAdder(Method method, String fieldName) {
		return isSetter("add", method, fieldName);
	}

	private static boolean isSetter(Method method, String fieldName) {
		return isSetter("set", method, fieldName);
	}

	private static boolean isSetter(String prefix, Method method, String fieldName) {
		if (!method.getName().equalsIgnoreCase((prefix + fieldName)))
			return false;
		if (method.getParameterTypes().length != 1)
			return false;

		return true;
	}
}