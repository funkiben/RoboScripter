package main;

import script.Script;
import script.instruction.TestInstruction;

public class Main {

	public static RoboScripter scripter;

	public static void main(String[] args) {
		scripter = new RoboScripter();

		Script script = new Script("test");

		int cx = RoboScripter.BOARD_SIZE / 2;
		int cy = RoboScripter.BOARD_SIZE / 2;

		script.addInstruction(new TestInstruction(script, cx, cy));
		script.getInstructions().get(0).setStart(cx, cy);
		cx += 5000;
		cy += 2500;
		script.addInstruction(new TestInstruction(script, cx, cy));
		cx -= 3000;
		cy += 1000;
		script.addInstruction(new TestInstruction(script, cx, cy));
		cx -= 4000;
		cy -= 5000;
		script.addInstruction(new TestInstruction(script, cx, cy));
		cx += 1000;
		cy += 3000;
		script.addInstruction(new TestInstruction(script, cx, cy));
		cx -= 1500;
		cy += 3000;
		script.addInstruction(new TestInstruction(script, cx, cy));
		cx -= 1000;
		script.addInstruction(new TestInstruction(script, cx, cy));
		cy -= 1000;
		script.addInstruction(new TestInstruction(script, cx, cy));
		script.addInstruction(new TestInstruction(script, cx, cy));
		
		//scripter.getWindow().setScript(script);
		
		scripter.getScriptDB().add(script);
		scripter.getScriptDB().save(script);
		
	}

}
