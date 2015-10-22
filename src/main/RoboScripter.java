package main;

import script.ScriptDB;

public class RoboScripter {
	
	private final ScriptDB scriptDB;
	
	public RoboScripter() {
		scriptDB = new ScriptDB();
	}
	
	public ScriptDB getScriptDB() {
		return scriptDB;
	}
	
}
