package main;

import gui.RoboScripterWindow;
import script.ScriptDB;

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
	
}
