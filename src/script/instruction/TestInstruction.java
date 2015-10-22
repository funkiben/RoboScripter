package script.instruction;

import java.util.List;

import script.Script;

public class TestInstruction extends Instruction {

	public TestInstruction(Script script, int endX, int endY) {
		super(script, "Test", endX, endY);
	}

	@Override
	public List<String> getImports() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getGlobalVars() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getInitCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRunCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
