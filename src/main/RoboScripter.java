package main;

import gui.RoboScripterWindow;
import script.Script;
import script.ScriptDB;
import script.compiler.ScriptCompiler;

public class RoboScripter {
	
	public static final int BOARD_SIZE = 10000;
	
	private final ScriptDB scriptDB;
	private final RoboScripterWindow window;
	
	public RoboScripter() {
		scriptDB = new ScriptDB();
		window = new RoboScripterWindow(this);
	}
	
	public ScriptDB getScriptDB() {
		return scriptDB;
	}
	
	public RoboScripterWindow getWindow() {
		return window;
	}
	
	public ScriptCompiler compile(Script script) {
		ScriptCompiler compiler = new ScriptCompiler(script);
		compiler.compile();
		return compiler;
	}
	
}
