package aplicacao;

import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TesteFX {
	public static void main(String[] args) {
		ScriptEngineManager manager = new ScriptEngineManager();

		JavaFXScriptEngine fxEngine = (JavaFXScriptEngine) manager.getEngineByName("javafx");

		try {

			String param = "JavaFX object created in Java";

			String script = String.format("MyJavaFXClass {property: \"%s\"}", param);

			Object o = fxEngine.eval(script);

			fxEngine.invokeMethod(o, "printProperty");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main2(String[] args) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByExtension("fx");
			InputStreamReader reader = new InputStreamReader(
					CalculatorLauncher.class.getResourceAsStream("Calculator.fx"));
			engine.eval(reader);
		} catch (ScriptException ex) {
			ex.printStackTrace();
		}
	}
}
