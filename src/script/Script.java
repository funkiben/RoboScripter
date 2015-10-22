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
	
	public void addInstruction(Instruction instruction) {
		if (instructions.size() > 0) {
			Instruction prevInstruction = instructions.get(instructions.size() - 1);
			instruction.setStart(prevInstruction.getEndX(), prevInstruction.getEndY());
		}
		instructions.add(instruction);
	}
	
	public void insertInstruction(Instruction instruction, int index) {
		instructions.add(index, instruction);
	}
	
	public void removeInstruction(int index) {
		instructions.remove(index);
	}
	
}
