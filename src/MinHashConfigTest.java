import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;

@SuppressWarnings("unchecked")
public class MinHashConfigTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.out.println("the config xml path not specified.");
		} else {
			run(args[0]);
		}
	}

	private static void run(String xmlConfigpath) {
		XMLConfiguration xmlConfig;

		try {
			xmlConfig = new XMLConfiguration(xmlConfigpath);

			Object tempObject = xmlConfig.getProperty("step.class");
			if (tempObject instanceof List) {
				List<String> classList = (List<String>) tempObject;
				for (int i = 0; i < classList.size(); i++) {
					String className = (String) classList.get(i);
					invoke(className, xmlConfig, i);
				}
			} else {
				invoke((String) tempObject, xmlConfig, 0);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void invoke(String className, XMLConfiguration xmlConfig,
			int index) throws Exception {
		Class<?> c = Class.forName(className);
		Object o = generateObject(c, xmlConfig, index);

		setField(o, c, xmlConfig, index);

		invokeMethod(o, c, xmlConfig, index);

		sleep(xmlConfig, index);
	}

	private static void sleep(XMLConfiguration xmlConfig, int index)
			throws InterruptedException {
		String sleepTime = xmlConfig.getString("step(" + index + ").sleep");
		if (sleepTime != null && !"".equals(sleepTime.trim())) {
			Thread.sleep(Integer.parseInt(sleepTime));
		}
	}

	private static void invokeMethod(Object o, Class<?> c,
			XMLConfiguration xmlConfig, int index) throws Exception {
		Object tempMethodName = xmlConfig.getList("step(" + index
				+ ").method.name");
		if (tempMethodName instanceof List) {
			List<String> methodNameList = (List<String>) tempMethodName;
			for (int i = 0; i < methodNameList.size(); i++) {
				String methodName = (String) methodNameList.get(i);
				List methodParamValueList = xmlConfig.getList("step(" + index
						+ ").method(" + i + ").param");

				Method method = null;
				Object[] parameter = null;
				if (methodParamValueList == null
						|| methodParamValueList.size() == 0) {
					method = c.getMethod(methodName, new Class[0]);
				} else {
					List methodParamTypeList = xmlConfig.getList("step("
							+ index + ").method(" + i + ").param[@type]");
					Class[] classes = generateClassArray(methodParamTypeList);
					method = c.getMethod(methodName, classes);
					parameter = generateObjectArray(methodParamValueList,
							classes);
				}
				method.invoke(o, parameter);
			}
		} else {
			String methodName = (String) tempMethodName;
			List methodParamValueList = xmlConfig.getList("step(" + index
					+ ").method.param");

			Method method = null;
			Object[] parameter = null;
			if (methodParamValueList == null
					|| methodParamValueList.size() == 0) {
				method = c.getMethod(methodName, new Class[0]);
			} else {
				List methodParamTypeList = xmlConfig.getList("step(" + index
						+ ").method.param[@type]");
				Class[] classes = generateClassArray(methodParamTypeList);
				method = c.getMethod(methodName, classes);
				parameter = generateObjectArray(methodParamValueList, classes);
			}
			method.invoke(o, parameter);
		}
	}

	private static void setField(Object o, Class<?> c,
			XMLConfiguration xmlConfig, int index) throws Exception {
		Object tempFieldName = xmlConfig.getList("step(" + index
				+ ").field.name");
		if (tempFieldName instanceof List) {
			List<String> fieldNameList = (List<String>) tempFieldName;
			for (int i = 0; i < fieldNameList.size(); i++) {
				String fieldName = (String) fieldNameList.get(i);
				String[] fieldValueArray = xmlConfig.getStringArray("step("
						+ index + ").field(" + i + ").value");
				// String fieldValue = xmlConfig.getString("step(" + index
				// + ").field(" + i + ").value");
				String fieldValue = generateFieldValue(fieldValueArray);

				boolean isStatic = false;
				if (xmlConfig.containsKey("step(" + index + ").field(" + i
						+ ")[@static]")) {
					isStatic = xmlConfig.getBoolean("step(" + index
							+ ").field(" + i + ")[@static]");
				}
				setFieldValue(o, c, fieldName, fieldValue, isStatic);
			}
		} else {
			String fieldName = (String) tempFieldName;
			String[] fieldValueArray = xmlConfig.getStringArray("step(" + index
					+ ").field.value");
//			String fieldValue = xmlConfig.getString("step(" + index
//					+ ").field.value");
			String fieldValue = generateFieldValue(fieldValueArray);
			boolean isStatic = false;
			if (xmlConfig.containsKey("step(" + index + ").field[@static]")) {
				isStatic = xmlConfig.getBoolean("step(" + index
						+ ").field[@static]");
			}
			setFieldValue(o, c, fieldName, fieldValue, isStatic);
		}
	}

	private static String generateFieldValue(String[] fieldValueArray) {
		StringBuilder sb = new StringBuilder();
		if (fieldValueArray != null) {
			int length = fieldValueArray.length;
			for (int i = 0; i < length; i++) {
				String string = fieldValueArray[i];
				sb.append(string);
				if (i < (length - 1)) {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}

	private static void setFieldValue(Object o, Class<?> c, String fieldName,
			String fieldValue, boolean isStatic) throws Exception {
		if (isStatic) {
			Field field = c.getField(fieldName);
			field.set(o, castObject(field.getType(), fieldValue));
		} else {
			String methodName = "set"
					+ Character.toUpperCase(fieldName.charAt(0))
					+ fieldName.substring(1);
			Method setMethod = c.getMethod(methodName, String.class);
			setMethod.invoke(o, fieldValue);
		}

	}

	private static Object generateObject(Class<?> c,
			XMLConfiguration xmlConfig, int index) throws Exception {
		List constructorTypeList = xmlConfig.getList("step(" + index
				+ ").constructor[@type]");

		if (constructorTypeList == null || constructorTypeList.size() == 0) {
			return c.newInstance();
		}

		List constructorValueList = xmlConfig.getList("step(" + index
				+ ").constructor");
		Class[] constructorParamTypes = generateClassArray(constructorTypeList);
		Object[] constructorParamValues = generateObjectArray(
				constructorValueList, constructorParamTypes);
		Constructor constructor = c.getConstructor(constructorParamTypes);
		return constructor.newInstance(constructorParamValues);
	}

	private static Class[] generateClassArray(List<String> typeList)
			throws ClassNotFoundException {
		Class[] classes = new Class[typeList.size()];
		for (int i = 0; i < typeList.size(); i++) {
			String className = (String) typeList.get(i);
			classes[i] = Class.forName(className);
		}
		return classes;
	}

	private static Object[] generateObjectArray(List<String> valueList,
			Class[] classes) throws Exception {
		if (classes.length > valueList.size()) {
			throw new Exception("parameters value not enough.");
		}
		Object[] strArray = new Object[classes.length];
		for (int i = 0; i < classes.length; i++) {
			strArray[i] = (String) valueList.get(i);
			strArray[i] = castObject(classes[i], strArray[i]);
		}
		return strArray;
	}

	private static Object castObject(Class classType, Object object) {
		String value = (String) object;

		if (classType == Integer.class) {
			return Integer.valueOf(value);
		} else if (classType == int.class) {
			return Integer.parseInt(value);
		} else if (classType == Long.class) {
			return Long.valueOf(value);
		} else if (classType == long.class) {
			return Long.parseLong(value);
		} else if (classType == boolean.class) {
			return Boolean.parseBoolean(value);
		} else if (classType == Boolean.class) {
			return Boolean.valueOf(value);
		} else {
			return object;
		}
	}
}
