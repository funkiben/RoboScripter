package script;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import script.instruction.Instruction;

public class Script implements Serializable {
	
	private static final long serialVersionUID = -1697694307389461759L;
	
	private String name;
	private File saveFile = null;
	private final List<Instruction> instructions = new ArrayList<Instruction>();
	
	public Script(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Instruction> getInstructions() {
		return instructions;
	}
	
	public void changeSaveFile(File file) {
		if (this.saveFile != null) {
			saveFile.delete();
		}
		
		saveFile = file;
	}
	
	public File getSaveFile() {
		return saveFile;
	}
	
	public void addInstruction(Instruction instruction) {
		if (instructions.size() > 0) {
			Instruction prevInstruction = instructions.get(instructions.size() - 1);
			instruction.setPrevInstruction(prevInstruction);
		}
		instructions.add(instruction);
	}
	
	public void insertInstruction(Instruction instruction, int index) {
		instructions.add(index, instruction);
	}
	
	public void removeInstruction(int index) {
		instructions.remove(index);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
