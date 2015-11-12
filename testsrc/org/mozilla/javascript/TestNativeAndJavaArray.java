package org.mozilla.javascript;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestNativeAndJavaArray {

	@Test
	public void testSetup() {
		Map<String, Object> scriptResult = executeScript("var a = 1+2; resultContainer.put('a', a);");
		assertEquals(3.0, scriptResult.get("a"));
	}
	
	@Test
	public void testConcatJava2NativeArray() {
		Map<String, Object> scriptResult = executeScript("var javaArray = javaArrayFactory.createIntArray();\n"+
				"var jsArray = [11, 12, 13];\n"+
				"var javaCjs = javaArray.concat(jsArray);\n"+
				"var jsCjava = jsArray.concat(javaArray);\n"+
				"resultContainer.put('javaCjsLength', javaCjs.length);\n"+
				"resultContainer.put('jsCjavaLength', jsCjava.length);\n"+
				"resultContainer.put('javaCjs2', javaCjs[2]);\n"+
				"resultContainer.put('javaCjs5', javaCjs[5]);\n"+
				"resultContainer.put('jsCjava2', jsCjava[2]);\n"+
				"resultContainer.put('jsCjava5', jsCjava[5]);\n"
				);
		
		assertEquals(7.0, scriptResult.get("javaCjsLength"));
		assertEquals(7.0, scriptResult.get("jsCjavaLength"));
		assertEquals(3.0, scriptResult.get("javaCjs2"));
		assertEquals(13.0, scriptResult.get("jsCjava2"));
		assertEquals(12.0, scriptResult.get("javaCjs5"));
		assertEquals(3.0, scriptResult.get("jsCjava5"));

	}
	
	private Map<String, Object> executeScript(String script) {
		final ContextFactory contextFactory = ContextFactory.getGlobal();
		final Context context = contextFactory.enter();

		try {
			Scriptable scope = new ImporterTopLevel(context);

			ScriptableObject.putProperty(scope, "javaArrayFactory",
					new JavaArrayFactory());
			HashMap<String, Object> result = new HashMap<String, Object>();
			ScriptableObject.putProperty(scope, "resultContainer",
					result);

			final BufferedReader reader = new BufferedReader(new StringReader(
					script));
			context.evaluateReader(scope, reader, "test", 1, null);

			return result;
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
