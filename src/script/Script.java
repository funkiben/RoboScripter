package script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import script.instruction.Instruction;

public class Script implements Serializable {
	
	private static final long serialVersionUID = -1697694307389461759L;
	
	private String name;
	private final List<Instruction> instructions = new ArrayList<Instruction>();
	
	public Script(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Instruction> getInstructions() {
		return instructions;
	}
	
}
